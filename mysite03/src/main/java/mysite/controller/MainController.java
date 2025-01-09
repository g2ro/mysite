package mysite.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mysite.vo.UserVo;

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
	} // MVC -> View Resolver 방식
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "Hello World";
	} // ->StringHttpMessageConverter
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02() {
		return "안녕 세상";
	} // ->StringHttpMessageConverter
	
	@ResponseBody
	@RequestMapping("/msg03")
	public Object message03() {
		UserVo vo = new UserVo();
		vo.setId(10L);
		vo.setName("둘리");
		vo.setEmail("doly@gmail.com");
		return vo;
	}
}
