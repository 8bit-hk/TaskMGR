package jp.eightbit.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.eightbit.exam.entity.TaskPriority;

@Repository
public interface TaskPriorityRepository extends JpaRepository<TaskPriority, Long> {
	
	
}