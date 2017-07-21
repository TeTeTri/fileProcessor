package com.assignment.fileProcessor.repository;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.*;

@Entity
@Table(schema = "medical")
@JacksonXmlRootElement(localName = "patient")
public class Patient {
	@Id
	private int id;

	@NotNull
	@Size(min = 1, max = 50)
	@JsonProperty("first_name")
	private String firstName;

	@NotNull
	@Size(min = 1, max = 50)
	@JsonProperty("last_name")
	private String lastName;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(schema = "medical", name = "medical_record",
				joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name = "disease_id", referencedColumnName = "id"))
	@JacksonXmlElementWrapper(localName = "diseases")
	@JacksonXmlProperty(localName = "disease")
	private List<Disease> diseases;

	public Patient() { }

	public Patient(int id, String firstName, String lastName, List<Disease> diseases) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.diseases = diseases;
	}

	public int getId() { return this.id; }
	public String getFirstName() { return this.firstName; }
	public String getLastName() { return this.lastName; }
	public List<Disease> getDiseases() { return this.diseases; }

	public void setId(int id) { this.id = id; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public void setDiseases(List<Disease> diseases) { this.diseases = diseases; }

	@Override
	public String toString() {
		return String.format("{ID: %d, FIRST NAME: %s, LAST NAME: %s}", this.id, this.firstName, this.lastName);
	}

	public String toStringAll() {
		return String.format("{ID: %d, FIRST NAME: %s, LAST NAME: %s, DISEASES: %s}", this.id, this.firstName, this.lastName, this.diseases);
	}

	@JsonIgnore
	public boolean isEmpty() {
		return (this.firstName == null) || (this.lastName == null) || (this.diseases == null) || this.diseases.isEmpty();
	}
}
