package com.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SpringDataJpaUserDetailsService implements UserDetailsService {
	
	@Autowired
	private ChatUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ChatUser chatUser = this.repository.findByUsername(username);
		UserDetails user = User.withUsername(chatUser.getUsername()).password(chatUser.getPassword()).roles(chatUser.getRoleName()).build();
        return user;
    }
}
