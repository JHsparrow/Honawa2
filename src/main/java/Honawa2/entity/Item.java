package Honawa2.entity;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import Honawa2.DTO.ItemFormDto;
import Honawa2.constant.ItemGubun;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Item extends BaseEntity {
	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //상품코드
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Member userId;
	
	
	@Column(nullable = false, length = 50)
	private String itemName; //상품명
	
	@Column(nullable = false, name = "price")
	private int price; //가격
	
	@Column(nullable = false, name = "discount")
	private int discount; //할인
	
	@Column(nullable = false)
	private int stockNumber; //재고수량
	
	@Column(nullable = false, name = "tier")
	private int tier; //티어
	
	@Column(nullable = false, name = "watt")
	private int watt; //전력
	
	@Enumerated(EnumType.STRING)
	private ItemGubun itemGubun;
	
	public void updateItem(ItemFormDto itemFormDto) {
		this.itemName = itemFormDto.getItemName();
		this.price = itemFormDto.getPrice();
		this.discount = itemFormDto.getDiscount();
		this.stockNumber = itemFormDto.getStockNumber();
		this.tier = itemFormDto.getTier();
		this.watt = itemFormDto.getWatt();
		this.itemGubun = itemFormDto.getItemGubun();
	}
	
	//상품의 재고 증가
	public void addStock(int stockNumber) {
		this.stockNumber += stockNumber;
	}
}
