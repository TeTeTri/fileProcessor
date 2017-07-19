package com.assignment.fileProcessor.logic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "patient")
public class PatientDTO {
	private int id;
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;
	@JacksonXmlElementWrapper(localName = "diseases")
	@JacksonXmlProperty(localName = "disease")
	private List<String> diseases;

	public PatientDTO() { }

	public int getId() { return this.id; }
	public String getFirstName() { return this.firstName; }
	public String getLastName() { return this.lastName; }
	public List<String> getDiseases() { return this.diseases; }

	public void setId(int id) { this.id = id; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public void setDiseases(List<String> diseases) { this.diseases = diseases; }
	
	@Override
	public String toString() {
		return String.format("[ID: %d, FIRST NAME: %s, LAST NAME: %s, DISEASES: %s]", this.id, this.firstName, this.lastName, this.diseases.toString());
	}
	
	public boolean isEmpty() {
		return (this.firstName == null) || (this.lastName == null) || (this.diseases == null) || this.diseases.isEmpty();
	}
}
