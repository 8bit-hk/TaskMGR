package jp.eightbit.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.eightbit.exam.entity.Task;
import jp.eightbit.exam.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	//User findByNameContaining(String userName);
	
	List<User> findByRoleId(int roleId);
	
	
	List<User> findByNameContaining(String name);
	
	List<User> findByRoleIdAndNameContaining(int roleId, String name);
}
