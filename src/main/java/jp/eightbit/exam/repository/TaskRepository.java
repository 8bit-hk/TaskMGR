package jp.eightbit.exam.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.eightbit.exam.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByOrderByDueTimeAsc();
	
	// 優先度で検索
	List<Task> findByPriorityId(int priorityId);

	// タスク名で検索
	List<Task> findByNameContaining(String taskName);

	// 担当者IDで検索
	List<Task> findByUserId(int userId);
	
	// 担当者IDとタスクの状態で検索
	List<Task> findByUserIdAndStatusId(int userId,int statusId);
	
	// タスクの状態で検索（未/進/完）
	List<Task> findByStatusId(int statusId);
	
	// 特定の日で検索（YYYY/MM/DD）
	List<Task> findBydueTime(LocalDateTime date);
	
	// 特定の期間で検索（YYYY/MM/DD～YYYY/MM/DD）
	List<Task> findBydueTimeBetween(LocalDateTime from,LocalDateTime to);
		
	// 優先度とタスク名で検索
	List<Task> findByPriorityIdAndNameContaining(int priorityId, String taskName);
	// 優先度と担当者名で検索
	List<Task> findByPriorityIdAndUserId(int priorityId, int userId);
	// 優先度と日付で検索
	List<Task> findByPriorityIdAndDueTimeBetween(int priorityId,LocalDateTime from,LocalDateTime to);
	
	// タスク名と担当者名で検索
	List<Task> findByNameContainingAndUserId(String taskName,int userId);
	// タスク名と日付で検索
	List<Task> findByNameContainingAndDueTimeBetween(String taskName, LocalDateTime from,LocalDateTime to);
	
	// 担当者名と日付で検索
	List<Task> findByUserIdAndDueTimeBetween(int userId, LocalDateTime from,LocalDateTime to);
	
	// 優先度とタスク名と担当者名
	List<Task> findByPriorityIdAndNameContainingAndUserId(int priorityId, String taskName, int userId);
	// 優先度とタスク名と日付
	List<Task> findByPriorityIdAndNameContainingAndDueTimeBetween(int priorityId, String taskName, LocalDateTime from,LocalDateTime to);
	// 優先度と担当者名と日付
	List<Task> findByPriorityIdAndUserIdAndDueTimeBetween(int priorityId, int userId, LocalDateTime from,LocalDateTime to);
	// タスク名と担当者名と日付
	List<Task> findByNameContainingAndUserIdAndDueTimeBetween(String taskName, int userId, LocalDateTime from,LocalDateTime to);

	// 全部（優先度とタスク名と担当者名と日付
	List<Task> findByPriorityIdAndNameContainingAndUserIdAndDueTimeBetween(int priorityId,String taskName, int userId, LocalDateTime from,LocalDateTime to);


}
