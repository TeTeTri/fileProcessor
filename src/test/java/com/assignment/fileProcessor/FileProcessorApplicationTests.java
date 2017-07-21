package com.assignment.fileProcessor;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.FileInputStream;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileProcessorApplicationTests {
	@Autowired
	private WebApplicationContext webContext;

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("restDocumentation");

	private MockMvc mockMvc;

	@Before
	public void testSetup() {
		// mockMvc setup
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webContext)
				.apply(documentationConfiguration(this.restDocumentation))
				.build();
	}

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk())
					.andExpect(content().string("Hello World!"));
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

	@Test
	public void badRequestOnSparseFilePost() throws Exception {
		MockMultipartFile theFile = new MockMultipartFile("file", "foo.xml", "application/xml", "<doctor></doctor>".getBytes());
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());

		theFile = new MockMultipartFile("file", "bar.json", "application/json", "{}".getBytes());
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());
	}

	@Test // wrong file structure
	public void badRequestOnMalformedFilePost() throws Exception {
		MockMultipartFile theFile = new MockMultipartFile("file", "foo.xml", "application/xml", "{}".getBytes());
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isBadRequest());

		theFile = new MockMultipartFile("file", "bar.json", "application/json", "<doctor></doctor>".getBytes());
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
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isOk())
					.andExpect(content().string("You successfully uploaded the file!"));

		theFile = new MockMultipartFile("file", "example.json", "", new FileInputStream("example.json"));
		this.mockMvc.perform(fileUpload("/").file(theFile)).andExpect(status().isOk())
					.andExpect(content().string("You successfully uploaded the file!"))
					.andDo(document("index"));
	}
}
