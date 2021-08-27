package com.lsm.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML파일로 ) 하는 Controller
//@Controller

// 사용자가 요청 -> 응답(Data) 하는 Controller
@RestController
public class HttpControllerTest {
	
	//인터넷 브라우저 요청 즉 아래처럼 경로로 인해 접근하는 것은 GET 요청 밖에 없다.
	
	//http://localhost:8080/http/lombok (select)
		@GetMapping("/http/lombok")
		 public String getLombokTest() { //Spring의 MessageConverter가 파싱함 
			Member m1 = new Member();
			Member m = Member.builder().username("aa").id(10).password("1234").build();
			 System.out.println(m.getUsername());
			 m.setUsername("asd");
			 System.out.println(m.getUsername());
			 return "getLombokTEST 요청 Clear";
		 }
	
	//http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	 public String getTest(Member m) { //Spring의 MessageConverter가 파싱함 
		 return "get 요청 : "+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	 }
	//http://localhost:8080/http/post (insert)
	@PostMapping("/http/post")
	 public String postTest(@RequestBody Member m) {
		return "post 요청 : "+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	 }
	//http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	 public String putTest() {
		 return "put 요청";
	 }
	//http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	 public String deleteTest() {
		 return "delete 요청";
	 }


}
