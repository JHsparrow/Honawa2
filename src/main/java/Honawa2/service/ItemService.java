package Honawa2.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import Honawa2.DTO.ItemFormDto;
import Honawa2.entity.Cart;
import Honawa2.entity.Item;
import Honawa2.repository.CartRepository;
import Honawa2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	private final ItemRepository itemRepository;
	private final CartRepository cartRepository;
	
	//상품 등록
	public Long saveItem(ItemFormDto itemFormDto) throws Exception {
		
		//상품등록
		Item item = itemFormDto.createItem();
		itemRepository.save(item);
		return item.getId();
	}
	
	@Transactional(readOnly = true) //트랜잭션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
	public ItemFormDto getItemDtl(Long itemId) {
		//1. item_img테이블의 이미지를 가져온다.
		
		//2. item테이블에 있는 데이터를 가져온다.
		Item item = itemRepository.findById(itemId)
				                  .orElseThrow(EntityNotFoundException::new);
		
		//엔티티 객체 -> dto객체로 변환
		ItemFormDto itemFormDto = ItemFormDto.of(item);
		
		return itemFormDto;
	}
	
	//상품 리스트 가져오기
	@Transactional(readOnly = true)
	public Page<Item> getAdminItemPage(Pageable pageable) {
		return itemRepository.getAdminItemPage(pageable);
	}
	
	public List<Cart> getCartCpu(String id) {return cartRepository.findCartCpu(id);	}
	public List<Cart> getCartRam(String id) {return cartRepository.findCartRam(id);	}
	public List<Cart> getCartBoard(String id) {return cartRepository.findCartBoard(id);}
	public List<Cart> getCartVga(String id) {return cartRepository.findCartVga(id);	}
	public List<Cart> getCartMemory(String id) {return cartRepository.findCartMemory(id);}
	public List<Cart> getCartPower(String id) {return cartRepository.findCartPower(id);}
	public List<Cart> getCartCase(String id) {return cartRepository.findCartCase(id);}
	
	
	
	public Long updateItem(ItemFormDto itemFormDto) throws Exception {
		Item item = itemRepository.findById(itemFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);   
		
		item.updateItem(itemFormDto);
		
		return item.getId();
	}
	
	public void deleteOrder(Long itemId) {
		Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
		itemRepository.delete(item);
	}
	
}
