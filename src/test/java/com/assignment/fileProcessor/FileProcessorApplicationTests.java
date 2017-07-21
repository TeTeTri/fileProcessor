package com.assignment.fileProcessor;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import java.io.FileInputStream;
import org.jooq.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.assignment.fileProcessor.repository.Sequences.*;

import com.assignment.fileProcessor.repository.Medical;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileProcessorApplicationTests {
	@Autowired
	private DSLContext dslContext;

	@Autowired
	private WebApplicationContext webContext;

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("restDocumentation");

	private RestDocumentationResultHandler document;

	private MockMvc mockMvc;

	@Before
	public void testSetup() {
		this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webContext)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(this.document)
				.build();
		
		Queries ddl = this.dslContext.ddl(Medical.MEDICAL);
		for (Query query : ddl.queries()) {
			this.dslContext.execute(query);
		}
		this.dslContext.createSequence(DEPARTMENT_ID_SEQ).execute();
		this.dslContext.createSequence(DISEASE_ID_SEQ).execute();
		this.dslContext.createSequence(RECORD_ID_SEQ).execute();
		this.dslContext.createSequence(REPORT_ID_SEQ).execute();
	}

	@After
	public void testTeardown() {
		this.dslContext.dropSchema(Medical.MEDICAL).execute();
	}

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk())
					.andExpect(content().string("Hello World!"));
	}

	@Test
	public void getDoctorList() throws Exception {
		this.mockMvc.perform(get("/doctors")).andExpect(status().isOk());
	}

	@Test
	public void getPatientList() throws Exception {
		this.mockMvc.perform(get("/patients")).andExpect(status().isOk());
	}

	@Test
	public void getDiseaseList() throws Exception {
		this.mockMvc.perform(get("/diseases")).andExpect(status().isOk());
	}

	@Test
	public void badRequestOnEmptyPost() throws Exception {
		this.mockMvc.perform(post("/")).andExpect(status().isBadRequest());
	}

	@Test
	public void badRequestOnEmptyFilePost() throws Exception {
		MockMultipartFile theFile = new MockMultipartFile("file", "foo.xml", "application/xml", new byte[0]);
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());

		theFile = new MockMultipartFile("file", "bar.json", "application/json", new byte[0]);
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());
	}

	@Test // wrong file structure
	public void badRequestOnMalformedFilePost() throws Exception {
		MockMultipartFile theFile = new MockMultipartFile("file", "foo.xml", "application/xml", "{}".getBytes());
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());

		theFile = new MockMultipartFile("file", "bar.json", "application/json", "<doctor></doctor>".getBytes());
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());
	}

	@Test // correct file structure, missing values
	public void badRequestOnSparseFilePost() throws Exception {
		MockMultipartFile theFile = new MockMultipartFile("file", "foo.xml", "application/xml", "<doctor></doctor>".getBytes());
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());

		theFile = new MockMultipartFile("file", "bar.json", "application/json", "{}".getBytes());
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());
	}

	@Test // correct file structure, wrong values
	public void badRequestOnFaultyFilePost() throws Exception {
		MockMultipartFile theFile = new MockMultipartFile("file", "faulty.xml", "application/xml", new FileInputStream("faulty.xml"));
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());

		theFile = new MockMultipartFile("file", "faulty.json", "application/json", new FileInputStream("faulty.json"));
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());
	}

	@Test
	public void successfulExampleFilePost() throws Exception {
		MockMultipartFile theFile = new MockMultipartFile("file", "example.xml", "application/xml", new FileInputStream("example.xml"));
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isOk()).andExpect(content().string("You successfully uploaded the file!"));

		theFile = new MockMultipartFile("file", "example.json", "", new FileInputStream("example.json"));
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isOk()).andExpect(content().string("You successfully uploaded the file!"));
	}
}
