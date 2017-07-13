package com.assignment.fileProcessor.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.assignment.fileProcessor.logic.FileParser;

@Component
@Profile("!test")
public class ScheduledTasks {
	private static final File input = new File("input");

	@Autowired
	private FileParser parser;

	@Scheduled(fixedRate = 90000)
	public void readFiles() {
		// TODO propper logging
		System.out.println("Looking for files in the input folder!");
		for (File localFile : input.listFiles()) {
			if (localFile.isFile()) {
				System.out.printf("Parsing %s!\n", localFile.getName());
				this.parser.parseFile(localFile, false);
			}
		}
	}
}
