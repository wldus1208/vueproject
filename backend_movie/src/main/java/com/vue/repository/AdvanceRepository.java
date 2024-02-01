package com.vue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vue.entity.Advance;

public interface AdvanceRepository extends JpaRepository<Advance, Integer> {
	List<Advance> findByMemberId(int memberId);
	Advance findByMemberIdAndItemId(int memberId, int itemId);
}
