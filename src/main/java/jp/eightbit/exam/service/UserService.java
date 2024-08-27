package jp.eightbit.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.eightbit.exam.entity.Role;
import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.model.MemberSearchQuery;
import jp.eightbit.exam.repository.RoleRepository;
import jp.eightbit.exam.repository.TaskRepository;
import jp.eightbit.exam.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	public User userfindById(int userId) {
		return userRepo.findById((long) userId).get();
	}
	
	public List<User> findAll(){
		return userRepo.findAll();
	}
	public User findLoginUser(String userName) {
		User loginUser = userRepo.findByEmail(userName);
		return loginUser;
	}
	
	/**
	 * @return 全ロールデータ
	 */
	public List<Role> getRoleAll(){
		List<Role> roleList = roleRepo.findAll();
		return roleList;
	}
	/**
	 * 新規ユーザ追加
	 */
	public void userRegist(User user,int roleId) {
		user.setRole(roleRepo.findById((long) roleId).get());		
		
		userRepo.save(user);
	}
	
	/**
	 * ユーザ情報更新
	 */
	public void userUpdate(User user,int roleId) {
		user.setRole(roleRepo.findById((long) roleId).get());		

		userRepo.save(user);
	}
	/**
	 * 既存ユーザ削除
	 */
	public void userDelete(int userId) {
		List<Task> taskList = taskRepo.findByUserId(userId);
		System.out.println(taskList);
		userRepo.deleteById((long) userId);
	}
	
	public List<User> searchUser(MemberSearchQuery searchQuery){
		List<User> userList = null;
		int roleNum = searchQuery.getRoleNum();
		String name = searchQuery.getName();
		
		//検索機能
		if(roleNum == 0 && name.isBlank()) {
			userList = userRepo.findAll();
		}else if(roleNum != 0 && name.isBlank()) {
			// 役割のみ
			userList = userRepo.findByRoleId(roleNum);
		}else if(roleNum == 0 && !name.isBlank()) {
			// 名前のみ
			userList = userRepo.findByNameContaining(name);
		}else if(roleNum != 0 && !name.isBlank()) {
			// 役割と名前両方
			userList = userRepo.findByRoleIdAndNameContaining(roleNum, name);
		}
		
		return userList;
	}
}
