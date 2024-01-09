package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dto.MemberDTO;
import entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import repository.MemberRepository;

@Service
@RequiredArgsConstructor 
@Component
public class MemberService {


	@Autowired
	private final MemberRepository memberRepository; 
	
	
	
	//memberRepository가 null인 상태에서 save 메서드를 호출하려 하면 오류가 난다. 그래서 이 코드를 추가.
	@Autowired
	public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
	
	
	@Autowired
	//받아온 데이터들을 저장하는 메소드이다.
	public void save(MemberDTO memberDTO) {

		//repository의 save 메서드 호출.(entity를 넘겨야 한다.)
		//1.dto를 entity로 변환한다.(dto 클래스에 구현)
		try{MemberEntity memberEntity=MemberEntity.toMemberEntity(memberDTO);
		memberRepository.save(memberEntity);
		}
		//2.save 메서드를 호출한다.(jpa에서 호출한다.)
		//3.이 쿼리를 통해 데이터베이스 내에서 쿼리를 만들어 주는 것이다.
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}


	public MemberDTO login(MemberDTO memberDTO) {
		// 로그인을 수행할 때 수행되는 함수이다.
		
		//1.회원이 입력한 아이디로 db에서 조회를 한다.
		//Optional은 memberentity를 한번 더 감싸는 개념이다.
		Optional<MemberEntity> ByEmail= memberRepository.findByEmail(memberDTO.getEmail());
		
		//조회 결과가 있다면
		if(ByEmail.isPresent()) {
			//일단 이 구문을 통해 데이터를 벗겨낸다.
			MemberEntity memberEntity = ByEmail.get();
			//2.db에서 조회한 비밀번호(entity)가 사용자가 입력한 비밀번호(dto)가 일치하는지 판단한다.
			if(memberEntity.getPassword().equals(memberDTO.getPassword())) {
				//비밀번호 일치
				//entity를 dto로 변환한 후 리턴해야 한다.(번거롭다.)
				//결국, 로그인을 성공했을 때만 dto에 뭘 담아서 주는 것이다.
				MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
				return dto;
			}
			else {
				//비밀번호 불일치(로그인 실패)
				return null;
			}
			
		}
		else {
			//3.없다면
			return null;
		}
		
		
		
	}


	//회원 조회를 위한 함수이다.
	public List<MemberDTO> findAll() {
		List<MemberEntity> memberEntityList = memberRepository.findAll();
		//entity list를 dto list로 변환해야 한다.
		List<MemberDTO> memberDTOList = new ArrayList<>();
		//하나하나 꺼낸다.
		for(MemberEntity memberEntity: memberEntityList) {
			memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
			
		}
		
		return memberDTOList;
	}


	//id로 회원을 찾는 함수. 프로젝트에는 별 쓸모가 없을듯.
	public MemberDTO findById(Long id) {
		// TODO Auto-generated method stub
		Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
		if(optionalMemberEntity.isPresent()) {
			//있느냐 없느냐에 따라 return이 달라짐.
			//entity를 dto로 변환해서 return함.
			return MemberDTO.toMemberDTO(optionalMemberEntity.get());
		}
		else
		{return null;}
	}


	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		memberRepository.deleteById(id);
		
	}


	public String emailCheck(String memberEmail) {
		//repository 함수를 통해 사용자가 입력한 이메일 값으로 조회를 한다.
		Optional<MemberEntity> byMemberEmail = memberRepository.findByEmail(memberEmail);
		if(byMemberEmail.isPresent()) {
			
			//이메일 값이 이미 있으면(회원이 중복되어 있으면)사용할 수 없다.
			
			return null;
		}
		
		else {
			//조회 결과가 없으면 사용할 수 있다.
			return "ok";
		}
		
		
		
	}


	public MemberEntity getPasswordByEmail(String memberEmail) {
		Optional<MemberEntity> byMemberEmail = memberRepository.findByEmail(memberEmail);
	    return byMemberEmail.orElse(null);
	}
	

	//Transactional 어노테이션은 여러 줄의 코드를 하나의 작업으로 처리해준다.
	//하나의 작업으로 처리해주면, 부분적으로 오류가 난 것을 같이 처리할 수 있다는 장점이 있다.
    @Transactional
    public boolean changePassword(String email, String newPassword) {
        Optional<MemberEntity> userOptional = memberRepository.findByEmail(email);

        if (userOptional.isPresent()) {
        	MemberEntity user = userOptional.get();
            user.setPassword(newPassword);
            memberRepository.save(user);
            return true;
        }

        return false;
    }

}
