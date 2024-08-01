package jp.eightbit.exam.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.eightbit.exam.entity.User;


public class LoginUserDetails implements UserDetails {
	private final User loginUser;
	private final Collection<? extends GrantedAuthority> authorities;

	public LoginUserDetails(User loginUser) {
		this.loginUser = loginUser;
//		this.authorities = loginUser.roleList().stream().map(role -> new SimpleGrantedAuthority(role)).toList();
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority(loginUser.getRole().getName()));
		this.authorities = list;

	}

	public User getLoginUser() { return loginUser; }

	@Override	// ハッシュ化済みのパスワードを返す
	public String getPassword() { return loginUser.getPassword(); }

	@Override	// ログインで利用するユーザ名を返す
	public String getUsername() { return loginUser.getEmail(); }

	@Override	// ロールのコレクションを返す
	public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

	@Override	// ユーザが期限切れでなければtrueを返す
	public boolean isAccountNonExpired() { return true; }

	@Override	// ユーザがロックされていなければtrueを返す
	public boolean isAccountNonLocked() { return true; }

	@Override	// ユーザパスワードが期限切れでなければtrueを返す
	public boolean isCredentialsNonExpired() { return true; }

	@Override	// ユーザが有効であればtrueを返す
	public boolean isEnabled() { return true; }
}
