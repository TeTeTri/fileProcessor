package com.assignment.fileProcessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.jooq.DSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.assignment.fileProcessor.repository.Sequences.*;
import static com.assignment.fileProcessor.repository.Tables.*;

import com.assignment.fileProcessor.controller.WebController;
import com.assignment.fileProcessor.repository.Medical;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileProcessorApplicationTests {
	@Autowired
	private WebController controller;
	
	@Autowired
	private DSLContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void contextLoads() {
		assertThat(this.controller).isNotNull();
		assertThat(this.context).isNotNull();
	}
	
	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk())
					.andExpect(content().string("Hello World!"));
	}
	
	@Test
	public void databaseInitiationAndDataInsertion() {
		this.context.createSchema(Medical.MEDICAL).execute();
		this.context.createSequence(DEPARTMENT_ID_SEQ).execute();
		this.context.createSequence(DISEASE_ID_SEQ).execute();
		this.context.createSequence(RECORD_ID_SEQ).execute();
		this.context.createSequence(REPORT_ID_SEQ).execute();
		
		this.context.createTable(DEPARTMENT)
					.column(DEPARTMENT.DEPARTMENT_ID)
					.column(DEPARTMENT.NAME)
					.execute();
		this.context.createTable(DOCTOR)
					.column(DOCTOR.DOCTOR_ID)
					.column(DOCTOR.DEPARTMENT_ID)
					.execute();
		this.context.createTable(PATIENT)
					.column(PATIENT.PATIENT_ID)
					.column(PATIENT.FIRST_NAME)
					.column(PATIENT.LAST_NAME)
					.execute();
		this.context.createTable(DISEASE)
					.column(DISEASE.DISEASE_ID)
					.column(DISEASE.NAME)
					.execute();
		this.context.createTable(MEDICAL_RECORD)
					.column(MEDICAL_RECORD.RECORD_ID)
					.column(MEDICAL_RECORD.DOCTOR_ID)
					.column(MEDICAL_RECORD.PATIENT_ID)
					.column(MEDICAL_RECORD.DISEASE_ID)
					.execute();
		this.context.createTable(DOCUMENT_REPORT)
					.column(DOCUMENT_REPORT.REPORT_ID)
					.column(DOCUMENT_REPORT.EXECUTION_TIME)
					.column(DOCUMENT_REPORT.DOCTOR_ID)
					.column(DOCUMENT_REPORT.PROCESS_EXECUTION_TIME)
					.column(DOCUMENT_REPORT.ERROR)
					.column(DOCUMENT_REPORT.DOCUMENT_SOURCE)
					.execute();
		
		this.context.insertInto(DEPARTMENT).values(1, "marand").execute();
		
		this.context.insertInto(DOCTOR).values(100, 1).execute();
		
		this.context.insertInto(PATIENT).values(1, "Bostjan", "Lah").execute();
		this.context.insertInto(PATIENT).values(2, "Boris", "Marn").execute();
		this.context.insertInto(PATIENT).values(3, "Anze", "Droljc").execute();
		
		this.context.insertInto(DISEASE).values(1, "nice_to_people").execute();
		this.context.insertInto(DISEASE).values(2, "long_legs").execute();
		this.context.insertInto(DISEASE).values(3, "used_to_have_dredds").execute();
		this.context.insertInto(DISEASE).values(4, "chocaholic").execute();
		this.context.insertInto(DISEASE).values(5, "great_haircut").execute();
		
		this.context.insertInto(MEDICAL_RECORD).values(1, 100, 1, 1).execute();
		this.context.insertInto(MEDICAL_RECORD).values(2, 100, 1, 2).execute();
		this.context.insertInto(MEDICAL_RECORD).values(3, 100, 2, 3).execute();
		this.context.insertInto(MEDICAL_RECORD).values(4, 100, 2, 1).execute();
		this.context.insertInto(MEDICAL_RECORD).values(5, 100, 3, 4).execute();
		this.context.insertInto(MEDICAL_RECORD).values(6, 100, 3, 5).execute();
		
		System.out.println(context.selectFrom(DEPARTMENT).fetch());
		System.out.println(context.selectFrom(DOCTOR).fetch());
		System.out.println(context.selectFrom(PATIENT).fetch());
		System.out.println(context.selectFrom(DISEASE).fetch());
		System.out.println(context.selectFrom(MEDICAL_RECORD).fetch());		
	}
}
