package Honawa2.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import Honawa2.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>  {
	
	
	Member findById(String id);
	
	
}
