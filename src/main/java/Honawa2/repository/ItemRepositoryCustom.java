package Honawa2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import Honawa2.entity.Item;

public interface ItemRepositoryCustom {
	Page<Item> getAdminItemPage(Pageable pageable);
}
