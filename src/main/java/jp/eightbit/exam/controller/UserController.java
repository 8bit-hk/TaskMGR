package jp.eightbit.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.model.MemberSearchQuery;
import jp.eightbit.exam.model.TaskSearchQuery;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.UserService;


@Controller
public class UserController {

	private final UserService userService;
	private final TaskService taskService;
	
	@Autowired
	public UserController(UserService userService,TaskService taskService) {
		this.userService = userService;
		this.taskService = taskService;
	}

	@GetMapping("/member")
	public String userIndexPage(Model model) {
		List<User> userList = userService.findAll();
		List<Task> taskList = taskService.findAll();
		model.addAttribute("searchQuery", new MemberSearchQuery());
		model.addAttribute("userList",userList);
		model.addAttribute("taskList",taskList);
		System.out.println(model);
		return "member";
	}

	@GetMapping("/memberSearch")
	public String searchUser(Model model, @ModelAttribute MemberSearchQuery searchQuery) {
		List<User> userList = userService.searchUser(searchQuery);
		List<Task> taskList = taskService.findAll();
		model.addAttribute("searchQuery", new MemberSearchQuery());
		model.addAttribute("userList", userList);
		model.addAttribute("taskList",taskList);
		System.out.println(model);	
		return "member";

	}

}
