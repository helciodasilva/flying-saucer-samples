package com.helciodasilva.flying.saucer.samples.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.helciodasilva.flying.saucer.samples.service.PdfService;

@Controller
@RequestMapping("/pdf")
public class PdfController {

	@Autowired
	PdfService pdfService;

	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<?> download(@RequestParam(required = true) String username) throws Exception {

		Map<String, String> data = new HashMap<>();
		data.put("username", username);

		File pdf = pdfService.createPdf("template", data);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", pdf.getName());
		headers.setContentLength(pdf.length());

		return new ResponseEntity<>(new FileSystemResource(pdf), headers, HttpStatus.OK);
	}

}
