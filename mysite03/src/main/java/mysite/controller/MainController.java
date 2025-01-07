package mysite.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.servlet.LocaleResolver;
//import jakarta.servlet.http.HttpServletRequest;
//import mysite.service.SiteService;
//import mysite.vo.SiteVo;

@Controller
public class MainController {
//	@Autowired
//	ApplicationContext applicationContext;
	
	@RequestMapping({"/", "/main"})
	public String index(Model model) {
//		SiteVo vo = applicationContext.getBean(SiteVo.class);
//		System.out.println(vo);

		return "main/index";
	}
}
