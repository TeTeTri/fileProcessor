package com.assignment.fileProcessor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import com.assignment.fileProcessor.logic.MultipartFileToDoctor;
import com.assignment.fileProcessor.repository.Doctor;

@Component
@Profile("!test")
public class ScheduledTasks {
	private static final File INPUT_FOLDER = new File("input");
	private static final Path OUTPUT_FOLDER = new File("out").toPath();
	private static final Path ERROR_FOLDER = new File("error").toPath();

	private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	private MultipartFileToDoctor converter;
	
	@Autowired
	private DataManager manager;

	@Scheduled(fixedRate = 90000)
	public void readFiles() {
		LOG.info("Looking for files in the input folder!");
		for (File localFile : INPUT_FOLDER.listFiles()) {
			if (localFile.isFile()) {
				String filename = localFile.getName();
				try {
					// convert file to doctor DTO and enter its data
					String contentType = filename.endsWith(".xml") ? "application/xml" : "application/json";
					MultipartFile mpf = new MockMultipartFile("file", filename, contentType, new FileInputStream(localFile));
					Doctor doctor = converter.convert(mpf);
					this.manager.enterData(doctor, false);

					// on success, move parsed file to the OUTPUT folder
					Files.move(localFile.toPath(), OUTPUT_FOLDER.resolve(filename));
					LOG.info("{} successfully parsed!", filename);
				} catch (Exception e) {
					try {
						// in case of error, move file to the ERROR folder
						Files.move(localFile.toPath(), ERROR_FOLDER.resolve(filename));
					} catch (IOException eIO) {
						LOG.error(eIO.toString());
					}
					LOG.error(e.toString());
				}
			}
		}
	}
}
