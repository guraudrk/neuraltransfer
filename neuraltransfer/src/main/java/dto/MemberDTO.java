package dto;

import org.springframework.stereotype.Component;

import entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //기본생성자를 자동으로 만들어줌.
@ToString
@Component
public class MemberDTO {
	
	//위의 어노테이션들은 코드를 간략하게 하기 위한 어노테이션들이다.
	//위 어노테이션에 대한 패키지는 바로 lombok이다.
	private Long id; 
	private String email;
	private String password;
	private String passwordConfirm; // 추가된 필드
	private String filename; //이미지 저장을 위해 추가된 필드
	
	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


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


	public void setPassword(String password) {
		this.password = password;
	}


	//비밀번호 확인 로직을 위해 추가된 코드.
		public String getPasswordConfirm() {
	        return passwordConfirm;
	    }

		//비밀번호 확인 로직을 위해 추가된 코드.
	    public void setPasswordConfirm(String passwordConfirm) {
	        this.passwordConfirm = passwordConfirm;
	    }


	//entity를 dto로 변환할 때 이걸 쓴다.
	//번거롭기는 하지만, 이렇게 변환을 해야 더 효율적으로 쓸 수 있다.
	public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setPassword(memberEntity.getPassword());
        memberDTO.setFilename(memberEntity.getFilename());
        return memberDTO;
	

}


	
}
