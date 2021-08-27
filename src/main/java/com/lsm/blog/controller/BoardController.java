package com.lsm.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//사용자가 요청 -> 응답(HTML파일로 ) 하는 Controller
@Controller
public class BoardController {
	
	@GetMapping({"","/"})
	public String index() {
		// /WEB-INF/views/index.jsp
		return "index";
	}

}
