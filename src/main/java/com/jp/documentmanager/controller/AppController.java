package com.jp.documentmanager.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jp.documentmanager.entity.Document;
import com.jp.documentmanager.repository.DocumentRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@ControllerAdvice
public class AppController {
	@Value("${spring.servlet.multipart.max-file-size}")
	private String maxFileSize;
	
	@Autowired
	private DocumentRepository documentRepository;

	@GetMapping("/")
	public String viewHomePage(Model model) {
		List<Document> listDocs = documentRepository.findAll();
		model.addAttribute("listDocs", listDocs);
		return "home";
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("document") MultipartFile mulitpartFile, RedirectAttributes ra)
			throws IOException {
		String fileName = StringUtils.cleanPath(mulitpartFile.getOriginalFilename());

		Document document = new Document();
		document.setName(fileName);
		document.setContent(mulitpartFile.getBytes());
		document.setSize(mulitpartFile.getSize());
		document.setUploadTime(new Date());
		documentRepository.save(document);
		ra.addFlashAttribute("message", "The file has been uploaded successfully");
		return "redirect:/";
	}

	@GetMapping("/download")
	public void downloadFile(@Param("id") Long id, HttpServletResponse response) throws Exception {
		Optional<Document> result = documentRepository.findById(id);
		if (!result.isPresent()) {
			throw new Exception("Could not find document with ID: " + id);
		}
		Document document = result.get();
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename = " + document.getName();

		response.setHeader(headerKey, headerValue);

		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(document.getContent());
		outputStream.close();
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleFileUploadError(RedirectAttributes ra) {
		ra.addFlashAttribute("error","You could not upload file bigger than " + maxFileSize );
		return "redirect:/";
	}
}
