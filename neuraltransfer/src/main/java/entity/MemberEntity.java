package entity;

import dto.MemberDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name ="member_table")
public class MemberEntity {

	//Table이라는 어노테이션은 데이터베이스에 해당 이름의 테이블이 자동으로 생기도록 해준다.
	//이 엔티티 클래스가 테이블의 역활을 한다.
	
	
	@Id //pk 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가
	private Long id;
	
	
	//실제로 사용자가 적었던 id
	@Column(unique = true) // unique 제약조건 추가
	private String email;
	
	@Column
	private String password;
	
	@Column
	private String filename;
	
	
	
	
	



	public Long getId() {
		return id;
	}







	public void setId(Long id) {
		this.id = id;
	}







	public String getEmail() {
		return email;
	}







	public void setEmail(String email) {
		this.email = email;
	}







	public String getPassword() {
		return password;
	}
	
	







	public String getFilename() {
		return filename;
	}







	public void setFilename(String filename) {
		this.filename = filename;
	}







	public void setPassword(String password) {
		this.password = password;
	}

	//dto을 entity로 변동해준다.
	public static MemberEntity toMemberEntity(MemberDTO MemberDTO) {
		MemberEntity memberEntity = new MemberEntity();
		memberEntity.setId(MemberDTO.getId());
		memberEntity.setEmail(MemberDTO.getEmail());
		memberEntity.setPassword(MemberDTO.getPassword());
		memberEntity.setFilename(MemberDTO.getFilename());

		return memberEntity;
		
	}
}
