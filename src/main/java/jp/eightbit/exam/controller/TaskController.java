package jp.eightbit.exam.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.TaskPriority;
import jp.eightbit.exam.entity.TaskState;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.model.TaskSearchQuery;
import jp.eightbit.exam.service.ServiceUtility;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.UserService;


@Controller
public class TaskController {

	private final TaskService taskService;
	private final UserService userService;

	@Autowired
	public TaskController(TaskService taskService,UserService userService) {
		this.taskService = taskService;
		this.userService = userService;
	}

	/**
	 *  全タスクを取得し、全体タスクページに遷移する (タスクページのトップ）
	 * @param model
	 * @return
	 */
	@GetMapping("/task")
	public String taskIndexPage(Model model,@AuthenticationPrincipal UserDetails userDetails) {
		
		List<Task> taskList = taskService.findAll();
		// ログインユーザ情報をemailから取得
		User loginUser = userService.findLoginUser(userDetails.getUsername());
		
		model.addAttribute("loginUserId", loginUser.getId());
		model.addAttribute("loginUserRole", loginUser.getRole().getId());

		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		model.addAttribute("taskList",taskList);
		model.addAttribute("url", "/task");
		model.addAttribute("searchQuery", new TaskSearchQuery());

		return "task";
	}
	
	/**
	 *  担当者未割り当てのタスクリストを取得し、全体タスクページに遷移する
	 * @param model
	 * @return
	 */
	@GetMapping("/taskUnassigned")
	public String taskUnassignedPage(Model model,@AuthenticationPrincipal UserDetails userDetails) {
		List<Task> taskList = taskService.findUnassignedTaskAll();

		User loginUser = userService.findLoginUser(userDetails.getUsername());
		model.addAttribute("loginUserRole", loginUser.getRole().getId());
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		model.addAttribute("taskList",taskList);

		model.addAttribute("url", "/taskUnassigned");
		model.addAttribute("searchQuery", new TaskSearchQuery());
		return "task";
	}
	
	/**
	 *  マイタスクページに遷移する　マイタスクページのトップ
	 * @param model
	 * @param userDetails
	 * @return
	 */
	@GetMapping("myTask")
	public String taskPersonalPage(Model model,@AuthenticationPrincipal UserDetails userDetails) {
		
		// ログインユーザ情報をemailから取得
		User loginUser = userService.findLoginUser(userDetails.getUsername());
		// ログインユーザの担当タスクのみを取得
		List<Task> taskList = taskService.findPersonalTaskAll(loginUser.getId());
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);

		model.addAttribute("loginUserName", loginUser.getName());
		model.addAttribute("taskList",taskList);

