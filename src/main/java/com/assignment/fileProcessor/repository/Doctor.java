package com.assignment.fileProcessor.repository;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.*;

@Entity
@Table(schema = "medical")
@JacksonXmlRootElement(localName = "doctor")
public class Doctor {
	@Id
	@JacksonXmlProperty(isAttribute = true)
	private int id;

	@NotNull
	@Size(min = 1, max = 50)
	@JacksonXmlProperty(isAttribute = true)
	private String department;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "doctor_id")
	@JacksonXmlElementWrapper(localName = "patients")
	@JacksonXmlProperty(localName = "patient")
	private List<Patient> patients;

	public Doctor() { }

	public Doctor(int id, String department, List<Patient> patients) {
		this.id = id;
		this.department = department;
		this.patients = patients;
	}

	public int getId() { return this.id; }
	public String getDepartment() { return this.department; }
	public List<Patient> getPatients() { return this.patients; }

	public void setDepartment(String department) { this.department = department; }
	public void setId(int id) { this.id = id; }
	public void setPatients(List<Patient> patients) { this.patients = patients; }

	@Override
	public String toString() {
		return String.format("{ID: %d, DEPARTMENT: %s}", this.id, this.department);
	}

	public String toStringAll() {
		return String.format("{ID: %d, DEPARTMENT: %s, PATIENTS: %s}", this.id, this.department, this.patients);
	}

	@JsonIgnore
	public boolean isEmpty() {
		return (this.department == null) || (this.patients == null) || this.patients.isEmpty();
	}
}
