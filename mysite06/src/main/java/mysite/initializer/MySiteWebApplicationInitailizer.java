package mysite.initializer;

import java.util.ResourceBundle;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.Filter;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration.Dynamic;
import mysite.config.AppConfig;
import mysite.config.WebConfig;

public class MySiteWebApplicationInitailizer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		ResourceBundle bundle = ResourceBundle.getBundle("mysite.config.web.fileupload"); // bunble이라는 말에 properties가 담겨 있다.
		long maxFileSize = Long.parseLong(bundle.getString("fileupload.maxFileSize"));
		long maxRequestSize = Long.parseLong(bundle.getString("fileupload.maxRequestSize"));
		int fileSizeThresHold = Integer.parseInt(bundle.getString("fileupload.fileSizeThreshold"));
		MultipartConfigElement config = new MultipartConfigElement(null,maxFileSize, maxRequestSize, fileSizeThresHold);
		registration.setMultipartConfig(config);
	}

	@Override
	protected Filter[] getServletFilters() {
		
		return new Filter[] {new DelegatingFilterProxy("springSecurityFilterChain")}; //@EnableWebSecurity에 의해 자동으로 생성됨, SecurityConfigEx03을 통해 확인이 가능함.
	}
	
	
}
