package com.assignment.fileProcessor.controller;

import java.io.IOException;
import java.util.InputMismatchException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.*;

import com.assignment.fileProcessor.logic.DoctorDTO;
import com.assignment.fileProcessor.logic.DataManager;

@RestController
public class WebController implements ErrorController {
	private static final String ERROR_PATH = "/error";

	private static final Logger LOG = LoggerFactory.getLogger(WebController.class);

	@Autowired
	private DataManager manager;

	@GetMapping("/")
	public String get() {
		return "Hello World!";
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
	public String readFile(@RequestParam("file") DoctorDTO doctorDTO, HttpServletResponse response) throws IOException {
		LOG.info("Received file from http POST!");
		try {
			this.manager.enterData(doctorDTO, true);
			return "You successfully uploaded the file!";
		} catch (InputMismatchException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		}
		return "";
	}
}
