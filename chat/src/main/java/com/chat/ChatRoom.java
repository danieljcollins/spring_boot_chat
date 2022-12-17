package com.chat;
/*
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;

import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
*/
import javax.persistence.*;
import java.lang.Boolean;

@Entity
@Table(name="chat_rooms")
public class ChatRoom{
	
	@Id
	@Column(name="chat_room_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer chatRoomId;
	
	@Column(name="user_id")	//, insertable=false, updatable=false)
	public Integer chatUserId;
	
	@OneToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
	public ChatUser chatUser;
	
	@Column(name="chat_room_name")
	public String chatRoomName;
	
	@Column(name="private_room")
	public Boolean privateRoom;
	
	//@Column(name="chat_room_type")
	//public String chatRoomType;
	
	// dev note: add a public room or private room field later
	public ChatRoom(String chatRoomName, ChatUser chatUser, Boolean privateRoom){	//Integer userId){
		this.chatRoomName = chatRoomName;
		this.chatUser = chatUser;	// JPA/Hibernate maps the ChatUser object to the user_id field in the chat_room table automatically
		this.privateRoom = privateRoom;
		this.chatUserId = chatUser.getChatUserId();
	}	
	
	public ChatRoom(){
		
	}
	
	public void setId(Integer chatRoomId){
		this.chatRoomId = chatRoomId;
	}
	
	public Integer getChatUserId(){
		return this.chatUserId;
	}
	
	public Integer getChatRoomId(){
		return this.chatRoomId;
	}
	
	public String getChatRoomName(){
		return this.chatRoomName;
	}
	
	public ChatUser getChatUser(){
		return this.chatUser;
	}
	
	public void setChatUser(ChatUser chatUser){
		this.chatUser = chatUser;
	}
	
	public Boolean getPrivateRoom(){
		return this.privateRoom;
	}
	
	public void setPrivateRoom(Boolean privateRoom){
		this.privateRoom = privateRoom;
	}
}