		model.addAttribute("searchQuery", new TaskSearchQuery());
		return "myTask";
	}
	
	/**
	 *  新規タスク追加ページに遷移する
	 * @param model
	 * @return
	 */
	@GetMapping("/task/regist")
	public String taskRegistPage(Model model) {
		// プルダウンメニューを作るために優先度リストとユーザリストをmodelに渡す
		
		List<TaskPriority> priorityList = taskService.getTaskPriorityAll();
		List<User> userList = userService.findAll();

		model.addAttribute("priorityList", priorityList);
		model.addAttribute("taskEntity", new Task());
		model.addAttribute("userList", userList);
		System.out.println(model);
		return "taskRegist";
	}

	/**
	 *  新規タスク追加処理実行後にリダイレクトでタスクページに戻る
	 * @param task
	 * @param request
	 * @param priorityId
	 * @param userId
	 * @return
	 */
	@PostMapping("/task/registExecute")
	public String taskRegistExecute(@ModelAttribute Task task, HttpServletRequest request,
			@RequestParam("priority") int priorityId,@RequestParam("userId") int userId){
		
		String dueTimeStr = new String();
		dueTimeStr = request.getParameter("dueTime");
		ServiceUtility.parseDate(dueTimeStr);
		taskService.taskRegist(task, priorityId,userId);
		return "redirect:/task";
	}

	/**
	 * 全体タスクページの実行/完了ボタンがクリックされた際の処理
	 * @param taskId
	 * @param statusId
	 * @return
	 */
	@PostMapping("/task/update") 
	public String taskUpdate(@RequestParam("id") int taskId,@RequestParam("statusId") int statusId) {
		taskService.taskUpdateStatus(taskId,statusId);
		
		return "redirect:/task";
	}
	
	/**
	 * マイタスクページの実行/完了ボタンがクリックされた際の処理
	 * @param taskId
	 * @param statusId
	 * @return
	 */
	@PostMapping("/mytask/update") 
	public String mytaskUpdate(@RequestParam("id") int taskId,@RequestParam("statusId") int statusId) {
		taskService.taskUpdateStatus(taskId,statusId);
		
		return "redirect:/myTask";
	}

	/**
	 * 未割当てタスクを割り当てた際の処理
	 * @param taskId
	 * @param userId
	 * @return
	 */
	@PostMapping("/task/repAssignedUpdate") 
	public String taskrepAssignedUpdate(@RequestParam("id") int[] taskId,@RequestParam("userId") int[] userId) {
		taskService.assignUserToTask(taskId,userId);
		
		return "redirect:/task";
	}
	
	/**
	 * タスク編集処理
	 * @param taskId
	 * @param priorityId
	 * @param taskName
	 * @param dueTime
	 * @param repId
	 * @param statusId
	 * @return
	 */
	@PostMapping("/task/updateDB")  
	public String taskDBUpdate(Model model,@RequestParam("taskId") int taskId,@RequestParam("priorityId") int priorityId,@RequestParam("taskName") String taskName,
	@RequestParam("dueTime") LocalDateTime dueTime,@RequestParam("repId") int repId,@RequestParam("statusId") int statusId,@RequestParam("path")String pathName) {
		
		String scenePath = new String();
		Task task = taskService.taskfindById(taskId);
		
		task.setPriority(taskService.taskPriorityfindById(priorityId));
		task.setName(taskName);
		task.setDueTime(dueTime);
		if(repId != 0) {
			task.setUser(userService.userfindById(repId));			
		}else {
			task.setUser(null);
		}
		task.setStatus(taskService.taskStatusfindById(statusId));

		taskService.taskDBUpdate(task);
		
		if(pathName.equals("/task")) {
			scenePath = "redirect:/task";
		}else if(pathName.equals("/myTask")) {
			scenePath = "redirect:/myTask";			
		}else if(pathName.equals("/taskUnassigned")) {
			scenePath = "redirect:/taskUnassigned";
		}else if(pathName.equals("/taskSearch")) {
			scenePath = "redirect:/task";
		}else if(pathName.equals("/myTaskSearch")) {
			scenePath = "redirect:/myTask";
		}
		
			

		return scenePath;
	}
	/**
	 * タスク削除処理
	 * @param taskId
	 * @return
	 */
	@PostMapping("/task/deleteDB") 
	public String taskDBdelete(@RequestParam("taskId") int taskId) {
		taskService.taskDBdelete(taskId);
		
		return "redirect:/task";
	}

	/**
	 * 全体タスクの検索結果表示画面
	 * @param model
	 * @param searchQuery
	 * @param userDetails
	 * @return
	 */
	@GetMapping("/taskSearch")
	public String searchTask(Model model, @ModelAttribute TaskSearchQuery searchQuery,@AuthenticationPrincipal UserDetails userDetails) {
	
		List<Task> taskList = taskService.searchTask(searchQuery);
		// ログインユーザ情報をemailから取得
		User loginUser = userService.findLoginUser(userDetails.getUsername());
		
		model.addAttribute("loginUserId", loginUser.getId());
		model.addAttribute("url", "/taskSearch");
		model.addAttribute("loginUserRole", loginUser.getRole().getId());
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		model.addAttribute("taskList", taskList);
		model.addAttribute("searchQuery", new TaskSearchQuery());
		
		return "task";

	}
	/**
	 * マイタスクの検索結果表示画面
	 * @param model
	 * @param searchQuery
	 * @param userDetails
	 * @return
	 */
	@GetMapping("/myTaskSearch")
	public String searchMyTask(Model model, @ModelAttribute TaskSearchQuery searchQuery,@AuthenticationPrincipal UserDetails userDetails,@RequestParam("repname") String loginuserName) {
		
		searchQuery.setRepName(loginuserName);
		List<Task> taskList = taskService.searchTask(searchQuery);
		// ログインユーザ情報をemailから取得
		User loginUser = userService.findLoginUser(userDetails.getUsername());
		
		model.addAttribute("loginUserName", loginUser.getName());
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		model.addAttribute("taskList", taskList);
		model.addAttribute("searchQuery", new TaskSearchQuery());
		
		return "myTask";

	}
}

