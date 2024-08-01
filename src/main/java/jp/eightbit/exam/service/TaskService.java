package jp.eightbit.exam.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.TaskPriority;
import jp.eightbit.exam.entity.TaskState;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.model.TaskSearchQuery;
import jp.eightbit.exam.repository.TaskPriorityRepository;
import jp.eightbit.exam.repository.TaskRepository;
import jp.eightbit.exam.repository.TaskStateRepository;
import jp.eightbit.exam.repository.UserRepository;
import org.springframework.data.domain.Pageable;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private TaskStateRepository taskStateRepo;
	
	@Autowired
	private TaskPriorityRepository taskPriorityRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	public Task taskfindById(int taskId) {
		return taskRepo.findById((long) taskId).get();
	}
	/**
	 * タスクの状態Listを取得
	 * @return 
	 */
	public List<TaskState> getTaskStateAll(){
		List<TaskState> taskStateList = taskStateRepo.findAll();
		return taskStateList;
	}
	public TaskState taskStatusfindById(int id) {
		return taskStateRepo.findById((long) id).get();
	}
	/**
	 * タスクの優先度Listを取得
	 * @return 
	 */
	public List<TaskPriority> getTaskPriorityAll(){
		List<TaskPriority> taskPriorityList = taskPriorityRepo.findAll();
		return taskPriorityList;
	}
	public TaskPriority taskPriorityfindById(int id) {
		return taskPriorityRepo.findById((long) id).get();
	}
	
	/**
	 * 全タスクListを取得
	 * @return
	 */
	public List<Task> findAll(){
		return taskRepo.findAll();
	}
	
	
	/**
	 * ログインユーザに紐づいたタスクListを取得
	 * @param userId
	 * @return 
	 */
	public List<Task> findPersonalTaskAll(int userId){
		List<Task> taskList = taskRepo.findByUserId(userId);
		return taskList;
	}
	
	/**
	 * @return 担当者未割り当てタスクデータ
	 */
	public List<Task> findUnassignedTaskAll(){
		List<Task> taskList = taskRepo.findAll();
		List<Task> unassignedTaskList = taskList.stream()
				.filter(task -> task.getUser() == null && task.getStatus().getId() != 3)
				.collect(Collectors.toList());
		return unassignedTaskList;
	}
	
	/**
	 * 全体タスクのクリックされたStatusのタスクを取得
	 * @param index
	 * @return
	 */
	public List<Task> findProgressTaskAll(int index){
		List<Task> taskList = taskRepo.findByStatusId(index);
		
		return taskList;
	}
	

	/**
	 * マイタスクのクリックされたStatusのタスクを取得
	 * @param userId
	 * @param index
	 * @return
	 */
	public List<Task> findProgressMyTaskAll(int userId,int index){
		List<Task> myTaskList = taskRepo.findByUserIdAndStatusId(userId,index);

		return myTaskList;
	}



	
	/**
	 * 新規タスク追加
	 * @param task
	 * @param priorityId
	 * @param userId
	 */
	public void taskRegist(Task task, int priorityId,int userId) {
		task.setPriority(taskPriorityRepo.findById((long) priorityId).get());
		task.setStatus(taskStateRepo.findById(1L).get());
		
		if(userId != 0) {
			task.setUser(userRepo.findById((long) userId).get());
		}else {
			task.setUser(null);
		}
		
		taskRepo.save(task);
	}
	
	
	
	public List<Task> toDayTaskList(){
		List<Task> taskList = taskRepo.findAllByOrderByDueTimeAsc();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String s = now.format(form);
		
		List<Task> toDayTaskList = taskList.stream()
				.filter(task -> task.getDueTime().format(form).equals(s))
				.collect(Collectors.toList());
		
		return toDayTaskList;
	}
	
	// 検索
	public List<Task> searchTask(TaskSearchQuery searchQuery) {

		List<Task> taskList = new ArrayList<Task>();
		List<User> userList = userRepo.findByNameContaining(searchQuery.getRepName());
		LocalDateTime from = null;
		LocalDateTime to = null;
		String taskName =searchQuery.getTaskName();
		String repName = searchQuery.getRepName();
		int priorityNum = searchQuery.getPriorityNum();
		
		if(!searchQuery.getFrom().isBlank()){
			from = LocalDateTime.parse(searchQuery.getFrom());			
		}
		if(!searchQuery.getTo().isBlank()) {
			to = LocalDateTime.parse(searchQuery.getTo());			
		}

		//検索機能
		if(priorityNum == 0 && taskName.isBlank() && repName.isBlank() && from == null && to == null) {
			// 何も入力されていない時は全表示
			taskList = taskRepo.findAll();
		}else if(taskName.isBlank() && repName.isBlank() && from == null && to == null) {
			//優先度のみ
			taskList = taskRepo.findByPriorityId(priorityNum);
		}else if(repName.isBlank() && from == null && to == null && priorityNum == 0) {
			// タスク名のみ
			taskList = taskRepo.findByNameContaining(taskName);
		}else if(taskName.isBlank() && from == null && to == null && priorityNum == 0) {
			// 担当者名のみ
			for(User u : userList) {
				List<Task> userTaskList = taskRepo.findByUserId(u.getId());
				taskList.addAll(userTaskList);
			}
		}else if(taskName.isBlank() && repName.isBlank() && priorityNum == 0) {
			// 日付のみの検索
			taskList = taskRepo.findBydueTimeBetween(from, to);
		}else if(repName.isBlank() && from == null && to == null) {
			// 優先度とタスク名
			taskList = taskRepo.findByPriorityIdAndNameContaining(priorityNum, taskName);
		}else if(taskName.isBlank() && from == null && to == null) {
			// 優先度と担当者名
			for(User u : userList) {
				List<Task> userTaskList = taskRepo.findByPriorityIdAndUserId(priorityNum, u.getId());
				taskList.addAll(userTaskList);
			}
		}else if(taskName.isBlank() && repName.isBlank()) {
			// 優先度と日付
			taskList = taskRepo.findByPriorityIdAndDueTimeBetween(priorityNum, from, to);
		}else if(priorityNum == 0 && from == null && to == null) {
			// タスク名と担当者名
			for(User u : userList) {
				List<Task> userTaskList = taskRepo.findByNameContainingAndUserId(taskName, u.getId());
				taskList.addAll(userTaskList);
			}
		}else if(priorityNum == 0 && repName.isBlank()) {
			// タスク名と日付
			taskList = taskRepo.findByNameContainingAndDueTimeBetween(taskName, from, to);
		}else if(priorityNum == 0 && taskName.isBlank()) {
			// 担当者名と日付
			for(User u : userList) {
				List<Task> userTaskList = taskRepo.findByUserIdAndDueTimeBetween(u.getId(), from, to);
				taskList.addAll(userTaskList);
			} 
		}else if(from == null && to == null) {
			// 優先度とタスク名と担当者名
			for(User u : userList) {
				List<Task> userTaskList = taskRepo.findByPriorityIdAndNameContainingAndUserId(priorityNum, taskName, u.getId());
				taskList.addAll(userTaskList);
			}
		}else if(repName.isBlank()) {
			// 優先度とタスク名と日付
			taskList = taskRepo.findByPriorityIdAndNameContainingAndDueTimeBetween(priorityNum, taskName , from, to);
		}else if(taskName.isBlank()) {
			// 優先度と担当者名と日付
			for(User u : userList) {
				List<Task> userTaskList = taskRepo.findByPriorityIdAndUserIdAndDueTimeBetween(priorityNum, u.getId(), from, to);
				taskList.addAll(userTaskList);
			}
		}else if(priorityNum == 0) {
			// タスク名と担当者名と日付
			for(User u : userList) {
				List<Task> userTaskList = taskRepo.findByNameContainingAndUserIdAndDueTimeBetween(taskName, u.getId(), from, to);
				taskList.addAll(userTaskList);
			}
		}else {
			// 全部（優先度とタスク名と担当者名と日付
			for(User u : userList) {
				List<Task> userTaskList = taskRepo.findByPriorityIdAndNameContainingAndUserIdAndDueTimeBetween(priorityNum, taskName, u.getId(), from, to);
				taskList.addAll(userTaskList);
			}
		}
			
		
		return taskList;

	}
	
	public void taskUpdateStatus(int taskId,int statusId) {
		Task task = taskRepo.findById((long) taskId).get();
		TaskState ts = new TaskState();
		ts.setName(task.getStatus().getName());
		ts.setId(statusId);
		
		task.setStatus(ts);
		
		taskRepo.save(task);
	}
	
	public void taskDBUpdate(Task task) {
		
		taskRepo.save(task);
	}
	public void taskDBdelete(int taskId) {	
		taskRepo.deleteById((long) taskId);
	}
	public void assignUserToTask(int[] taskId,int[] userId) {
		List<Task> taskList = new ArrayList<>();
		List<User> userList = new ArrayList<>();
		for(int i : taskId) {
			taskList.add(taskRepo.findById((long) i).get());			
		}
		for(int i : userId) {
			if(i != 0) {
				userList.add(userRepo.findById((long) i).get());				
			}else {
				userList.add(null);
			}
		}
		for(int i = 0; i < taskList.size(); i++) {
			taskList.get(i).setUser(userList.get(i));
			taskRepo.save(taskList.get(i));
		}
	}


}
