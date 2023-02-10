package Honawa2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Honawa2.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>, ItemRepositoryCustom{
	@Query(value = "select A.user_id ,B.item_gubun from cart A inner join item B on a.item_id = b.item_id where  B.item_gubun = ?2 and A.user_id = ?1", nativeQuery = true)
	String findCartGubun(@Param("userid") String userid, @Param("gubun") String gubun);
	
	@Query(value="select * from cart A inner join item B on a.item_id = b.item_id where A.user_id = ?1 and B.item_gubun = 'CPU' ",nativeQuery = true)
	List<Cart> findCartCpu(@Param("userid") String userid);

	@Query(value="select * from cart A inner join item B on a.item_id = b.item_id where A.user_id = ?1 and B.item_gubun = 'RAM' ",nativeQuery = true)
	List<Cart> findCartRam(@Param("userid") String userid);
	
	@Query(value="select * from cart A inner join item B on a.item_id = b.item_id where A.user_id = ?1 and B.item_gubun = 'BOARD' ",nativeQuery = true)
	List<Cart> findCartBoard(@Param("userid") String userid);
	
	@Query(value="select * from cart A inner join item B on a.item_id = b.item_id where A.user_id = ?1 and B.item_gubun = 'VGA' ",nativeQuery = true)
	List<Cart> findCartVga(@Param("userid") String userid);

	@Query(value="select * from cart A inner join item B on a.item_id = b.item_id where A.user_id = ?1 and B.item_gubun = 'MEMORY' ",nativeQuery = true)
	List<Cart> findCartMemory(@Param("userid") String userid);

	@Query(value="select * from cart A inner join item B on a.item_id = b.item_id where A.user_id = ?1 and B.item_gubun = 'POWER' ",nativeQuery = true)
	List<Cart> findCartPower(@Param("userid") String userid);

	@Query(value="select * from cart A inner join item B on a.item_id = b.item_id where A.user_id = ?1 and B.item_gubun = 'CASE' ",nativeQuery = true)
	List<Cart> findCartCase(@Param("userid") String userid);
	
	@Query(value="select * from cart A inner join item B on a.item_id = b.item_id where b.item_gubun = ?1  and A.user_id = ?2 ",nativeQuery = true)
	Cart findCartItemId(@Param("gubun") String gubun, @Param("userid") String userid);
	
	Optional<Cart> findByItemId(Long itemId);
}
