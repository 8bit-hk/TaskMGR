package jp.eightbit.exam.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.model.MemberSearchQuery;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.UserService;


@Controller
public class UserController {

	private final UserService userService;
	private final TaskService taskService;
	
	public UserController(UserService userService,TaskService taskService) {
		this.userService = userService;
		this.taskService = taskService;
	}
	// マイページへ遷移
	@GetMapping(path = "/myPage")
	public String myPage(Model model,@AuthenticationPrincipal UserDetails userDetails) {
		//ログインユーザのロール情報を渡す
		User loginUser = userService.findLoginUser(userDetails.getUsername());
		model.addAttribute("loginUserRole", loginUser.getRole().getId());
		return "myPage";
	}
	// ユーザ情報へ遷移
	@GetMapping(path = "/userDetail")
	public String myPageUserDetail(Model model,@AuthenticationPrincipal UserDetails userDetails) {
		//ログインユーザのユーザ情報を渡す
		User user = userService.findLoginUser(userDetails.getUsername());
		model.addAttribute("user", user);
		return "userDetail";
	}
	// ユーザ情報更新処理後にユーザ情報画面に戻る
	@PostMapping("/user/updateExecute")
	public String userUpdateExecute(@RequestParam("id") int id,@RequestParam("name") String name,@RequestParam("email") String email,
										@RequestParam("password") String password,@RequestParam("roleId") int roleId,@RequestParam("emailOld") String emailOld) {
		
		User user = userService.userfindById(id);
		
		user.setId(id);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		userService.userUpdate(user,roleId);
		String path = new String();
		if(emailOld.equals(email)) {
			path = "redirect:/myPage";
		}else {
			path = "redirect:/login";
		}
		
		return path;

	}
		
	// 既存アカウント削除画面へ遷移
	@GetMapping(path = "/user/delete")
	public String userDelete(Model model) {
		List<User> userList = userService.findAll();
		model.addAttribute("userList",userList);
		return "userDelete";
	}
	
	// 既存アカウント削除処理後に削除画面に戻る
	@PostMapping("/user/deleteExecute")
	public String userDeleteExecute(@RequestParam("userId") int userId,@AuthenticationPrincipal UserDetails userDetails) {
		User user = userService.userfindById(userId);
		String path = new String();

		
		if(!user.getEmail().equals(userDetails.getUsername())) {
			path = "redirect:/user/delete";
		}else {
			path = "redirect:/login";
		}
		
		userService.userDelete(userId);
		return path;
	}
	
	// メンバページﾞへ遷移
	@GetMapping("/member")
	public String userIndexPage(Model model,@AuthenticationPrincipal UserDetails userDetails) {
		List<User> userList = userService.findAll();
		List<Task> taskList = taskService.findAll();
		User loginUser = userService.findLoginUser(userDetails.getUsername());
		model.addAttribute("loginUserRole", loginUser.getRole().getId());
		model.addAttribute("searchQuery", new MemberSearchQuery());
		model.addAttribute("userList",userList);
		model.addAttribute("taskList",taskList);
		return "member";
	}

	// メンバ名検索結果表示
	@GetMapping("/memberSearch")
	public String searchUser(Model model, @ModelAttribute MemberSearchQuery searchQuery,@AuthenticationPrincipal UserDetails userDetails) {
		List<User> userList = userService.searchUser(searchQuery);
		List<Task> taskList = taskService.findAll();
		User loginUser = userService.findLoginUser(userDetails.getUsername());
		model.addAttribute("loginUserRole", loginUser.getRole().getId());
		model.addAttribute("searchQuery", new MemberSearchQuery());
		model.addAttribute("userList", userList);
		model.addAttribute("taskList",taskList);

		return "member";

	}

}
