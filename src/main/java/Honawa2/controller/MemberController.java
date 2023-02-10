package Honawa2.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Honawa2.DTO.MemberFormDto;
import Honawa2.DTO.MessageDto;
import Honawa2.entity.Member;
import Honawa2.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequestMapping(value="/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final SessionManager sessionManager;
	
	@GetMapping(value="login")
	public String Login(Model model) {
		return "member/login";
	}
	
	@GetMapping(value = "/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/login";
	}
	
	@GetMapping(value = "/mypage")
	public String mypage(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("userInfo",memberService.findUserInfo(id));  
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/mypage";
	}
	
	@PostMapping(value = "/mypage") // (4) 
	public String memberUpdate(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			List<ObjectError> list =  bindingResult.getAllErrors();
            for(ObjectError e : list) {
                 System.out.println(e.getDefaultMessage());
            }
			return "redirect:/members/mypage";
		}
		try {
			memberService.updateMember(memberFormDto, passwordEncoder);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
			return "item/itemModify";
		}
		MessageDto message = new MessageDto("비밀번호 수정이 완료되었습니다.", "/members/mypage");
        return showMessageAndRedirect(message, model);
	}
	
	@GetMapping(value="logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
	}
	
	
	@GetMapping(value="join")
	public String join(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/join";
	}
	
	@PostMapping(value = "/joinResult")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "member/join";
		}
		
		try {			
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/join";
		}
		
		return "redirect:/";
	}
	 private String showMessageAndRedirect(final MessageDto params, Model model) {
	        model.addAttribute("params", params);
	        return "common/messageRedirect";
	 }
}
