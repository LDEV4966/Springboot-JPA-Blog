package com.lsm.blog.config.auth;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lsm.blog.model.User;
import com.lsm.blog.repository.UserRepository;

@Service // Bean등록 
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	// 스프링이 로그인 요청을 가로챌 때 유저 네임과 패스워드 2개의 변수를 가로채는데
	// 패스워드 부분(패스워트 틀린경우 등 )은 알아서 처리함
	// username 만 DB에 있는 지 확인하면 됨.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User principal = userRepository.findByUsername(username).orElseThrow(new Supplier<UsernameNotFoundException>() {
			@Override
			public UsernameNotFoundException get() {
				// TODO Auto-generated method stub
				return new UsernameNotFoundException("해당 사용자는 찾을 수 없습니다."+username);
			}
		});
		//UserDetails객체를 통해 스프링 시큐리티는 유저 오브젝트를 반환한다 따라서 반환타입 역시 UserDetails 이여야지 우리가 원하는 유저이름과 비밀번호를 세션에 등록 시킬 수 있다. 
		return new PrincipalDetail(principal); // 시큐리티의 세션에 유저정보가 저장이 됨. 
	}

}
