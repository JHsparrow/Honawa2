package Honawa2.controller;

import java.security.Principal;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Honawa2.DTO.ItemFormDto;
import Honawa2.DTO.MemberFormDto;
import Honawa2.DTO.MessageDto;
import Honawa2.constant.ItemGubun;
import Honawa2.entity.Item;
import Honawa2.entity.Member;
import Honawa2.service.CartService;
import Honawa2.service.ItemService;
import Honawa2.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {
	
	private final ItemService itemService;
	private final MemberService memberService;
	private final CartService cartService;
	
	@GetMapping(value="/items/list")
	public String list(Optional<Integer> page, Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 300);
		Page<Item> items = itemService.getAdminItemPage(pageable);
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		
		model.addAttribute("items", items);
		model.addAttribute("cartCpu", itemService.getCartCpu(id));
		model.addAttribute("cartRam", itemService.getCartRam(id));
		model.addAttribute("cartBoard", itemService.getCartBoard(id));
		model.addAttribute("cartVga", itemService.getCartVga(id));
		model.addAttribute("cartMemory", itemService.getCartMemory(id));
		model.addAttribute("cartPower", itemService.getCartPower(id));
		model.addAttribute("cartCase", itemService.getCartCase(id));
		
		return "item/list";
	}
	
	@GetMapping(value="/items/view")
	public String view(Model model) {
		return "item/view";
	}
	
	@GetMapping(value="/items/payment")
	public String payment(Model model) {
		return "item/payment";
	}
	
	@GetMapping(value = "/admin/item/new")
	public String itemNew(Model model) {
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("userid",id);
		model.addAttribute("itemFormDto", new ItemFormDto());
		
		return "item/itemForm";
	}
	
	@PostMapping(value = "/admin/item/new")
	public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, 
			Model model) {
		if(bindingResult.hasErrors()) {
			return "item/itemForm";
		}
		try {
			itemService.saveItem(itemFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			return "item/itemForm";
		}
		MessageDto message = new MessageDto("상품 생성이 완료되었습니다.", "/items/list");
        return showMessageAndRedirect(message, model);
	}
	
	@GetMapping(value = {"/admin/items", "/admin/items/{page}"}) 
	public String itemManage(@PathVariable("page") Optional<Integer> page, Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3); 
		
		Page<Item> items = itemService.getAdminItemPage(pageable);
		
		model.addAttribute("items", items);
		
		return "item/itemMng";
//		return items;
	}
	
	//상품 상세 페이지
	@GetMapping(value = "/items/view/{itemId}")
	public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
		ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
		model.addAttribute("item", itemFormDto);
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("userid",id);
		return "item/itemDtl";
	}
	
	@GetMapping(value = "/items/modify/{itemId}")
	public String itemModify(@PathVariable("itemId") Long itemId, Model model) {
		try {
			ItemFormDto itemFormdto = itemService.getItemDtl(itemId);
			model.addAttribute(itemFormdto);
			model.addAttribute("gubun",ItemGubun.values());
		} catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			model.addAttribute("itemFormDto", new ItemFormDto());
			return "item/list";
		}
		
		return "item/itemModify";
	}
	
	@PostMapping(value = "/items/modify/{itemId}")
	public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model, @PathVariable("itemId") Long itemId) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
			return "item/itemModify";
		}
		
		try {
			itemService.updateItem(itemFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
			return "item/itemModify";
		}
		MessageDto message = new MessageDto("상품 수정이 완료되었습니다.", "/items/view/"+itemId);
        return showMessageAndRedirect(message, model);
	}
	
	 @GetMapping("/items/delete/{itemId}")
	    public String deleteOrder(@PathVariable("itemId") Long itemId, Model model) {
		 	cartService.deleteCartInItem(itemId);
	    	itemService.deleteOrder(itemId);
	    	MessageDto message = new MessageDto("상품 삭제가 완료되었습니다.", "/items/list");
	        return showMessageAndRedirect(message, model);
	    }
	 
	
	
	
	 private String showMessageAndRedirect(final MessageDto params, Model model) {
	        model.addAttribute("params", params);
	        return "common/messageRedirect";
	 }
	
}
