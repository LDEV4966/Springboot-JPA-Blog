package com.lsm.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.blog.dto.ReplySaveRequestDto;
import com.lsm.blog.model.Board;
import com.lsm.blog.model.Reply;
import com.lsm.blog.model.RoleType;
import com.lsm.blog.model.User;
import com.lsm.blog.repository.BoardRepository;
import com.lsm.blog.repository.ReplyRepository;
import com.lsm.blog.repository.UserRepository;


import ch.qos.logback.core.encoder.Encoder;

@Service //IoC 대상이다. 
public class BoardService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private BoardRepository boardRepository;
	
	@Autowired 
	private ReplyRepository replyRepository;
	
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
	
	@Transactional 
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		
		//전통적인 영속화로 처리하는 법
		
		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{ 
			return new IllegalArgumentException("댓글쓰기 실패  User 아이디를 찾을 수가 없습니다 . ");
		}); // 영속화  완료 : DB에서 가져온 데이터를 영속화 컨텍스트의 1차캐시에 저장 하여 변화를 감지하거나 접근을  용이하게 한다. 
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글쓰기 실패 Board 아이디를 찾을 수가 없습니다 . ");
		}); // 영속화  완료 
		
		Reply reply = Reply.builder()
				.user(user)
				.board(board)
				.content(replySaveRequestDto.getContent())
				.build();
		replyRepository.save(reply);
		
//		//전통적인 영속화로 처리하는 법 대신 커스터마이즈 쿼리 만들어서 짧게 처리하는 법 
//		int customizedSaveRes = replyRepository.customizedSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(),replySaveRequestDto.getContent());
//		System.out.println("댓글쓰기 customizedSave 결과 : " + customizedSaveRes); // JDBC는 쿼리에 대한 결과 값으로 바뀐 행의 수를 출력한다. 
		
	}
	@Transactional 
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
}
