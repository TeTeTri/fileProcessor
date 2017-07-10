package com.assignment.fileProcessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FileProcessorApplication {
	public static void main(String[] args) {
		SpringApplication.run(FileProcessorApplication.class, args);
	}
}
