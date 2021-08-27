package com.lsm.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.blog.dto.ResponseDto;

@ControllerAdvice //모든 Exception이 발생했을 때 이 클래스로 들어오게하는 어노테이션 
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class) // Exception을 핸들러 하기 위한 것.
	public ResponseDto<String > handleArgumentException(Exception e) {
		 return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
	}

}
