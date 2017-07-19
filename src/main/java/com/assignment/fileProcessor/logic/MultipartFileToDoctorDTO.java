package com.assignment.fileProcessor.logic;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public final class MultipartFileToDoctorDTO implements Converter<MultipartFile, DoctorDTO> {
	private static final ObjectMapper jsonMapper = Jackson2ObjectMapperBuilder.json().build();
	private static final XmlMapper xmlMapper = Jackson2ObjectMapperBuilder.xml().build();

	@Override
	public DoctorDTO convert(MultipartFile source) {
		try {
			return source.getContentType().equalsIgnoreCase("application/xml") ? 
					xmlMapper.readValue(source.getBytes(), DoctorDTO.class) :
					jsonMapper.readValue(source.getBytes(), DoctorDTO.class);
		} catch (Exception e) {
			//System.out.println(e.toString());
		}
		return null;
	}
}
