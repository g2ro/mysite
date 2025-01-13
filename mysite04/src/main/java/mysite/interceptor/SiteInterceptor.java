package mysite.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	@Autowired
	private LocaleResolver localeResolver;
	
	@Autowired
	private SiteService siteService;
	
	
//	public SiteInterceptor(LocaleResolver localeResolver, SiteService siteService) {
//		this.localeResolver = localeResolver;
//		this.siteService = siteService;
//	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		
		//locale
		String lang = localeResolver.resolveLocale(request).getLanguage();
		request.setAttribute("lang", lang);
		
		//siteVo
		SiteVo siteVo = (SiteVo) request.getServletContext().getAttribute("siteVo");
		if(siteVo == null) {
			siteVo = siteService.getSite();
			request.getServletContext().setAttribute("siteVo", siteVo);
		}
		
//		SiteVo siteVo = null;
//		ServletContext application = request.getServletContext();
//
//		if(application.getAttribute("siteVo") == null) {
//			siteVo = siteService.getSite();
//			application.setAttribute("siteVo", siteVo);
//		}
		
		
		return true;
	}

}
