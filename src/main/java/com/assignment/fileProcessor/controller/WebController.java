package com.assignment.fileProcessor.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.fileProcessor.logic.FileParser;

@RestController
public class WebController implements ErrorController {
	private static final String ERRORPATH = "/error";
	
	@Autowired
	private FileParser parser;
	
	@GetMapping("/")
	public String post() {
		return "Hello World!";
	}
	
	@GetMapping(ERRORPATH)
	public String error() {
		return "Oops!";
	}
	
	@Override
	public String getErrorPath() {
		return ERRORPATH;
	}
	
	@PostMapping("/")
	public String readFile(@RequestParam(value = "file") MultipartFile inputFile) {
		String message;
		try {
			String filename = inputFile.getOriginalFilename();
			File localFile = new File(filename);
			FileOutputStream fos = new FileOutputStream(localFile);
			fos.write(inputFile.getBytes());
			fos.close();
			// TODO propper logging and error message on parsing failure
			System.out.printf("Parsing file %s from http POST!\n", filename);
			this.parser.parseFile(localFile, true);
			message = String.format("You successfully uploaded the file %s!\n", filename);
		} catch (IOException e) {
			message = e.getMessage();
		}
		
		return message;
	}
}
