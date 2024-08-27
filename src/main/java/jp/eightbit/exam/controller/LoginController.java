package jp.eightbit.exam.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.eightbit.exam.entity.Role;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.UserService;

@Controller
public class LoginController {

	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	
	private final TaskService taskService;
	private final UserService userService;

	public LoginController(TaskService taskService,UserService userService) {
		this.taskService = taskService;
		this.userService = userService;
	}
	
	/**
	 * 当日期限のタスクを取得するしトップページへ遷移
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
	
	/**
	 * 新規ユーザ作成画面へ遷移
	 * @param model
	 * @return
	 */
	@GetMapping("/register")
	public String userRegist(Model model) {	
		List<Role> roleList = userService.getRoleAll();
		model.addAttribute("user", new User());
		model.addAttribute("roleList",roleList);
		return "userRegist";
	}
	
	/**
	 * DBへ新規ユーザ追加処理後にログイン画面に遷移
	 * @param user
	 * @param roleId
	 * @return
	 */
	@PostMapping("/registerExecute")
	public String userRegistExecute(@ModelAttribute User user,@RequestParam("roleId") int roleId) {
		
		// 入力されたパスワードをハッシュ化し再度格納
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		userService.userRegist(user,roleId);
		return "/login";
	}
	
	/**
	 * ログイン画面に遷移
	 * @return
	 */
	@GetMapping("/login")
	public String redirectLoginPage() {
		return "login";
	}

	/**
	 * ログアウトボタンを押下されたらログイン画面にリダイレクトする
	 * @param authentication
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/logout")
	public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		this.logoutHandler.logout(request, response, authentication);
		return "redirect:/login";
	}

}
