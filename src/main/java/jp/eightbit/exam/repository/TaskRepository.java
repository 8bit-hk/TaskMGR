package jp.eightbit.exam.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.eightbit.exam.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByOrderByIdDesc();


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
	
	// 開始日のみ入力された場合　入力日以降を全表示
	List<Task> findByDueTimeAfter(LocalDateTime from);
	
	// 終了日のみ入力された場合　入力日以前を全表示
	List<Task> findByDueTimeBefore(LocalDateTime to);
	
	// 特定の期間で検索（YYYY/MM/DD～YYYY/MM/DD）
	List<Task> findBydueTimeBetween(LocalDateTime from,LocalDateTime to);
		
	// 優先度とタスク名で検索
	List<Task> findByPriorityIdAndNameContaining(int priorityId, String taskName);
	// 優先度と担当者名で検索
	List<Task> findByPriorityIdAndUserId(int priorityId, int userId);
	// 優先度と日付で検索（開始日のみ）
	List<Task> findByDueTimeAfterAndPriorityId(LocalDateTime from,int priorityId);
	// 優先度と日付で検索（終了日のみ）
	List<Task> findByDueTimeBeforeAndPriorityId(LocalDateTime to,int priorityId);
	// 優先度と日付で検索（両方）
	List<Task> findByPriorityIdAndDueTimeBetween(int priorityId,LocalDateTime from,LocalDateTime to);
	
	// タスク名と担当者名で検索
	List<Task> findByNameContainingAndUserId(String taskName,int userId);
	// タスク名と日付で検索（開始日のみ）
	List<Task> findByDueTimeAfterAndNameContaining(LocalDateTime from, String taskName);
	// タスク名と日付で検索（終了日のみ）
	List<Task> findByDueTimeBeforeAndNameContaining(LocalDateTime to, String taskName);
	// タスク名と日付で検索（両方）
	List<Task> findByNameContainingAndDueTimeBetween(String taskName, LocalDateTime from,LocalDateTime to);
	
	// 担当者名と日付で検索（開始日のみ）
	List<Task> findByDueTimeAfterAndUserId(LocalDateTime from, int userId);
	// 担当者名と日付で検索（終了日のみ）
	List<Task> findByDueTimeBeforeAndUserId(LocalDateTime to, int userId);
	// 担当者名と日付で検索（両方）
	List<Task> findByUserIdAndDueTimeBetween(int userId, LocalDateTime from,LocalDateTime to);
	
	// 優先度とタスク名と担当者名
	List<Task> findByPriorityIdAndNameContainingAndUserId(int priorityId, String taskName, int userId);
	
	// 優先度とタスク名と日付（開始日のみ）
	List<Task> findByDueTimeAfterAndPriorityIdAndNameContaining(LocalDateTime from,int priorityId, String taskName);
	// 優先度とタスク名と日付（開始日のみ）
	List<Task> findByDueTimeBeforeAndPriorityIdAndNameContaining(LocalDateTime to,int priorityId, String taskName);
	// 優先度とタスク名と日付（両方）
	List<Task> findByPriorityIdAndNameContainingAndDueTimeBetween(int priorityId, String taskName, LocalDateTime from,LocalDateTime to);
	
	// 優先度と担当者名と日付（開始日のみ）
	List<Task> findByDueTimeAfterAndPriorityIdAndUserId(LocalDateTime from,int priorityId, int userId);
	// 優先度と担当者名と日付（終了日のみ）
	List<Task> findByDueTimeBeforeAndPriorityIdAndUserId(LocalDateTime to,int priorityId, int userId);
	// 優先度と担当者名と日付（両方）
	List<Task> findByPriorityIdAndUserIdAndDueTimeBetween(int priorityId, int userId, LocalDateTime from,LocalDateTime to);

	// タスク名と担当者名と日付（開始日のみ）
	List<Task> findByDueTimeAfterAndNameContainingAndUserId(LocalDateTime from,String taskName, int userId);
	// タスク名と担当者名と日付（開始日のみ）
	List<Task> findByDueTimeBeforeAndNameContainingAndUserId(LocalDateTime to,String taskName, int userId);
	// タスク名と担当者名と日付（両方）
	List<Task> findByNameContainingAndUserIdAndDueTimeBetween(String taskName, int userId, LocalDateTime from,LocalDateTime to);

	// 全部（優先度とタスク名と担当者名と日付（開始日のみ）
	List<Task> findByDueTimeAfterAndPriorityIdAndNameContainingAndUserId(LocalDateTime from,int priorityId,String taskName, int userId);
	// 全部（優先度とタスク名と担当者名と日付（終了日のみ）
	List<Task> findByDueTimeBeforeAndPriorityIdAndNameContainingAndUserId(LocalDateTime to,int priorityId,String taskName, int userId);
	// 全部（優先度とタスク名と担当者名と日付（両方）
	List<Task> findByPriorityIdAndNameContainingAndUserIdAndDueTimeBetween(int priorityId,String taskName, int userId, LocalDateTime from,LocalDateTime to);



}
