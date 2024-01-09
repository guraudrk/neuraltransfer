package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dto.MemberDTO;
import entity.MemberEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import service.MemberService;


@Controller
@RequiredArgsConstructor //lombok의 어노테이션이다.
public class MemberController {

	
	//memeberservice 객체를 정의한다.
	private final MemberService memberService;
	
	@Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
	
	
	//생성자 주입
	
	@GetMapping("/save")
	public String save() {
		return "Save";
	}
	
	//RequestParam을 통해 변수들을 받아온다. 아이디,비밀번호,이름을 받아온다.
	//member/save 페이지에서 데이터를 가져 올 때의 동작에 관한 함수이다.
	//@ResponseBody을 추가해서  해당 문자열이 뷰 템플릿이 아니라 직접 응답의 일부로 전송되도록 만든다.
	@PostMapping("/save")
	public @ResponseBody String save1(@ModelAttribute MemberDTO memberDTO,Model model ) {
		//DTO와 실제 페이지의 name이 동일 한 것을 찾아서 값을 가져와준다.
		
		
		 // 비밀번호 일치 여부 확인
	    if (!memberDTO.getPassword().equals(memberDTO.getPasswordConfirm())) {
	        model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
	        return "비밀번호가 일치하지 않습니다."; // 회원가입 페이지로 다시 이동
	    }
		
		//DTO의 정보들을 service를 통해 save한다.
		memberService.save(memberDTO);
		
		//이런 식으로 리다이렉트 해야 한다. 홈 페이지로 리다이렉트 했다.
		return "회원가입이 완료되었습니다.";
	}
	
	//비밀번호를 확인하는 컨트롤러.
	@PostMapping("/member/save")
	public @ResponseBody String checkPassword(@RequestParam("password") String password,
	                                          @RequestParam("confirmPassword") String confirmPassword) {
	    // 비밀번호 일치 여부 확인
	    if (password.equals(confirmPassword)) {
	        return "ok";
	    } else {
	        return "no";
	    }
	}
	
	//주소를 요청했을 때 로그인 페이지로 이동한다.
	//권한 부여 코드 구현을 위해, model을 정의한다.
	@GetMapping("/login")
	public String loginForm(HttpSession session, Model model) {
	    // 세션에서 로그인 정보를 가져옴
	    String loginId = (String) session.getAttribute("loginId");

	    // 이미 로그인된 경우 메인 페이지로 리다이렉트
	    if (loginId != null) {
	        return "redirect:/?loggedIn=true";
	    }

	    // 로그인되지 않은 경우 로그인 페이지로 이동
	    model.addAttribute("userDTO", new MemberDTO());
	    return "login";
	}
	
	
	//Httpsession은 세션을 부여하여, 로그인이 된 후에 다른 기능들을 사용할 수 있게 해준다.
	@PostMapping("/login")
	public String login(@ModelAttribute("userDTO") MemberDTO userDTO, HttpSession session, Model model) {
        // 사용자 인증 로직을 처리하는 MemberService의 login 메서드 호출
        MemberDTO loginResult = memberService.login(userDTO);

        
	    
        if (loginResult != null) {
            // 로그인 성공
            // 세션에 로그인 정보 저장
            session.setAttribute("loginId", loginResult.getEmail());
            model.addAttribute("loggedIn", true);
            System.out.println("로그인 성공. loggedIn을 true로 설정했습니다.");
            return "redirect:/";
        } else {
            // 로그인 실패
        	System.out.println("로그인 실패. /login?error=...로 리다이렉트합니다.");
            return "redirect:/login";
        }
    }
	
	/*
	//mapping이 잘못되면 404에러가 난다.
	//어떤 html로 가져갈 데이터가 있다면 model을 사용한다.
			@GetMapping("/member/")
			public String findAll(Model model) {
				List<MemberDTO> memberDTOList = memberService.findAll();
				
				model.addAttribute("memberList",memberDTOList);
				return "list";
				
			}
			
			
			//상세 조회를 할 때의 컨트롤러(프로젝트에서는 구현하지 않음)
			@GetMapping("/member/{id}")
			public String findById(@PathVariable Long id, Model model) {
				//회원 1명을 받는 것이기 때문에, return타입이 그냥 dto이다.
				MemberDTO memberDTO = memberService.findById(id);
				model.addAttribute("member",memberDTO);
				return "detail";
			}
			
			//회원 삭제를 할 때의 컨트롤러(프로젝트에서는 구현하지 않음)
			@GetMapping("/member/delete/{id}")
			public String deleteById(@PathVariable Long id) {
				memberService.deleteById(id);
				return "redirect:/member/";
			}
			
			*/
			//로그아웃을 할 때의 컨트롤러이다.
			@GetMapping("/logout")
			public String logout(HttpSession session, Model model) {
				// 로그아웃을 시켜주는 함수이다.
			    session.invalidate();
				
				// Thymeleaf를 사용하여 로그인 여부에 대한 정보를 전달한다.
			    model.addAttribute("loggedIn", false);

			    // 로그아웃이 완료되면 index 페이지를 리턴한다.
			    return "redirect:/";
			}
			
			
			//데이터를 requestparam을 통해 받아온다.
			@PostMapping("/member/email-check")
			public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
				
				
				//이메일을 디비에서 체크하기 위해 memberservice에서 함수를 만든다.
				String checkResult = memberService.emailCheck(memberEmail);
				return checkResult;
				
	//			if(checkResult!=null) {
	//				return "ok";
	//			}else {
	//				return "no";
	//			}
			}
			
			//이메일을 확인해서 그에 해당하는 비밀번호를 반환. 위의 emailCheck와 같은 것이므로 이름을 바꾼다.
			@PostMapping("/member/get-password")
			public @ResponseBody String emailCheck1(@RequestParam("memberEmail") String memberEmail) {
				
				
				 try {
				        // 이메일로 회원 엔터티 찾기
				        MemberEntity memberEntity = memberService.getPasswordByEmail(memberEmail);

				        if (memberEntity != null) {
				            // 회원이 찾아지면 비밀번호 반환. 이렇게 반환한 코드를 html에서 javascript를 통해 보여준다.
				            return memberEntity.getPassword();
				        } else {
				            // 회원이 찾아지지 않으면 에러 메시지 반환
				            return "해당 이메일로 등록된 회원이 없습니다: " + memberEmail;
				        }
				    } catch (Exception e) {
				        // 예외 처리 (예: 로그 기록)
				        return "내부 서버 오류";
				    }
			
			}
			
			//change-password에서 받아온 email과 password를 새롭게 설정한다.
			//(value = "email") 이런 식으로 설정하지 않으면 오류가 발생할 수 있다.
			@PostMapping("/changepassword")
			public String handlePostChangePassword(@RequestParam(value = "email") String email, @RequestParam(value = "newPassword") String newPassword, RedirectAttributes redirectAttributes) {
			    if (memberService.changePassword(email, newPassword)) {
			        redirectAttributes.addFlashAttribute("successMessage", "비밀번호가 성공적으로 설정되었습니다.");
			        return "redirect:/"; // 메인 페이지로 리다이렉션
			    } else {
			        return "redirect:/error"; // 사용자를 찾을 수 없는 경우 에러 페이지로 리다이렉션 또는 다른 처리를 수행
			    }
			}
			
			
			@GetMapping("/changepassword")
			public String showChangePasswordForm() {
				return "changepassword";
			}
			
}
