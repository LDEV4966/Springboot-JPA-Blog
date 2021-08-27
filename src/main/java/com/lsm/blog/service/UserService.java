package com.lsm.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.blog.model.User;
import com.lsm.blog.repository.UserRepository;

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
	@Transactional // insert 할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (정합성 유지 )
	public void 회원가입(User user) {
			userRepository.save(user);
	}
	//전통적인 로그인 방식  
//	@Transactional(readOnly = true ) // select 할때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (정합성 유지 )
//	public User 로그인(User user) {
//			return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
//	}
}
