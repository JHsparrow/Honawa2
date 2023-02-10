package Honawa2.DTO;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

import Honawa2.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	private String id;

	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	private String password;
	
	@NotBlank(message = "비밀번호를 확인해주세요.")
	private String passwordcheck;

	public Role role;
	
}
