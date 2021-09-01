package com.lsm.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lsm.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	
	//벌크 연산 @Modifying을 명시해줘야 한다.
	//기존 벌크 연산처럼 영향받은 엔티티의 개수를 반환한다.
	//	알다시피 벌크 연산은 영속성 컨텍스트를 무시한다.
	//	벌크 연산후에 영속성 컨텍스트를 초기화하고 싶으면 clearAutomatically 옵션을 true로 주면 된다. 기본값은 false이다
	@Modifying
	@Query(value = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())",nativeQuery = true)
	int customizedSave(int userId, int boardId , String content );
}
