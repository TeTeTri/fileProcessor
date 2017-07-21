package com.assignment.fileProcessor.repository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.*;

@Entity
@Table(schema = "medical", uniqueConstraints = {@UniqueConstraint(name = "disease_name_unique", columnNames = {"name"}) })
@SequenceGenerator(schema = "medical", sequenceName = "disease_id_seq", name = "disease_id_seq_gen")
@JacksonXmlRootElement(localName = "disease")
public class Disease {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disease_id_seq_gen")
	@JsonIgnore
	private int id;

	@NotNull
	@Size(min = 1, max = 50)
	private String name;

	public Disease() { }

	public Disease(String name) {
		this.name = name;
	}

	public int getId() { return id; }
	public String getName() { return this.name; }

	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }

	@Override
	public String toString() {
		return this.name;
	}
}
