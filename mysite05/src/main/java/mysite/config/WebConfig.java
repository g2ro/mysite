package mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import mysite.config.app.SecurityConfig;
import mysite.config.web.FileUploadConfig;
import mysite.config.web.LocaleConfig;
import mysite.config.web.MvcConfig;

@Configuration
@ComponentScan({"mysite.controller", "mysite.exception"})
@EnableAspectJAutoProxy
@Import({MvcConfig.class, LocaleConfig.class,FileUploadConfig.class})
public class WebConfig {
	
}
