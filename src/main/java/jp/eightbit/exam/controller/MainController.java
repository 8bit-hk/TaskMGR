package jp.eightbit.exam.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.service.TaskService;


@Controller
public class MainController {

	@Autowired
	private final TaskService taskService;
	
	@Autowired
	public MainController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	/**
	 * 当日期限のタスクを取得する
	 * @param model
	 * @return
	 */
	@GetMapping("/top")
	public String routeToIndex(Model model) {
		
		List<Task> taskList = taskService.toDayTaskList();
		model.addAttribute("taskList", taskList);
		return "index";
	}
	
}
