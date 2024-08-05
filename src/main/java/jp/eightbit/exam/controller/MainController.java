package jp.eightbit.exam.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.UserService;


@Controller
public class MainController {

	private final TaskService taskService;
	private final UserService userService;

	@Autowired
	public MainController(TaskService taskService,UserService userService) {
		this.taskService = taskService;
		this.userService = userService;
	}

	
	/**
	 * 当日期限のタスクを取得する
	 * @param model
	 * @return
	 */
	@GetMapping("/top")
	public String routeToIndex(Model model,@AuthenticationPrincipal UserDetails userDetails) {
		User loginUser = userService.findLoginUser(userDetails.getUsername());
		model.addAttribute("loginUserRole", loginUser.getRole().getId());
		List<Task> taskList = taskService.toDayTaskList();
		model.addAttribute("taskList", taskList);
		return "index";
	}
	
}
