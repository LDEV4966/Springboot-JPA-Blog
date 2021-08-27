package com.lsm.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lsm.blog.model.User;

//JpaRepository 를 상속한다. User객체를 저장하는데 primary key로 Interger 즉 정수를 받는다는 의미이다.
//자동으로 bean 등록이 된다 -> @Repository 어노테이션  생략가능 
//즉 IOC에 의해 스프링이 UserRepository를 component scan할 때 저장해둔다.
public interface UserRepository extends JpaRepository<User, Integer> {
	
	//JPA NAMING 쿼리
	//SELECT * FROM user WHERE username = ?1 AND password =?2;
//	User findByUsernameAndPassword(String username, String password);
	
	//위와같이 JPA NAMING 쿼리를 쓰면 아래 방식과 동일 하게 작동한다.
//	@Query(value = "SELECT * FROM user WHERE username = ?1 AND password =?2", nativeQuery = true)
//	User login(String username,String password);
	

}
