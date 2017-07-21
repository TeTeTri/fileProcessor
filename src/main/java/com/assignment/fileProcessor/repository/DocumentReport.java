package com.assignment.fileProcessor.repository;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.*;

@Entity
@Table(schema = "medical")
@SequenceGenerator(schema = "medical", sequenceName = "report_id_seq", name = "report_id_seq_gen")
@JacksonXmlRootElement(localName = "document_report")
public class DocumentReport {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id_seq_gen")
	@JsonIgnore
	private int id;

	@NotNull
	@JsonProperty("execution_time")
	private Timestamp executionTime;

	@JsonProperty("doctor_id")
	private int doctorId;

	@NotNull
	@JsonProperty("process_execution_time")
	private Timestamp processExecutionTime;

	@Size(min = 1, max = 150)
	private String error;

	@NotNull
	@JsonProperty("document_source")
	private boolean documentSource;

	public DocumentReport() { }

	public DocumentReport(Timestamp executionTime, int doctorId, Timestamp processExecutionTime, String error, boolean documentSource) {
		this.executionTime = executionTime;
		this.doctorId = doctorId;
		this.processExecutionTime = processExecutionTime;
		this.error = error;
		this.documentSource = documentSource;
	}

	public int getId() { return this.id; }
	public Timestamp getExecutionTime() { return this.executionTime; }
	public int getDoctorId() { return this.doctorId; }
	public Timestamp getProcessExecutionTime() { return this.processExecutionTime; }
	public String getError() { return this.error; }
	public boolean getDocumentSource() { return this.documentSource; }

	public void setId(int id) { this.id = id; }
	public void setExecutionTime(Timestamp executionTime) { this.executionTime = executionTime; }
	public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
	public void setProcessExecutionTime(Timestamp processExecutionTime) { this.processExecutionTime = processExecutionTime; }
	public void setError(String error) { this.error = error; }
	public void setDocumentSource(boolean documentSource) { this.documentSource = documentSource; }

	@Override
	public String toString() {
		return String.format("{EXECUTION TIME: %t, DOCTOR ID: %d, PROCESS EXECUTION TIME: %t, ERROR: %s, SOURCE: %b}", this.executionTime, this.doctorId, this.processExecutionTime, this.error, this.documentSource);
	}
}
