package Honawa2.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import Honawa2.entity.Item;
import Honawa2.entity.QItem;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	@Override
	public Page<Item> getAdminItemPage(Pageable pageable) {
		List<Item> content = queryFactory
				.selectFrom(QItem.item) //select * from item
				.orderBy(QItem.item.id.desc())
				.offset(pageable.getOffset()) //데이터를 가져올 시작 index
				.limit(pageable.getPageSize()) //한번에 가지고 올 최대 개수
				.fetch();
		
		//https://querydsl.com/static/querydsl/4.1.0/apidocs/com/querydsl/core/types/dsl/Wildcard.html
		// Wildcard.count = count(*)
		long total = queryFactory.select(Wildcard.count).from(QItem.item)
                
                .fetchOne();
		
		
		return new PageImpl<>(content, pageable, total);
	}
 
}
