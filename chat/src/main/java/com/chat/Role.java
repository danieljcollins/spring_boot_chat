package com.chat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Arrays;

import java.util.List;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.userdetails.UserDetails;


// the users will be loaded into the spring application through a repository class
// the users are a representation of database data of user data stored
// this will allow users to login and send messages to a chat room under an identity
// there will be two roles, USER and ADMIN, with USER having the limited abilities
// and ADMIN having advanced abilities such as deleting chat rooms, messages, users, etc.
@Entity
@Table(name="roles")
public class Role {
	//public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	@Id
	@Column(name="role_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roleId;
	
	@Column(name="role_name")
	private String roleName;
		
	protected Role(){}
	
	public Role(String roleName) {
		this.roleName = roleName;
	}
	
	public Long getRoleID(){
		return this.roleId;
	}
	
	public String getRoleName(){
		return this.roleName;
	}	
	
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}
}
