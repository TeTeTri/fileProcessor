package com.assignment.fileProcessor.logic;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.fileProcessor.repository.Doctor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public final class MultipartFileToDoctor implements Converter<MultipartFile, Doctor> {
	private static final ObjectMapper jsonMapper = Jackson2ObjectMapperBuilder.json().build();
	private static final XmlMapper xmlMapper = Jackson2ObjectMapperBuilder.xml().build();

	@Override
	public Doctor convert(MultipartFile source) {
		try {
			return source.getContentType().equalsIgnoreCase("application/xml") ? 
					xmlMapper.readValue(source.getBytes(), Doctor.class) :
					jsonMapper.readValue(source.getBytes(), Doctor.class);
		} catch (Exception e) {
			//System.out.println(e.toString());
		}
		return null;
	}
}
