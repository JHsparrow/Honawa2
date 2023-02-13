package Honawa2.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Honawa2.DTO.CartDto;
import Honawa2.entity.Cart;
import Honawa2.entity.Item;
import Honawa2.entity.Member;
import Honawa2.repository.CartRepository;
import Honawa2.repository.ItemRepository;
import Honawa2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service //service 클래스의 역할
@Transactional //서비스 클래서에서 로직을 처리하다가 에러가 발생하면 로직을 수행하기 이전 상태로 되돌려 준다. 
@RequiredArgsConstructor
public class CartService {
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final CartRepository cartRepository;
	
	public Long cart(CartDto cartDto, String id) {
		
		String CuCheck = cartRepository.findCartGubun(id, cartDto.getGubun());
		
		if(CuCheck == null) {
			Item item = itemRepository.findById(cartDto.getItemId())
	                .orElseThrow(EntityNotFoundException::new);
			Member member = memberRepository.findById(id);		
			Cart cart = Cart.createCart(member, item);
			cartRepository.save(cart);
			
			return cart.getId();
		} else {
			Cart cart = cartRepository.findCartItemId(cartDto.getGubun(),id);
			Item item = itemRepository.findById(cartDto.getItemId()).orElseThrow(EntityNotFoundException::new);
			cart.updateItem(cartDto, item);
			
			return cart.getId();
		}
		
		
	}
	
	public void deleteCart(Long cartId) {
		Cart cart = cartRepository.findByItemId(cartId).orElseThrow(EntityNotFoundException::new);
		cartRepository.delete(cart);
	}
	
	public void deleteMyCart(Long cartId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);
		cartRepository.delete(cart);
	}
	
	public List<Cart> viewAll(String userid){
		List<Cart> cart = cartRepository.findAllList(userid);
		return cart;
	}
}
