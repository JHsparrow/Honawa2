package Honawa2.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Honawa2.entity.Item;
import Honawa2.repository.ItemRepository;
import Honawa2.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final ItemService itemService;
	
	
	@GetMapping(value = "/")
	public String main(Optional<Integer> page, Model model) {
		
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("userid",id);
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		
		Page<Item> items = itemService.getAdminItemPage(pageable);
		model.addAttribute("items", items);
		return "index";
	}
}
