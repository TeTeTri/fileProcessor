package com.assignment.fileProcessor.logic;

import static com.assignment.fileProcessor.repository.Sequences.*;
import static com.assignment.fileProcessor.repository.Tables.*;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.util.InputMismatchException;

import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assignment.fileProcessor.repository.tables.records.DocumentReportRecord;

@Component
public class DataManager {
	private static final Timestamp jvmStart = new Timestamp(ManagementFactory.getRuntimeMXBean().getStartTime());

	@Autowired
	private DSLContext context;

	/**
	 * @param doctorDTO doctor data transfer object
	 * @param httpRequest is the data source a http request
	 * 
	 * @throws Exception in case of database errors
	 * @throws InputMismatchException in case of malformed input
	 */
	public void enterData(DoctorDTO doctorDTO, boolean httpRequest) throws InputMismatchException, Exception {
		if (doctorDTO == null || doctorDTO.isEmpty()) {
			throw new InputMismatchException("Incorrectly formatted input!");
		}
		// start building a DOCUMENT_REPORT record
		InsertSetMoreStep<DocumentReportRecord> insertStep = this.context.insertInto(DOCUMENT_REPORT)
					.set(DOCUMENT_REPORT.DOCUMENT_SOURCE, httpRequest)
					.set(DOCUMENT_REPORT.EXECUTION_TIME, jvmStart)
					.set(DOCUMENT_REPORT.PROCESS_EXECUTION_TIME, DSL.currentTimestamp());

		Exception exception = null;
		try {
			// check if a row with DEPARTMENT.NAME value does not exists
			Integer departmentId = this.context.fetchValue(this.context.select(DEPARTMENT.DEPARTMENT_ID).from(DEPARTMENT).where(DEPARTMENT.NAME.eq(doctorDTO.getDepartment())));
			if (departmentId == null) {
				// insert row into the table DEPARTMENT
				this.context.insertInto(DEPARTMENT, DEPARTMENT.NAME)
						.values(doctorDTO.getDepartment())
						.execute();
				departmentId = this.context.currval(DEPARTMENT_ID_SEQ);
			}

			// insert row into the table DOCTOR, if no matching DOCTOR_ID is present
			this.context.insertInto(DOCTOR, DOCTOR.DOCTOR_ID, DOCTOR.DEPARTMENT_ID)
					.values(doctorDTO.getId(), departmentId)
					.onConflict(DOCTOR.DOCTOR_ID)
					.doNothing()
					.execute();

			// add the DOCTOR_ID to the DOCUMENT_REPORT record
			insertStep.set(DOCUMENT_REPORT.DOCTOR_ID, doctorDTO.getId());

			// walk through patient data
			for (PatientDTO patient : doctorDTO.getPatients()) {
				// insert row into the table PATIENT, if no matching PATIENT_ID is present 
				this.context.insertInto(PATIENT, PATIENT.PATIENT_ID, PATIENT.FIRST_NAME, PATIENT.LAST_NAME)
						.values(patient.getId(), patient.getFirstName(), patient.getLastName())
						.onConflict(PATIENT.PATIENT_ID)
						.doNothing()
						.execute();

				// walk through disease data
				for (String disease : patient.getDiseases()) {
					// check if a row with DISEASE.NAME value does not exists
					Integer diseaseId = this.context.fetchValue(this.context.select(DISEASE.DISEASE_ID).from(DISEASE).where(DISEASE.NAME.eq(disease)));
					if (diseaseId == null) {
						// insert row into the table DISEASE
						this.context.insertInto(DISEASE, DISEASE.NAME)
								.values(disease)
								.execute();
						diseaseId = this.context.currval(DISEASE_ID_SEQ);
					}

					// check if the MEDICAL_RECORD row does not exists
					if (this.context.select(MEDICAL_RECORD.DOCTOR_ID, MEDICAL_RECORD.PATIENT_ID, MEDICAL_RECORD.DISEASE_ID)
								.from(MEDICAL_RECORD)
								.where(MEDICAL_RECORD.DOCTOR_ID.eq(doctorDTO.getId()), MEDICAL_RECORD.PATIENT_ID.eq(patient.getId()), MEDICAL_RECORD.DISEASE_ID.eq(diseaseId))
								.fetch().isEmpty()) {
						// insert row into the table MEDICAL_RECORD
						this.context.insertInto(MEDICAL_RECORD, MEDICAL_RECORD.DOCTOR_ID, MEDICAL_RECORD.PATIENT_ID, MEDICAL_RECORD.DISEASE_ID)
						.values(doctorDTO.getId(), patient.getId(), diseaseId)
						.execute();
					}
				}
			}
/*
			System.out.println(this.context.selectFrom(DEPARTMENT).fetch());
			System.out.println(this.context.selectFrom(DOCTOR).fetch());
			System.out.println(this.context.selectFrom(PATIENT).fetch());
			System.out.println(this.context.selectFrom(DISEASE).fetch());
			System.out.println(this.context.selectFrom(MEDICAL_RECORD).fetch());
*/
		} catch (Exception e) {
			exception = e;
			// add error message to the DOCUMENT_REPORT record 
			insertStep.set(DOCUMENT_REPORT.ERROR, e.getClass().getName());
		}

		// insert the DOCUMENT_REPORT record
		insertStep.execute();

		//System.out.println(insertStep.toString());
		//System.out.println(this.context.selectFrom(DOCUMENT_REPORT).fetch());

		if (exception != null) {
			throw exception;
		}
	}
}
