package mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import mysite.security.Auth;
import mysite.security.AuthUser;
import mysite.service.UserService;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/user") // 공통으로 들어갈 것
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

//	@RequestMapping(value= "/join", method=RequestMethod.GET)
	@GetMapping("/join")
	public String join(@ModelAttribute("userVo") UserVo userVo) {
		return "user/join";
		
	}
	
//	@RequestMapping(value="/join", method=RequestMethod.POST)
	@PostMapping("/join")
	public String join(@ModelAttribute("userVo") @Valid UserVo userVo, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			Map<String, Object> map = result.getModel();
			model.addAllAttributes(map);
			
			return "user/join";
		}
		
		System.out.println(userVo);
		userService.join(userVo);
		System.out.println(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSueccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value= "/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	// 로그인처럼 보안과 관련된 것들은 controller 외부에서 진행하는 것이 추천됨.
//	@RequestMapping(value= "/login", method=RequestMethod.POST)
//	public String login(HttpSession session, UserVo userVo, Model model) {
//		UserVo authUser =userService.getUser(userVo.getEmail(), userVo.getPassword());
//		if(authUser == null) {
//			model.addAttribute("email", userVo.getEmail());
//			model.addAttribute("result", "fail");
//			return "user/login";
//		}
//		// login 처리
//		session.setAttribute("authUser", authUser);
//		return "redirect:/";
//	} // LoginInterceptor로 역할 대체
	
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/";
//	} // LogoutInterceptor로 역할 대
	
	
	@Auth
	@RequestMapping(value= "/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		UserVo userVo = userService.getUser(authUser.getId());
		
		model.addAttribute("vo", userVo);
		return "user/update";
	}
	
	@Auth
	@RequestMapping(value= "/update", method=RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo userVo) {
		userVo.setId(authUser.getId());
		userService.update(userVo);
		
		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}
	@RequestMapping("/auth")
	public void auth() {
	}
	@RequestMapping("/logout")
	public void logout() {
	}
}
