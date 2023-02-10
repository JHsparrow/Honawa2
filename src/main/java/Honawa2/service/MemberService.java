package Honawa2.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Honawa2.DTO.ItemFormDto;
import Honawa2.DTO.MemberFormDto;
import Honawa2.entity.Item;
import Honawa2.entity.Member;
import Honawa2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional 
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	private final MemberRepository memberRepository;
	
	@Override	
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Member member = memberRepository.findById(id);
		if(member == null) {
			throw new UsernameNotFoundException(id);
		}
		//userDetails의 객체를 반환
		return User.builder()
				.username(member.getId())
				.password(member.getPassword())
				.roles(member.getRole().toString())
				.build();
		}
	
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member); //member 테이블에 insert
	}
	
	public String updateMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder ) throws Exception {
		Member member = memberRepository.findById(memberFormDto.getId());
		
		member.updateMember(memberFormDto, passwordEncoder);
		
		return member.getId();
	}
	
	
	
	public Member findUserInfo(String id) {
		return memberRepository.findById(id);
	}
	
	//이메일 중복체크 메소드
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findById(member.getId());
		if (findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}
	
	
	
}
