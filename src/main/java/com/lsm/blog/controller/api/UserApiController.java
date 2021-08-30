package com.lsm.blog.controller.api;

import javax.servlet.http.HttpSession;

//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.blog.config.auth.PrincipalDetail;
import com.lsm.blog.dto.ResponseDto;
import com.lsm.blog.model.RoleType;
import com.lsm.blog.model.User;
import com.lsm.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired // DI(의존성 주)
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user ) {
		System.out.println("UserApiController : save 호출됨 !");
		//자바오브젝트를 반환하면 Jackson은 이를 json으로 변환해서 리턴해준다. 따라서 js에서 이를 json 형식으로 읽을 수 있게 됨. 
		userService.회원가입(user); 
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);//(200,1) // HttpStatus.OK 는 HttpStatus의 enum타입이다. // 200 == 정상반환 
	}
	
	//전통적인 로그인 방식 !! 
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user , HttpSession session ) {
//		System.out.println("UserApiController : login 호출됨 !");
//		User principal = userService.로그인(user); // principal(접근주)
//		
//		if( principal != null) {
//			session.setAttribute("principal", principal); // 세션이 형성 
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);//(200,1) // HttpStatus.OK 는 HttpStatus의 enum타입이다. // 200 == 정상반환 
//	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user ) {
		System.out.println("UserApiController : update 호출됨 !");
		userService.회원수정(user); 
		//DB 는 변경 되었지만 세션값은  변경되지 않은 상태이기 때문에 우리 가 직접 세션 값을 변경 해줘야 한다. 
		 // 강제로 세션 값 바꾸기 
		//세션 재정의 
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);//(200,1) // HttpStatus.OK 는 HttpStatus의 enum타입이다. // 200 == 정상반환 
	}
	
}
