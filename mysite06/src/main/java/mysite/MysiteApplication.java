package mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import mysite.config.AppConfig;
import mysite.config.WebConfig;

@SpringBootApplication
//@Import({AppConfig.class, WebConfig.class}) // @Configuration 또한 설정 클래스로 scan이 된다 하지만 @SpringBootTest환경에선 scan이 진행되지 않는다.
public class MysiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysiteApplication.class, args);
	}

}
