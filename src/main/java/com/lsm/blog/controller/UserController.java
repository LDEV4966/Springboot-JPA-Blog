package com.lsm.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
//그냥 주소이면 / 이면 index.jsp 허용
// static 이하에 있는 /js/** , /css/**, /image/**

@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code ) { //  @ResponseBody : Data를 리턴해주는 컨트롤러 함수 
		//oauth 로부터 받은 code 값은 인증서버로 부터 인증을 받았다는 것을 의미한다.
		//code로 부터 oauth의 리소스 서버에 접근하여 해당 리소스 오너의 개인정보에  접근할 수 있는 토큰을 받울 수 있다 . 
		
		//Post방식으로 토큰을  key=value 데이터를 요청 (카카오 쪽으로 )
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 오브젝트 생성 
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8"); 
		
		//HttpBody 오브젝트 생성 
		MultiValueMap<String, String> parms = new LinkedMultiValueMap<>();
		parms.add("grant_type","authorization_code");
		parms.add("client_id","8938f98bcb5d5c357fb2e656163e237c");
		parms.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
		parms.add("code",code);
		
		//HttpHeader 오브젝트 , HttpBody 오브젝트 를 하나의 오브젝트에 담기 
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(parms,headers);
		
		
		//Http 요청하기 - POST 방식으로 그리고 resonse 변수의 응답을받음. 
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);
		
		return "카카오 token access 완료" + ":    "+ response ;
	}
}
