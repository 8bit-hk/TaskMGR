package jp.eightbit.exam.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.eightbit.exam.entity.User;
import jp.eightbit.exam.repository.UserRepository;
@Service
public class LoginUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	public LoginUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override	// ユーザ名で検索して、UserDetailsを返す
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> loginUserOptional = Optional.of(userRepository.findByEmail(email));
		return loginUserOptional.map(loginUser -> new LoginUserDetails(loginUser))
				.orElseThrow(() -> new UsernameNotFoundException("not found"));
	}
}
