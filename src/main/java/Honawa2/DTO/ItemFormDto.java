package Honawa2.DTO;


import javax.validation.constraints.*;

import org.modelmapper.ModelMapper;

import Honawa2.constant.ItemGubun;
import Honawa2.constant.ItemSellStatus;
import Honawa2.entity.Item;
import Honawa2.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFormDto {
	private Long id; 
	
	private String userId; 
	
	@NotBlank(message = "상품명은 필수 입력 값입니다.")
	private String itemName; 
	
	@NotNull(message = "가격은 필수 입력 값입니다.")
	private int price; //가격
	
	private int discount; // 할인
	
	@NotNull(message = "재고는 필수 입력 값입니다.")
	private int stockNumber; //재고수량
	
	@NotNull(message = "티어는 필수 입력 값입니다.")
	private int tier; //티어
	
	@NotNull(message = "전력은 필수 입력 값입니다.")
	private int watt; // 전력 
	
	private ItemGubun itemGubun; //상품 구분
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Item createItem() {
		return modelMapper.map(this, Item.class);
	}
	
	public static ItemFormDto of(Item item) {
		return modelMapper.map(item, ItemFormDto.class);
	}
}
