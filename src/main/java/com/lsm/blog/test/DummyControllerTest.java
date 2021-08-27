package com.lsm.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lsm.blog.model.RoleType;
import com.lsm.blog.model.User;
import com.lsm.blog.repository.UserRepository;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;


@RestController //HTML  파일이 아니라 DATA를 반환하는 controller = RestController 
public class DummyControllerTest {
	
	//DummyControllerTest가 메모리에 뜰때 @Autowired는 spring이 현재 관리하고 있는 UserRepository를 찾아 자동으로 집어넣어준다.
	@Autowired  // 위의 말을 요약한게 ,  의존성 주입 -> DI
	private UserRepository userRepository;
	
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return "해당 아이디는 데이타베이스에 없습니다";
		}
		
		return "delete clear "+"id: "+id;
	}
	
	
	@Transactional //더티체킹  :  함수종료시 자동으로  영속화 되어있는 정보들의 변경 값을 확인학고 변경시  DB에 commit한다.(변경감지)
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id , @RequestBody User requestUser) { //json data request => Java Object : Message Converter
		System.out.println("id : "+id);
		System.out.println("password : "+requestUser.getPassword() );
		System.out.println("email : "+requestUser.getEmail() );
		System.out.println("id : "+id);
		
		//JPA는 DB에서 값을 가져올때 해당 데이터를 영속성 컨텍스트의 1차캐시에 영속화 시킨다. ->메모리 접근을 효율적으로 하게 함.!
		User user = userRepository.findById(id).orElseThrow(()->{ //유저정보를 가져오면서 영속화 시킨거임.
				return new IllegalArgumentException("수정에 실패했습니다 . ");
		});		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// userRepository.save(requestUser);  //save없이 @Transactional 로 더티체킹하여 업데이트하는것을 기본으로 함

		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건의 데이터를 리턴받아 볼 예, id를 기준으로 하여, direction = Direction.DESC ,즉, 내림 순으로 정렬하여  한페이지에 볼 size 를 정한다.
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable){
		
		Page<User> pagingUsers  = userRepository.findAll(pageable); 
//		if(pagingUsers.isLast()) {
//			
//		}
		List<User> users  = pagingUsers.getContent();
		return users;
	}
	
	
	// http://localhost:8000/blog/dummy/user/2
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//user/max+1을 찾으면 내가 데이터베이스에서 찾을 수 없자나 ? 그럼 null 값을 반환하겠지 그럼 프로그램에 문제가있지 않겠니  
		//그래서 Optional 로 User 객체를  감싸서 가져올테니 null인지 아닌지 판단해서 리턴해!
		//id가 있으면 user에 넣어주고 없으면 orElseThrow실행.
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException
				>() {
			//Supplier는 인터페이스이므로 new를 통해서 객체를 생성하지 못한다.생성하려면 인터페이스 내의 추상메서드를 모두 작성해주면 new가능하다.
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당유저는 없습니다. 아이디 : "+id);
			}
		});
		
		//람다식으로도  위의 구문 대처가능.
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당유저는 없습니다. ");
//		});
		
		// 요청 : 웹 브라우저
		// user = 자바객체 
		// json으로의 변환이 필요
		// 스프링부트 = MessageConverter가 응답시에 작동한다.
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 자바객체 를 Json으로 리턴하게 변환하여 웹 브라우저로 던져준다.
		return user;
	}
	
	// http://localhost:8000/blog/dummy/Join (요)
	@PostMapping("/dummy/Join")
	public String Join(User user) {
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user); // insert
		return "sign up clear";
	}
	
}
