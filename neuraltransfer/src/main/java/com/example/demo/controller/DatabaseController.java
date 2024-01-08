package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import service.ImageService;

@Controller
public class DatabaseController {
	
	 @Autowired
	    private ImageService imageService;

	    @GetMapping("/database")
	    public String databasePage(Model model, Principal principal, HttpSession session) {
	        // 세션에서 로그인 정보를 가져옴
	        String loginId = (String) session.getAttribute("loginId");

	        if (principal != null || loginId != null) {
	            // 로그인된 경우 (Principal을 통한 확인 및 세션에서도 확인)
	            String username = (principal != null) ? principal.getName() : loginId;

	            // 여기에서 데이터베이스 페이지에 필요한 로직 추가
	            List<String> userImages = imageService.getUserImages(username);
	            model.addAttribute("userImages", userImages);
	            
	            return "database";
	        } else {
	            // 로그인되지 않은 경우
	            return "redirect:/login";
	        }
	    }

}
