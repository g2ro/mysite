package mysite.config.app;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.repository.UserRepository;
import mysite.security.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
	// spring-security-practices => configuration-filters => SecurityConfigEx05 복사
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
	    return webSecurity -> webSecurity.httpFirewall(new DefaultHttpFirewall());
	}
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    		.csrf(csrf -> 
    			csrf.disable() // post를 받을 때 자신이 보낸 post가 맞는지 확인하는 역할
    			)
    		.formLogin(formLogin -> {
    			formLogin
    				.loginPage("/user/login")
    				.loginProcessingUrl("/user/auth")
    				.usernameParameter("email")
    				.passwordParameter("password")
    				.defaultSuccessUrl("/")
//    				.failureUrl("/user/login?result=fail");
    				.failureHandler(new AuthenticationFailureHandler() {
						@Override
						public void onAuthenticationFailure(
								HttpServletRequest request, 
								HttpServletResponse response,
								AuthenticationException exception) throws IOException, ServletException {
								request.setAttribute("email", request.getParameter("email"));
								request.setAttribute("result", "fail");
								
								// Servlet Handler에서 foward 사용하는 방식이지만, SecurityContextHolderAwareRequestFilter에 의해 controller로 바로 보내지게 된다.
								// 생각해보기
								request
									.getRequestDispatcher("/user/login")
									.forward(request, response);
							
						}
    					
    				});
    		})
    		
    		.logout(logout->{
    			logout
    				.logoutUrl("/user/logout")
    				.logoutSuccessUrl("/");
    		})
    		.authorizeHttpRequests((authorizeRequests) -> {
    			/* ACL */
    			authorizeRequests.requestMatchers(new RegexRequestMatcher("^/user/update$", null))
//    			.authenticated() -> 인증만 있으면 됨
    			.hasAnyRole("ADMIN", "USER") // -> 사용자 지만 role 기반으로 구분함.
    			
    			.requestMatchers(new RegexRequestMatcher("^/admin/?.*$", null))
    			.hasAnyRole("ADMIN")
    			
    			.requestMatchers(new RegexRequestMatcher("^/board/?(write|modify|delete|reply)$", null))
    			.hasAnyRole("ADMIN", "USER")
    			
    			.anyRequest()
    			.permitAll();
    		})
    		.exceptionHandling(exceptionHandling -> {
//    			exceptionHandling.accessDeniedPage("/WEB-INF/views/errors/403.jsp");
    			exceptionHandling.accessDeniedHandler(new AccessDeniedHandler() {
					
					@Override
					public void handle(
							HttpServletRequest request, 
							HttpServletResponse response,
							AccessDeniedException accessDeniedException) throws IOException, ServletException {
						
						response.sendRedirect(request.getContextPath());
						
					}
				});
    		});
    	return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	
    	authenticationProvider.setPasswordEncoder(passwordEncoder);
    	authenticationProvider.setUserDetailsService(userDetailsService);
    	
    	return new ProviderManager(authenticationProvider);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(4); /*4 ~ 31 */ // 해쉬하는 횟수
    }
    
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
    	return new UserDetailsServiceImpl(userRepository);
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
