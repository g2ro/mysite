package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import mysite.security.Auth;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role="ADMIN") //모든 mappoing의 권한을 다음과 같이 설정하겠다.
@Controller
@RequestMapping("/admin")
public class AdminController {
	private SiteService siteService;
	
	public AdminController(SiteService siteService) {
		this.siteService = siteService;
	}
	
	@RequestMapping({"", "/main"})
	public String main(Model model) {
		model.addAttribute("siteVo", siteService.getSite());
		//model 수정
		return "admin/main";
	}
	@RequestMapping("/main/update")
	public String mainUpdate(
			@RequestParam("title") String title,
			@RequestParam("welcomeMessage") String welcom,
			@RequestParam("file1") MultipartFile file1,
			@RequestParam("description") String description
			) {
		
		siteService.updateSite(title, welcom, file1, description); // 해당 로직 작성 예정
		return "redirect:/";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
