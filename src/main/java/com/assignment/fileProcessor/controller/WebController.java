package com.assignment.fileProcessor.controller;

import java.io.IOException;
import java.util.InputMismatchException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.*;

import com.assignment.fileProcessor.logic.DataManager;
import com.assignment.fileProcessor.repository.*;

@RestController
public class WebController implements ErrorController {
	private static final String ERROR_PATH = "/error";

	private static final Logger LOG = LoggerFactory.getLogger(WebController.class);
	
	@Autowired
	private DataManager manager;

	@GetMapping("/")
	public String home() {
		return "Hello World!";
	}
	
	@GetMapping("/doctors")
	public String getDoctors() {
		StringBuilder sb = new StringBuilder();
		for (Doctor doctor : this.manager.getDoctors()) {
			sb.append(doctor).append("<br>");
		}
		return sb.toString();
	}
	
	@GetMapping("/patients")
	public String getPatients() {
		StringBuilder sb = new StringBuilder();
		for (Patient patient : this.manager.getPatients()) {
			sb.append(patient).append("<br>");
		}
		return sb.toString();
	}
	
	@GetMapping("/diseases")
	public String getDiseases() {
		StringBuilder sb = new StringBuilder();
		for (Disease disease : this.manager.getDiseases()) {
			sb.append(disease).append("<br>");
		}
		return sb.toString();
	}

	@GetMapping(ERROR_PATH)
	public String error() {
		return "Oops!";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	@PostMapping("/")
	public String readFile(@RequestParam("file") Doctor doctor, HttpServletResponse response) throws IOException {
		LOG.info("Received file from http POST!");
		try {
			this.manager.enterData(doctor, true);
			return "You successfully uploaded the file!";
		} catch (InputMismatchException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			e.printStackTrace();
		}
		return "";
	}
}
