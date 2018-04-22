package com.helciodasilva.flying.saucer.samples.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class PdfService {
	
	@Autowired
	private TemplateEngine templateEngine;

	public File createPdf(String templateName, Map<String, String> map) throws Exception {

		Context ctx = new Context();

		for (String key : map.keySet()) {
			ctx.setVariable(key, map.get(key));
		}

		String processedHtml = templateEngine.process(templateName, ctx);

		final File outputFile = File.createTempFile(UUID.randomUUID().toString(), ".pdf");

		try (FileOutputStream os = new FileOutputStream(outputFile);) {
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(processedHtml);
			renderer.layout();
			renderer.createPDF(os, false);
			renderer.finishPDF();

			return outputFile;
		}

	}

}