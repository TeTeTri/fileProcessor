package com.assignment.fileProcessor.logic;

import java.util.List;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.*;

@JacksonXmlRootElement(localName = "doctor")
public class DoctorDTO {
	@JacksonXmlProperty(isAttribute = true)
	private int id;
	@JacksonXmlProperty(isAttribute = true)
	private String department;
	@JacksonXmlElementWrapper(localName = "patients")
	@JacksonXmlProperty(localName = "patient")
	private List<PatientDTO> patients;

	public DoctorDTO() { }

	public int getId() { return this.id; }
	public String getDepartment() { return this.department; }
	public List<PatientDTO> getPatients() { return this.patients; }

	public void setDepartment(String department) { this.department = department; }
	public void setId(int id) { this.id = id; }
	public void setPatients(List<PatientDTO> patients) { this.patients = patients; }

	@Override
	public String toString() {
		return String.format("{ID: %d, DEPARTMENT: %s}", this.id, this.department);
	}

	public String toStringAll() {
		return String.format("{ID: %d, DEPARTMENT: %s, PATIENTS: %s}", this.id, this.department, this.patients.toString());
	}

	@JsonIgnore
	public boolean isEmpty() {
		return (this.department == null) || (this.patients == null) || this.patients.isEmpty();
	}
}
