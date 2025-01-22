package mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import mysite.config.app.DBConfig;
import mysite.config.app.MyBatisConfig;
import mysite.config.app.SecurityConfig;

@Configuration
//@EnableAspectJAutoProxy // 해당 기능도 자동으로 설정해준다.
//@EnableTransactionManagement // spring starter mybatis에 자동으로 설정해줌 
//@Import({DBConfig.class, MyBatisConfig.class, SecurityConfig.class}) // Configuration에 의해 자동 스캔을 진행함.
//@ComponentScan(basePackages = {"mysite.service", "mysite.repository", "mysite.component", "mysite.aspect"})
public class AppConfig {

}
