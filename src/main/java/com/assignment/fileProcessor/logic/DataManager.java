package com.assignment.fileProcessor.logic;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.InputMismatchException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assignment.fileProcessor.repository.*;

@Component
public class DataManager {
	private static final Timestamp jvmStart = new Timestamp(ManagementFactory.getRuntimeMXBean().getStartTime());

	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private DiseaseRepository diseaseRepository;
	@Autowired
	private DocumentReportRepository reportRepository;

	/**
	 * @param doctorDTO doctor data transfer object
	 * @param httpRequest is the data source a http request
	 * 
	 * @throws Exception in case of database errors
	 * @throws InputMismatchException in case of malformed input
	 */
	public void enterData(Doctor doctor, boolean httpRequest) throws InputMismatchException, Exception {
		// start building a DOCUMENT_REPORT record
		DocumentReport report = new DocumentReport();
		report.setProcessExecutionTime(Timestamp.from(Instant.now()));
		report.setExecutionTime(jvmStart);
		report.setDocumentSource(httpRequest);

		Exception exception = null;
		if (doctor == null || doctor.isEmpty()) {
			exception = new InputMismatchException("Incorrectly formatted input!");
			// add ERROR to DOCUMENT_REPORT
			report.setError(exception.getClass().getName());
		} else {
			// insert doctor data
			this.doctorRepository.save(doctor);
			// add DOCTOR_ID to DOCUMENT_REPORT
			report.setDoctorId(doctor.getId());
		}

		// insert the DOCUMENT_REPORT record
		this.reportRepository.save(report);

		if (exception != null) {
			throw exception;
		}
	}

	public Iterable<Doctor> getDoctors() {
		return this.doctorRepository.findAll();
	}

	public Iterable<Patient> getPatients() {
		return this.patientRepository.findAll();
	}

	public Iterable<Disease> getDiseases() {
		return this.diseaseRepository.findAll();
	}
}
