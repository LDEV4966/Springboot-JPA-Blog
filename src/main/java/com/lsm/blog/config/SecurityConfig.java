package com.lsm.blog.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//빈 등록을 해야함 : IOC를 가능학게 하는 것.
//아래 3개는 스프링 시큐리티 세트 목록 이다! 
@Configuration // bean 등록 
@EnableWebSecurity // 시큐리티 필터 가 등록이 된다. (기존에 것에서 필터를 해당 파일에서 추가적으로 진행 )
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정주소로 접근을 하면 권한 및 인증을 미리 체크하겠다느 뜻.
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Request가 요청되면 "/auth/**"로 들어 오는 도메인은 항상 허용하고 나머지는 다 인증을 거쳐야 한다는 뜻.
		System.out.println("동작 인지 시큐리티 컨픽");
		http
			.authorizeRequests()
			.antMatchers("/auth/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		.and()
			.formLogin()
			.loginPage("/auth/loginForm");
	}

}
