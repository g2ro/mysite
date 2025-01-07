package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import mysite.security.Auth;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role="ADMIN") //모든 mappoing의 권한을 다음과 같이 설정하겠다.
@Controller
@RequestMapping("/admin")
public class AdminController {
	private SiteService siteService;
	private final ServletContext servletContext;
	
	public AdminController(SiteService siteService, ServletContext servletContext) {
		this.siteService = siteService;
		this.servletContext = servletContext;
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
//			HttpServletRequest request
			) {
		
		siteService.updateSite(title, welcom, file1, description); // 해당 로직 작성 예정
		servletContext.setAttribute("siteVo", siteService.getSite());
		
//		ServletContext application = request.getServletContext();
//		SiteVo siteVo = (SiteVo) application.getAttribute("siteVo");
//		siteVo.setTitle(title);
//		application.setAttribute("siteVo", siteVo);
		
		return "redirect:/admin/main";
		
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
