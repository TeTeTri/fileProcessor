package com.assignment.fileProcessor.logic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatientDTO {
	private int id;
	private String firstName;
	private String lastName;
	private List<String> diseases;

	public PatientDTO() { }

	public int getId() { return this.id; }
	@JsonProperty("first_name") public String getFirstName() { return this.firstName; }
	@JsonProperty("last_name") public String getLastName() { return this.lastName; }
	public List<String> getDiseases() { return this.diseases; }

	public void setId(int id) { this.id = id; }
	@JsonProperty("first_name") public void setFirstName(String firstName) { this.firstName = firstName; }
	@JsonProperty("last_name") public void setLastName(String lastName) { this.lastName = lastName; }
	public void setDiseases(List<String> diseases) { this.diseases = diseases; }
	
	@Override
	public String toString() {
		return String.format("[ID: %d, FIRST NAME: %s, LAST NAME: %s, DISEASES: %s]", this.id, this.firstName, this.lastName, this.diseases.toString());
	}
}
