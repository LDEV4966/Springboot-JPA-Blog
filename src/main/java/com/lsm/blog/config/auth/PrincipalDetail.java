package com.lsm.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lsm.blog.model.User;
import com.lsm.blog.repository.UserRepository;

import lombok.Getter;

// 스프링 시큐리티가 로그인요청을 가로채서 로그인을진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
@Getter
public class PrincipalDetail implements UserDetails{
	private User user; //composition
	
	public User getUser() {
		return user;
	}

	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	//계정이 만료 되었는지 확인 true => 만료안됨 
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	//비밀 번호가  만료 되었는지 확인 true => 만료안됨 
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 활성화가 되었는지  확인 true => 활성화됨  
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true ;
	}
	
	//계정이 가직고 있는 권한 목록을 리턴한다.  ( 권한이 여럭개 있을 수 있어서 루프를 돌아야 하는데 우리는  한개만 가지고 있다 )
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Collection<GrantedAuthority> collectors = new ArrayList<>();
//		collectors.add(new GrantedAuthority() {
//			
//			@Override
//			public String getAuthority() {
//				// TODO Auto-generated method stub
//				return "ROLE_" + user.getRole(); // ROLE_USER
//			}
//		});
		//Lamda 식 으로 표현 가능 : 익명 클래스 내에 추상메소드가 하나 밖에 없기 때문에 생략가능 .
		collectors.add(()->{return "ROLE_" + user.getRole();  });
		return collectors;
	}

}
