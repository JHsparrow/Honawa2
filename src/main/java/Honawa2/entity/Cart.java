package Honawa2.entity;

import javax.persistence.*;

import Honawa2.DTO.CartDto;
import Honawa2.DTO.ItemFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart") //테이블명
@Getter
@Setter
@ToString
public class Cart {
	@Id
	@Column(name="cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Member userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	private int orderPrice; 
	
	public void updateItem(CartDto cartDto, Item item) {
		System.out.println("item : " + item);
		this.item = item;
		System.out.println("찾는당 3-2");
		this.orderPrice = cartDto.getPrice();
		System.out.println("찾는당 3-3");
	}
	

	public static Cart createCart(Member member, Item item) {
		Cart cart = new Cart();
		cart.setUserId(member);
		cart.setItem(item);
		cart.setOrderPrice(item.getPrice());
		
		return cart;
	}
}
