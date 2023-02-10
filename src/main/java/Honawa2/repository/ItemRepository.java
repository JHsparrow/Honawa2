package Honawa2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Honawa2.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom{
	
}
