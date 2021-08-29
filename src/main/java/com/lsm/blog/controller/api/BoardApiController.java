package com.lsm.blog.controller.api;

//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.blog.config.auth.PrincipalDetail;
import com.lsm.blog.dto.ResponseDto;
import com.lsm.blog.model.Board;
import com.lsm.blog.model.RoleType;
import com.lsm.blog.model.User;
import com.lsm.blog.service.BoardService;
import com.lsm.blog.service.UserService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board , @AuthenticationPrincipal PrincipalDetail principal ) {
		System.out.println("BoardApiController : save 호출됨 !");
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);//(200,1) // HttpStatus.OK 는 HttpStatus의 enum타입이다. // 200 == 정상반환 
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id  ) {
		System.out.println("BoardApiController : deleteById 호출됨 !");
		System.out.println("글삭제하기"+id);
	
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);//(200,1) // HttpStatus.OK 는 HttpStatus의 enum타입이다. // 200 == 정상반환 
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id,@RequestBody Board board   ) {
		System.out.println("BoardApiController : update 호출됨 !"); 
		System.out.println("글수정 하기"+id);
		boardService.글수정하기(id,board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);//(200,1) // HttpStatus.OK 는 HttpStatus의 enum타입이다. // 200 == 정상반환 
	}
	
	
}
