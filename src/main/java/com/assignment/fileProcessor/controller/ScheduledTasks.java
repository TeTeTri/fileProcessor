package com.assignment.fileProcessor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.fileProcessor.logic.DataManager;
import com.assignment.fileProcessor.logic.MultipartFileToDoctorDTO;

@Component
@Profile("!test")
public class ScheduledTasks {
	private static final File INPUT_FOLDER = new File("input");
	private static final Path OUTPUT_FOLDER = new File("out").toPath();
	private static final Path ERROR_FOLDER = new File("error").toPath();
	
	private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	MultipartFileToDoctorDTO converter;

	@Autowired
	private DataManager manager;
	
	@Scheduled(fixedRate = 90000)
	public void readFiles() {
		LOG.info("Looking for files in the input folder!");
		for (File localFile : INPUT_FOLDER.listFiles()) {
			if (localFile.isFile()) {
				String message = null;
				try {
					String filename = localFile.getName();
					String contentType = filename.endsWith(".xml") ? "application/xml" : "application/json";
					MultipartFile mpf = new MockMultipartFile("file", filename, contentType, new FileInputStream(localFile));
					message = this.manager.enterData(converter.convert(mpf), false);
					if (message == null) {
						// on success, move parsed file to the OUTPUT folder
						Files.move(localFile.toPath(), OUTPUT_FOLDER.resolve(filename));
						message = String.format("%s successfully parsed!", filename);
					} else {
						// in case of error, move file to the ERROR folder
						Files.move(localFile.toPath(), ERROR_FOLDER.resolve(filename));
					}
				} catch (Exception e) {
					message = e.toString();
				}
				LOG.info(message);
			}
		}
	}
}
