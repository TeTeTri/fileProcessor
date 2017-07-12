package com.assignment.fileProcessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.jooq.DSLContext;
import org.jooq.Queries;
import org.jooq.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
		Queries ddl = this.context.ddl(Medical.MEDICAL);
		for (Query query : ddl.queries()) {
			this.context.execute(query);
		}
		
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
		
		System.out.println(this.context.selectFrom(DEPARTMENT).fetch());
		System.out.println(this.context.selectFrom(DOCTOR).fetch());
		System.out.println(this.context.selectFrom(PATIENT).fetch());
		System.out.println(this.context.selectFrom(DISEASE).fetch());
		System.out.println(this.context.selectFrom(MEDICAL_RECORD).fetch());
	}
}
