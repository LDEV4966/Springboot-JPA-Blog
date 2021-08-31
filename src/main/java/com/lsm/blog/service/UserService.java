package com.lsm.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.blog.model.RoleType;
import com.lsm.blog.model.User;
import com.lsm.blog.repository.UserRepository;

import ch.qos.logback.core.encoder.Encoder;

@Service//스프링이 컴포넌트를 통해서 Bean에 등록해줌. IoC를 해준다.
//서비스가 필요한 이유는 ? -> 1. 트랜잭션 관리를 위해 , 2. 서비스 의미 때문.
// 제공해 주어야 할 로직 내 각 구성 단위를 트랜잭션이라한다. 이러한 트랜잭션을 하나로 묶어주는 것이 서비스의 역할이다.
public class UserService {
	@Autowired 
	private UserRepository userRepository;
	
	/*
	 * 1. 트랜잭션의 성질
	▶ 원자성(Atomicity) - 한 트랜잭션 내에서 실행한 작업들은 하나로 간주한다. 즉, 모두 성공 또는 모두 실패. 
	▶ 일관성(Consistency) - 트랜잭션은 일관성 있는 데이타베이스 상태를 유지한다. (data integrity 만족 등.)
	▶ 격리성(Isolation)  - 동시에 실행되는 트랜잭션들이 서로 영향을 미치지 않도록 격리해야한다.
	▶ 지속성(Durability) - 트랜잭션을 성공적으로 마치면 결과가 항상 저장되어야 한다.
	
	스프링에서는 트랜잭션 처리를 지원하는데 그중 어노테이션 방식으로 @Transactional을 선언하여 사용하는 방법이 일반적이며, 선언적 트랜잭션이라 부른다.
	클래스, 메서드위에 @Transactional 이 추가되면, 이 클래스에 트랜잭션 기능이 적용된 프록시 객체가 생성된다.
	이 프록시 객체는 @Transactional이 포함된 메소드가 호출 될 경우, 
	PlatformTransactionManager를 사용하여 트랜잭션을 시작하고, 정상 여부에 따라 Commit 또는 Rollback 한다.
 */
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional(readOnly = true ) 
	public User 회원찾기(String username) {
		System.out.println(username);
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}

	@Transactional // insert 할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (정합성 유지 )
	public void 회원가입(User user) {
		
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	//전통적인 로그인 방식  
//	@Transactional(readOnly = true ) // select 할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (정합성 유지 )
//	public User 로그인(User user) {
//			return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
//	}
	
	@Transactional // insert 할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (정합성 유지 )
	public void 회원수정(User user) {
		// 수정시에는 영속성 컨텍스트에 User 오브젝트를 영속화 시키고, 영속화된 user 오브젝트를 수정 
		// select를 해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화를 위해서 
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update 쿼리를 넘겨준다. 
		User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원수정 실패 아이디를 찾을 수가 없습니다 . "); 
		});
		
		//Vaildate check -> oauth 유저가 서버로 유저정보  수정을  불가하게 막기 위한 Vaildate  check
		if(persistence.getOauth() == null || persistence.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistence.setPassword(encPassword);
			persistence.setEmail(user.getEmail());
		}
		//회원 수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 이 자동으로 DB에 넘어간다.
		// 영속화된 persistence 객체가 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
		
	}
	
	
}
