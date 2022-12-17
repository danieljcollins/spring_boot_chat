package com.chat;
/*
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.OneToMany;
*/
import javax.persistence.*;

import java.util.List;

import com.chat.Role;
import com.chat.ChatMessage;

@Entity
@Table(name="users")
public class ChatUser{
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer chatUserId;
	
	@Column(name="user_name")
	private String username;
	
	@Column(name="pword")
	private String password;
	
	@Column(name="role_id")
	private Integer roleId;
	
	//String ROLE_PREFIX = "ROLE_";
	//String role;
	
	//private boolean accountNonLocked;	// for UserDetails implementation
	
	public ChatUser(){}
	
	public ChatUser(String username, String password, Integer roleId) {
		this.username = username;
		this.password = password;
		this.roleId = roleId;
	}
	
	public Integer getUserId(){
		return this.chatUserId;
	}
	
	public Integer getChatUserId(){
		return this.chatUserId;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setUsername(String username){
		this.username = username;
	}	
		
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName(){
		if(roleId == 1){
			return "ADMIN";
		}
		else if(roleId == 2){
			return "USER";
		}
		else{
			return "NO_ROLE";
		}
	}
	
	/*
	//For UserDetails interface
	@Override 
	public boolean isEnabled() {
		return true;
	}
	
	//For UserDetails interface
	@Override 
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//For UserDetails interface
	@Override 
	public boolean isAccountNonLocked() {
		return true;
	}
	
	public void setAccountNonLocked(Boolean accountNonLocked){
		this.accountNonLocked = accountNonLocked;
	}
	
	public boolean getAccountNonLocked() {
		return accountNonLocked;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	*/
}
