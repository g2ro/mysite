package mysite.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import mysite.security.Auth;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role="ADMIN") //모든 mappoing의 권한을 다음과 같이 설정하겠다.
@Controller
@RequestMapping("/admin")
public class AdminController {
	private final SiteService siteService;
	private final ServletContext servletContext;
	private final ApplicationContext applicationContext;
	
	public AdminController(SiteService siteService, ServletContext servletContext, ApplicationContext applicationContext) {
		this.siteService = siteService;
		this.servletContext = servletContext;
		this.applicationContext = applicationContext;
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
		
		// update servlet context bean
		servletContext.setAttribute("siteVo", siteService.getSite());
		
		//update application context bean
		SiteVo site = applicationContext.getBean(SiteVo.class);
		BeanUtils.copyProperties(siteService.getSite(), site);
		
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
