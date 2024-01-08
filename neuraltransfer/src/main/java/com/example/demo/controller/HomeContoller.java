package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import service.ImageService;

@Controller
public class HomeContoller {
	
	@Autowired
    private ImageService imageService;

    @PostMapping("/saveImage")
    public String saveImage(@RequestParam("image") MultipartFile image, Principal principal, Model model) throws IOException {
        if (principal != null) {
            String username = principal.getName();

            // 이미지를 해당 계정의 폴더에 저장
            //model.addAtrribute을 통해 메시지를 담아서 프론트에 전달해준다.
			imageService.saveImage(image, username);
			model.addAttribute("successMessage", "이미지가 성공적으로 저장되었습니다.");
			model.addAttribute("alertType", "success");
        } else {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            model.addAttribute("alertType", "warning");
        }

        return "redirect:/";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
	

}
