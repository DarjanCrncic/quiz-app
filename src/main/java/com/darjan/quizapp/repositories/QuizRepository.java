package com.darjan.quizapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.models.dtos.PieChartItemDTO;

public interface QuizRepository extends JpaRepository<Quiz, Long>{

	Page<Quiz> findAllByUserId(Long userId, Pageable pageable);
	
	@Query(value = "select avg(q.result) from quiz q join user u on u.id = q.user_id and u.username = ?1 group by q.user_id", nativeQuery = true)
	double getAverageScore(String username);

	@Query(value = "select count(category)/(select count(*) from quiz) as 'percentage', category as 'label' from quiz where user_id = ?1 "
			+ "group by category order by percentage desc", nativeQuery = true)
	List<PieChartItemDTO> getPieChartStats(Long userId);

}
