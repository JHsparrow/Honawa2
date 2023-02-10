package Honawa2.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import Honawa2.DTO.CartDelDto;
import Honawa2.DTO.CartDto;
import Honawa2.service.CartService;
import Honawa2.service.ItemService;
import Honawa2.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	
	private final ItemService itemService;
	private final MemberService memberService;
	private final CartService cartService;
	
	@PostMapping(value="/carts/new")
	public @ResponseBody ResponseEntity cart(@RequestBody @Valid CartDto cartDto, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Long result;

        try {
            result = cartService.cart(cartDto, id);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
       
    	return new ResponseEntity<Long>(result,HttpStatus.OK);
        
    }
	
	@PostMapping(value="/carts/delete/{itemId}")
	public @ResponseBody ResponseEntity DeleteCart(@PathVariable("itemId") Long itemId) {
		cartService.deleteCart(itemId);
		return new ResponseEntity<Long>((long) 123,HttpStatus.OK);
	}
	
	
}
