package com.lsm.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lsm.blog.config.auth.PrincipalDetail;
import com.lsm.blog.service.BoardService;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;


//사용자가 요청 -> 응답(HTML파일로 ) 하는 Controller
@Controller
public class BoardController {
	
	@Autowired 
	public BoardService boardService;
	
	
	@GetMapping({"","/"})
	//한페이지당 3건의 데이터를 리턴받아 볼 예, id를 기준으로 하여, direction = Direction.DESC ,즉, 내림 순으로 정렬하여(최근글을 먼저볼수 있게 )  한페이지에 볼 size 를 정한다.
	public String index(Model model ,@PageableDefault(size = 3,sort = "id",direction = Direction.DESC ) Pageable pageable) {
		model.addAttribute("boards",boardService.글목록(pageable)); //model에 board 테이블에 있는 정보를 모두 넣어준다.
		//boards 모델이 index 라는 페이지로 넘어간다.
		return "index"; //viewResolver 작동! 해당 페이지로 모델의 정보를 전달해주는 역할.  + prefix, sufix도 붙여준다.
		// /WEB-INF/views/index.jsp
	}
	
	@GetMapping("board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/detail"; 
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "/board/updateForm";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		// /WEB-INF/views/index.jsp
		return "/board/saveForm";
	}


}
