package com.chat;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

import com.chat.ChatUserRepository;
import com.chat.ChatUser;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	ChatUserRepository chatUserRepository;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Resource
    private UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
        http
			.csrf().disable()
          .authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/admin/user-list").hasRole("ADMIN")
            .antMatchers("/delete-chat-room").hasRole("ADMIN")
            .antMatchers("/admin/delete-chat-message/**").hasRole("ADMIN")
            .antMatchers("/chat-window").hasRole("USER")
            .antMatchers("/refresh-chat-window").hasRole("USER")
            .antMatchers("/chat-submit").hasAnyRole("ADMIN", "USER")
            .antMatchers("/chat-browser").hasRole("USER")
            .antMatchers("/chat-room-creation-submit").hasAnyRole("ADMIN", "USER")
            .antMatchers("/css/**").permitAll()
            .antMatchers("/home").permitAll()
            .antMatchers("/register-user").permitAll()
            .antMatchers("/register-user-submit").permitAll()
            .anyRequest().authenticated()	// any other request requires user to be authenticated
            .and()
         .formLogin()	//automatic spring generated login form
			.loginPage("/login")	// I will use my own login page; instead of the Spring default page
			.loginProcessingUrl("/perform-login")
			.defaultSuccessUrl("/login-redirect", true)
			.failureUrl("/login?loginFailure=true")
			.permitAll();	// permit all users (unauthenticated) to view the login page
	}
}
