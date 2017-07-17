package com.assignment.fileProcessor.logic;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "doctor")
public class DoctorDTO {
	@JacksonXmlProperty(isAttribute = true)
	private int id;
	@JacksonXmlProperty(isAttribute = true)
	private String department;
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
		return String.format("[ID: %d, DEPARTMENT: %s, PATIENTS: %s]", this.id, this.department, this.patients.toString());
	}
}
