package dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.example.demo","com.example.demo.controller","dto","entity","repository","service"}) //지정한 패키지의 하위의 모든 패키지에서 어노테이션들을 찾아 빈으로 등록한다.
public class AppConfig {
	
	//이 클래스는 spring에서 빈을 설정하고 관리하는 역활을 하는 구성 클래스이다.
	 @Bean
	 public MemberDTO memberDTO() {
	        return new MemberDTO();
	    }
	

}
