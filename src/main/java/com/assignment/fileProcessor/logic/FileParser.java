package com.assignment.fileProcessor.logic;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jooq.impl.DSL;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import static com.assignment.fileProcessor.repository.Sequences.*;
import static com.assignment.fileProcessor.repository.Tables.*;

import com.assignment.fileProcessor.repository.tables.records.*;

@Component
public class FileParser {
	private static final Path OUTPUT = new File("out").toPath();
	private static final Path ERROR = new File("error").toPath();
	private static final Timestamp jvmStart = new Timestamp(ManagementFactory.getRuntimeMXBean().getStartTime());

	@Autowired
	private DSLContext context;

	/**
	 * @param file input file (XML)
	 * @param httpRequest is the file source a http request
	 */
	public String parseFile(File file, boolean httpRequest) {
		// start building a DOCUMENT_REPORT record
		InsertSetMoreStep<DocumentReportRecord> insertStep = this.context.insertInto(DOCUMENT_REPORT)
					.set(DOCUMENT_REPORT.DOCUMENT_SOURCE, httpRequest)
					.set(DOCUMENT_REPORT.EXECUTION_TIME, jvmStart)
					.set(DOCUMENT_REPORT.PROCESS_EXECUTION_TIME, DSL.currentTimestamp());

		String message = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			Element doctor = doc.getDocumentElement();
			doctor.normalize();

			int doctorId = Integer.parseInt(doctor.getAttribute("id"));
			String departmentName = doctor.getAttribute("department");

			// check if a row with DEPARTMENT.NAME value does not exists
			Integer departmentId = this.context.fetchValue(this.context.select(DEPARTMENT.DEPARTMENT_ID).from(DEPARTMENT).where(DEPARTMENT.NAME.eq(departmentName)));
			if (departmentId == null) {
				// insert row into the table DEPARTMENT
				this.context.insertInto(DEPARTMENT, DEPARTMENT.NAME)
						.values(departmentName)
						.execute();
				departmentId = this.context.currval(DEPARTMENT_ID_SEQ);
			}

			// insert row into the table DOCTOR, if no matching DOCTOR_ID is present
			this.context.insertInto(DOCTOR, DOCTOR.DOCTOR_ID, DOCTOR.DEPARTMENT_ID)
					.values(doctorId, departmentId)
					.onConflict(DOCTOR.DOCTOR_ID)
					.doNothing()
					.execute();

			// add the DOCTOR_ID to the DOCUMENT_REPORT record
			insertStep.set(DOCUMENT_REPORT.DOCTOR_ID, doctorId);

			// walk through patient data
			Element patients = (Element) doctor.getElementsByTagName("patients").item(0);
			NodeList patientList = patients.getElementsByTagName("patient");
			for (int i = 0; i < patientList.getLength(); i++) {
				Element patient = (Element) patientList.item(i);

				// get patient data from XML element
				int patientId = Integer.parseInt(patient.getElementsByTagName("id").item(0).getTextContent());
				String firstName = patient.getElementsByTagName("first_name").item(0).getTextContent();
				String lastName = patient.getElementsByTagName("last_name").item(0).getTextContent();

				// insert row into the table PATIENT, if no matching PATIENT_ID is present 
				this.context.insertInto(PATIENT, PATIENT.PATIENT_ID, PATIENT.FIRST_NAME, PATIENT.LAST_NAME)
						.values(patientId, firstName, lastName)
						.onConflict(PATIENT.PATIENT_ID)
						.doNothing()
						.execute();

				// walk through disease data
				Element diseases = (Element) patient.getElementsByTagName("diseases").item(0);
				NodeList diseaseList = diseases.getElementsByTagName("disease");
				for (int j = 0; j < diseaseList.getLength(); j++) {
					Element disease = (Element) diseaseList.item(j);
					String diseaseName = disease.getTextContent();

					// check if a row with DISEASE.NAME value does not exists
					Integer diseaseId = this.context.fetchValue(this.context.select(DISEASE.DISEASE_ID).from(DISEASE).where(DISEASE.NAME.eq(diseaseName)));
					if (diseaseId == null) {
						// insert row into the table DISEASE
						this.context.insertInto(DISEASE, DISEASE.NAME)
								.values(diseaseName)
								.execute();
						diseaseId = this.context.currval(DISEASE_ID_SEQ);
					}

					// check if the MEDICAL_RECORD row does not exists
					if (this.context.select(MEDICAL_RECORD.DOCTOR_ID, MEDICAL_RECORD.PATIENT_ID, MEDICAL_RECORD.DISEASE_ID)
								.from(MEDICAL_RECORD)
								.where(MEDICAL_RECORD.DOCTOR_ID.eq(doctorId), MEDICAL_RECORD.PATIENT_ID.eq(patientId), MEDICAL_RECORD.DISEASE_ID.eq(diseaseId))
								.fetch().isEmpty()) {
						// insert row into the table MEDICAL_RECORD
						this.context.insertInto(MEDICAL_RECORD, MEDICAL_RECORD.DOCTOR_ID, MEDICAL_RECORD.PATIENT_ID, MEDICAL_RECORD.DISEASE_ID)
						.values(doctorId, patientId, diseaseId)
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
			// on success, move parsed file to the OUTPUT folder
			Files.move(file.toPath(), OUTPUT.resolve(file.getName()));
		} catch (Exception e1) {
			message = e1.getMessage();
			// add error message to the DOCUMENT_REPORT record 
			insertStep.set(DOCUMENT_REPORT.ERROR, e1.getClass().getName());

			try {
				// in case of error, move file to the ERROR folder
				Files.move(file.toPath(), ERROR.resolve(file.getName()));
			} catch (Exception e2) {
				message = e2.getMessage().concat(message);
			}
		}

		// insert the DOCUMENT_REPORT record
		insertStep.execute();

//		System.out.println(this.context.selectFrom(DOCUMENT_REPORT).fetch());
		return message;
	}
}
