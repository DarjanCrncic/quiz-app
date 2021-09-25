package com.darjan.quizapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.darjan.quizapp.models.Quiz;
import com.darjan.quizapp.models.dtos.AverageStatsDTO;
import com.darjan.quizapp.models.dtos.LeaderboardDTO;
import com.darjan.quizapp.models.dtos.PlayerAvgScore;
import com.darjan.quizapp.models.dtos.ResultChartItemDTO;

public interface QuizRepository extends JpaRepository<Quiz, Long>{

	Page<Quiz> findAllByUserId(Long userId, Pageable pageable);
	
	@Query(value = "select avg(q.result) as 'result', q.user_id as 'userId' from quiz q join user u on u.id = q.user_id and u.username = ?1 group by q.user_id", nativeQuery = true)
	PlayerAvgScore getAverageScore(String username);

	@Query(value = "select count(category)/(select count(*) from quiz) as 'percentage', category as 'label' from quiz where user_id = ?1 "
			+ "group by category order by percentage desc", nativeQuery = true)
	List<ResultChartItemDTO> getPieChartStats(Long userId);

	@Query(value = "select sum(result)/count(result) as 'percentage', category as 'label' from quiz where user_id = ?1 "
			+ "group by category order by percentage desc", nativeQuery = true)
	List<ResultChartItemDTO> getAverageQuizResultByCategory(Long userId);
	
	@Query(value = "select sum(result)/count(result) as 'avgScore', count(category)/(select count(*) from quiz) as 'playRate', category as 'label' from quiz where user_id = ?1 "
			+ "group by category order by avgScore desc", nativeQuery = true)
	List<AverageStatsDTO> getAverageStats(Long userId);

	@Query(value = "select sum(q.result)/count(q.result) as 'avgScore', u.id as 'id', u.full_name as 'fullName', u.image_url as 'imageUrl',\r\n"
			+ "u.username as 'username', u.email as 'email' from quiz q join user u on u.id = q.user_id group by q.user_id order by avgScore desc", nativeQuery = true)
	List<LeaderboardDTO> getLeaderboard();
}
