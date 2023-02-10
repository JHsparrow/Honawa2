package Honawa2.entity;

import javax.persistence.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import Honawa2.DTO.MemberFormDto;
import Honawa2.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="user_info")
@Getter
@Setter
@ToString
public class Member {
	@Id
	@Column(name="user_id")
	private String id;
	
	private String name;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setId(memberFormDto.getId());
		member.setName(memberFormDto.getName());
		
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		member.setPassword(password);
		
		member.setRole(Role.USER);
		
		return member;
	}
	
	public void updateMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		this.id = memberFormDto.getId();
		this.name = memberFormDto.getName();
		this.password = passwordEncoder.encode(memberFormDto.getPassword());
		this.role = memberFormDto.getRole();
	}
}
