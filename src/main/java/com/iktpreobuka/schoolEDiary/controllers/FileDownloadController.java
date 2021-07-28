package com.iktpreobuka.schoolEDiary.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/schoolEDiary/")
public class FileDownloadController {

	@Value("${logging.file.name}")
	private String logFilePath;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, path = "/downloadLogs")
	public ResponseEntity<Resource> downloadLogs(HttpServletRequest request) {
		logger.info("Received Admin request for log file dowload");

		String projectpath = System.getProperty("user.dir");
		Path path = Paths.get(projectpath).toAbsolutePath().normalize();
		Path filePath = path.resolve(logFilePath).normalize();

		try {
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				// Try to determine file's content type
				String contentType = null;
				try {
					contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
				} catch (IOException ex) {
					logger.info("Could not determine file type.");
				}

				// Fallback to the default content type if type could not be determined
				if (contentType == null) {
					contentType = "application/octet-stream";
				}

				logger.info("The Log file successfully retreived");

				return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
						.header(HttpHeaders.CONTENT_DISPOSITION,
								"attachment; filename=\"" + resource.getFilename() + "\"")
						.body(resource);
			} else {
				logger.info("Log file not found");
				return ResponseEntity.notFound().build();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}

	}
}
