package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

	
	//jparepository를 상속받는다. 
	//첫번째 인자는 엔터티, 두번째 인자는 primary key의 자료형이다.
	
	
	//interface이기 때문에 추상 클레스를 정의할 수 있다.
	// 이메일로 회원 정보 조회 (select * from member_table where member_email=?)
    Optional<MemberEntity> findByEmail(String memberEmail);
}
