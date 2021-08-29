package com.lsm.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.blog.model.Board;
import com.lsm.blog.model.RoleType;
import com.lsm.blog.model.User;
import com.lsm.blog.repository.BoardRepository;
import com.lsm.blog.repository.UserRepository;


import ch.qos.logback.core.encoder.Encoder;

@Service
public class BoardService {
	@Autowired 
	private BoardRepository boardRepository;
	
	@Transactional 
	public void 글쓰기(Board board , User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
		
	}
	
	@Transactional (readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		// TODO Auto-generated method stub
		return boardRepository.findAll(pageable); // Pageable 반환 
	}
	
	@Transactional (readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(()->{
				return new IllegalArgumentException("글 상세보기 실패 아이디를 찾을 수가 없습니다 . ");
			});
	}
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 아이디를 찾을 수가 없습니다 . ");
		}); // 영속화  완료 
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시 (Service사 종료될때 ) 트랜잭션 종료 -> 더티체킹으로 인해 자동으로 영속화된 기존의 컨텐츠와 비교후  변화 감지시  db flush . 
	}
}
