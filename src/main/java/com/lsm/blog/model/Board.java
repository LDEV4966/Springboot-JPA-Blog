package com.lsm.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id; //primary_key
	
	@Column(nullable = false,length = 100)
	private String title;
	
	@Lob //대용량 데이터 
	private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인됨. -> 글자가 대용량이 됨. 
	
	@ColumnDefault("0")
	private int count;
	
	
	@ManyToOne(fetch = FetchType.EAGER) // Many = Board , One = User  연관관계 -> 한명의 유저는 여러개의 개시글을 쓸수있다. //FetchType.EAGER 은 클래스 호출시 무조건 가져오는 default option 값이다.
	@JoinColumn(name="userId") //객체 형태의 값을 DB에 기록할 때 필드 네임 
	private User userrId; //글을 작성한 유저를 기록하는 것.
	//객체는 DB에저장 되지 못한다. 그래서 FK로 DB에  저장한다. 하지만 자바는 이를 저장할 수 있다. 그런데 JPA로 ORM과정을 거칠때 어떻게 하느냐? 일때 위의 @JoinColumn으로  필드값을 형성해준다,
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER ) // mappedBy는 해당 값이 연관관계의 주인이 아니다 즉, 난 FK가 아니에요 ) 라는 의미이며 DB에도 저장되지 않는다. , Reply클래스의 필드에서 변수명이 "board"라는 것을 가져온다.
	private List<Reply> reply; //하나의board에 여러개의 답글이 달릴 수 있기에 List 자료형 사용. 즉 이 값이 DB에 FK로 저장 될 수 없는 이유이다. DB의 각 colum값은 제1 정규형에 의해 원자성을 가져야 하는데 이는 답글이 추가 될때 마다 변경 되기에 DB에 저장하지 못한다.그래서 mappedBy를 사용한다.
	
	@CreationTimestamp
	private Timestamp createDate;

}
