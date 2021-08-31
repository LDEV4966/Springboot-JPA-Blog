package com.lsm.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data //getter,setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//ORM 이란 JPA를 통해 해당 언어의 Object를 관계형데이터베이스(테이블)로 Mapping 시키는 것을 의미. 
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert // JpaRepository insert 시 null 인 필드값은 제외 시켜준다. -> role 필드 default 를 user로 설정했기에 null값이 다시 들어가는 걸 막아준다.
public class User {

	@Id  //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB(MySql의 auto_increment) 넘버링 전략을 따라간다.
	private int id; // auto_increment
	
	@Column(nullable = false, length = 100, unique = true)
	private String username;
	
	@Column(nullable = false, length = 100) //비밀번호를 해쉬하여 암호화하여 넣기때문에 길이가 넉넉해야 함.
	private String password;
	
	@Column(nullable = false, length = 50) 
	private String email;
	
	//@ColumnDefault("user") -> 실수 유발가능성 이 높다 따라서 enum으로 해주는게 더 좋은 방안.
	//DB는RoleType 이라는게 없다 따라서 아래의 어노테이션을 통해 해당 타입이 스트링임을 알려주어야한다.
	@Enumerated(EnumType.STRING)
	private RoleType role;  //Enum을 쓰는게 좋다 -> 도메인(어떤범위를 설정하는 것을 말함, 예를 들어 성별이면 남자,여자 )을 쓰기 위해 // admin,user,manager ..
	
	private String oauth; // kakao,login
	
	@CreationTimestamp //시간이 자동 입력 
	private Timestamp createDate;
	
}