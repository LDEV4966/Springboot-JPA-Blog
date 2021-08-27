package com.lsm.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.blog.dto.ResponseDto;
import com.lsm.blog.model.RoleType;
import com.lsm.blog.model.User;
import com.lsm.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired // DI(의존성 주)
	private UserService userService;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user ) {
		System.out.println("UserApiController : save 호출됨 !");
		//자바오브젝트를 반환하면 Jackson은 이를 json으로 변환해서 리턴해준다. 따라서 js에서 이를 json 형식으로 읽을 수 있게 됨. 
		user.setRole(RoleType.USER);
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
	
}
