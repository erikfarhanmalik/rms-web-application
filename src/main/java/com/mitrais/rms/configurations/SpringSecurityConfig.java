package com.mitrais.rms.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.mitrais.rms.services.RmsUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private RmsUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests().anyRequest().authenticated()
		.and()
			.formLogin()
				.successHandler(savedRequestAwareAuthenticationSuccessHandler())
			.loginPage("/login").permitAll()
		.and()
			.logout().logoutUrl("/logout").permitAll()
		.and()
			.rememberMe()
				.userDetailsService(userDetailsService)
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(1209600);
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		Using in memory :
//		auth.inMemoryAuthentication()
//			.withUser("employee").password(passwordEncoder().encode("password")).roles("ROLE_EMPLOYEE")
//			.and().passwordEncoder(passwordEncoder());
//		
//		auth.inMemoryAuthentication()
//			.withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN")
//			.and().passwordEncoder(passwordEncoder());
		
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());	    
	    
	    return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
