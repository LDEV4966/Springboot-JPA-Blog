package com.lsm.blog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lsm.blog.config.auth.PrincipalDetailService;


//빈 등록을 해야함 : IOC를 가능학게 하는 것.
//아래 3개는 스프링 시큐리티 세트 목록 이다! 
@Configuration // bean 등록 
@EnableWebSecurity // 시큐리티 필터 가 등록이 된다. (기존에 것에서 필터를 해당 파일에서 추가적으로 진행 )
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정주소로 접근을 하면 권한 및 인증을 미리 체크하겠다느 뜻.
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService ;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	
	@Bean // IOC화 시키기 
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가  대신 로그인을 해주는데 password를  가로채기하는데 
	// 해당 password가 뭘로 해쉬가 되어 있는지 알아야 같은 해쉬로 암호화 해서 DB에 있는 해쉬랑 비교할 수 있음.
	//아래 방식을 통해서 스프링부트의 시큐리티라이브내에서 패스워드 값 비교를 가능하게 한다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Request가 요청되면 "/auth/**"로 들어 오는 도메인은 항상 허용하고 나머지는 다 인증을 거쳐야 한다는 뜻.
		System.out.println("동작 인지 시큐리티 컨픽");
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는것이 좋음 ) -  시큐리티 실행시  서버에 전달 되는 통신은 crsf 토큰이 없으면 다 차단한다.
			.authorizeRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		.and()
			.formLogin()
			.loginPage("/auth/loginForm")
			.loginProcessingUrl("/auth/loginProc") //스프링 시큐리틱가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
			.defaultSuccessUrl("/");//정상 로그인 일 때 
			
	}

}
