package com.lsm.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsm.blog.model.KakaoProfile;
import com.lsm.blog.model.OAuthToken;
import com.lsm.blog.model.User;
import com.lsm.blog.service.UserService;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
//그냥 주소이면 / 이면 index.jsp 허용
// static 이하에 있는 /js/** , /css/**, /image/**

@Controller
public class UserController {
	
	@Value("${lsm.key}")
	private String lsmKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

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
	public String kakaoCallback(String code ) { //  @ResponseBody : Data를 리턴해주는 컨트롤러 함수 
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
		//response로 부터 받은 객체에 accessToken으로 부터  리소스 서버에 접근하여 해당 리소스 오너의 개인정보에  접근가능하다. 
		
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token() );
		
		//Post방식으로 토큰을  key=value 데이터를 요청 (카카오 쪽으로 )
		RestTemplate rt2 = new RestTemplate();
				
		//HttpHeader 오브젝트 생성 
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token()); 	
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); 
		//HttpHeader 오브젝트 , HttpBody 오브젝트 를 하나의 오브젝트에 담기 
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
		//Http 요청하기 - POST 방식으로 그리고 resonse 변수의 응답을받음. 
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest2, String.class);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// User obj : username, password , email 
		System.out.println("카카오 아이디 : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그 서버 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("블로그 서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		UUID garbagePassword = UUID.randomUUID(); 
		System.out.println("블로그 서버 패스워드 : " + lsmKey);
		
		User kakaoUser = User.builder()
				.username( kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				.password(lsmKey)
				.email( kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		if(originUser.getUsername() == null) {
			System.out.println("기존 회원이 아닙니다..........회원가입 진행...");
			userService.회원가입(kakaoUser);
		} 
		//세션 등록 -> 로그인 처리 
		System.out.println("로그인  진행...");
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),lsmKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		return "redirect:/";
	}
}
