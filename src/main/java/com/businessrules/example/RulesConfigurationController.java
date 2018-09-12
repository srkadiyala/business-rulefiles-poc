package com.businessrules.example;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RulesConfigurationController {
	
	private Logger LOGGER = LoggerFactory.getLogger(RulesConfigurationController.class);
	
	@RequestMapping(path = "/download/{fileName:.+}", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
		String path = "rules-config/" + fileName;
		Resource resource = new ClassPathResource(path);
		ResponseEntity<Resource> response = null;
		try(InputStream is = resource.getInputStream()){
			response = ResponseEntity.ok()
		            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
		            .contentType(MediaType.parseMediaType("application/octet-stream"))
		            .body(resource);
		} catch(IOException e) {
			LOGGER.error("Error downloading file " + fileName, e);
		}
		return response;
	}
}
