package mysite.config.app;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
	// spring-security-practices => configuration-filters => SecurityConfigEx05 복사
	

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    		.formLogin((formLogin) -> {
    			formLogin
    				.loginPage("/user/login");
    		})
    		.authorizeHttpRequests((authorizeRequests) -> {
    			/* ACL */
    			authorizeRequests.requestMatchers(new RegexRequestMatcher("^/user/update$", null))
    			.authenticated()
    			
    			.anyRequest()
    			.permitAll();
    		});
    	return http.build();
    }
}

//public class SecurityConfig implements WebMvcConfigurer{
//
//	//ArgumentResolver
//	@Bean
//	public HandlerMethodArgumentResolver HandlerMethodArgumentResolver() {
//		return new AuthUserHandlerMethodArgumentResolver();
//	}
//	
//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//		resolvers.add(HandlerMethodArgumentResolver());
//	}
//	
//	// Interceptors
//	@Bean
//	public LoginInterceptor loginInterceptor() {
//		return new LoginInterceptor();
//	}
//	
//	@Bean
//	public LogoutInterceptor logoutInterceptor() {
//		return new LogoutInterceptor();
//	}
//	
//	@Bean
//	public AuthInterceptor authInterceptor() {
//		return new AuthInterceptor();
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry
//			.addInterceptor(loginInterceptor())
//			.addPathPatterns("/user/auth");
//		
//		registry
//		.addInterceptor(logoutInterceptor())
//		.addPathPatterns("/user/logout");
//		
//		registry
//		.addInterceptor(authInterceptor())
//		.addPathPatterns("/**")
//		.excludePathPatterns("/user/auth", "/user/logout", "/assets/**");
//			
//	}
//}
