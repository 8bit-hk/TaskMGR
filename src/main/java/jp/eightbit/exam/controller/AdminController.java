package jp.eightbit.exam.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.eightbit.exam.entity.Role;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.service.TaskService;
import jp.eightbit.exam.service.UserService;


@Controller
public class AdminController {
	
	private final UserService userService;
	
	@Autowired
	public AdminController(UserService userService) {
		this.userService = userService;
	}
	
	// 管理者専用ページへ遷移
	@GetMapping(path = "/admin")
	public String routeToAdmin(Model model) {
		return "admin";
	}
	
	// 新規アカウント作成画面へ遷移
	@GetMapping(path = "/admin/regist")
	public String userRegist(Model model) {
		
		List<Role> roleList = userService.getRoleAll();
		model.addAttribute("user", new User());
		model.addAttribute("roleList",roleList);
		System.out.println(model);
		return "userRegist";
	}
	
	// 既存アカウント削除画面へ遷移
	@GetMapping(path = "/admin/delete")
	public String userDelete(Model model) {
		List<User> userList = userService.findAll();
		model.addAttribute("userList",userList);
		return "userDelete";
	}
	
	// 新規アカウント追加処理後に管理画面トップに戻る
	@PostMapping("/admin/registExecute")
	public String userRegistExecute(@ModelAttribute User user,@RequestParam("roleId") int roleId) {
		
		// 入力されたパスワードをハッシュ化し再度格納
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		userService.userRegist(user,roleId);
		return "redirect:/admin";
	}
	
	@PostMapping("/admin/deleteExecute")
	public String userDeleteExecute(@RequestParam("userId") int userId) {
		userService.userDelete(userId);
		
		return "redirect:/admin/delete";
	}
	
}
