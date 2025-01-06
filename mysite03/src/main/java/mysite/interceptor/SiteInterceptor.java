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
	
	private LocaleResolver localeResolver;
	
	@Autowired
	private SiteService siteService;
	
	public SiteInterceptor(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String lang = localeResolver.resolveLocale(request).getLanguage();
		request.setAttribute("lang", lang);
		
		SiteVo siteVo = null;
		ServletContext application = request.getServletContext();

		if(application.getAttribute("siteVo") == null) {
			siteVo = siteService.getSite();
			application.setAttribute("siteVo", siteVo);
		}
		
		
		return true;
	}

}
