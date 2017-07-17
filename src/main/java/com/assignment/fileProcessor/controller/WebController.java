package com.assignment.fileProcessor.controller;

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
	public String readFile(@RequestParam(name = "file") DoctorDTO doctorDTO) {
		LOG.info("Received file from http POST!");
		String message = this.manager.enterData(doctorDTO, true);
		if (message == null) {
			message = "You successfully uploaded the file!";
		}
		return message;
	}
}
