package Honawa2.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
	private Long cartId;
	
	private Long itemId;
	
	private int price;
	
	private String gubun;
}
