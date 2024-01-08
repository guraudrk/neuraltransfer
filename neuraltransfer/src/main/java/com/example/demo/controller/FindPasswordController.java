package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FindPasswordController {
	
	//이메일 입력 페이지(비밀번호 찾기)로 이동
	@GetMapping("/findpassword")
	public String showFindPasswordPage() {
		return "findpassword";
	}

	 // 이메일 검증 및 결과 페이지로 이동
    @PostMapping("/findpassword")
    public String verifyEmail(@RequestParam String email) {
       return "findpassword";
    }
}
