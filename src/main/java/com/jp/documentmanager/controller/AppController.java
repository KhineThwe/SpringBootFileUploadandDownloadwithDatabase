package com.jp.documentmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jp.documentmanager.entity.Document;

@Controller
public class AppController {
	@GetMapping("/")
	public String viewHomePage() {
		return "home";
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("document") MultipartFile mulitpartFile, RedirectAttributes ra) {
		String fileName = StringUtils.cleanPath(mulitpartFile.getOriginalFilename());
		
		Document document = new Document();		
		return "redirect:/";
	}
}
