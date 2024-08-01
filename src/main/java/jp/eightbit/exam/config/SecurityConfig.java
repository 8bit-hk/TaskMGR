package jp.eightbit.exam.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.formLogin(login -> login				// フォーム認証の設定記述開始
				.loginProcessingUrl("/login")		// email・パスワードの送信先URL
				.loginPage("/login")				// ログイン画面のURL
				.defaultSuccessUrl("/top",true)	// ログイン成功後のリダイレクト先URL
				.failureUrl("/login?error")			// ログイン失敗後のリダイレクト先URL
				.permitAll()						// ログイン画面は未ログインでもアクセス可能
		).logout(logout -> logout					// ログアウトの設定記述開始
				.logoutSuccessUrl("/login")			// ログアウト成功後のリダイレクト先URL
		
		).authorizeHttpRequests(ahr -> ahr			// URLごとの認可設定記述開始
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
					.permitAll()					// "/css/**"などはログインなしでもアクセス可能
				.requestMatchers("/")
					.permitAll()					// "/"はログインなしでもアクセス可能
				.requestMatchers("/general")
					.hasRole("GENERAL")				// "/general"はROLE_GENERALのみアクセス可能（一般ユーザ）
				.requestMatchers("/admin")
					.hasRole("ADMIN")				// "/admin"ははROLE_ADMINのみアクセス可能（管理者ユーザ）
				.anyRequest().authenticated());		// 他のURLはログイン後のみアクセス可能
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		System.out.println(encoder.encode("user"));

		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
