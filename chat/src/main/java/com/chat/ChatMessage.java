package com.chat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;


// in this design, each message will have these fields, and as an object, can be managed by a repository-styled 
// object.
// this is opposed to simply putting the message content into a collection like an array, which may prove to 
// be a better option.
// the messages will be stored in the server app during runtime and will be discarded either through a 
// server command/process (ie. a clean-up method etc.) or during server reset/shut-down

@Entity
@Table(name="messages")
public class ChatMessage{
	
	@Id
	@Column(name="message_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer messageId;
	
	@Column(name="message")
	public String message;
	
	@Column(name="chat_room_id")
	public Integer chatRoomId;
	
	@Column(name = "user_id")
	public Integer chatUserId;
	
	@ManyToOne(targetEntity = com.chat.ChatUser.class, cascade = CascadeType.ALL)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private ChatUser chatUser;
		
	public ChatMessage(String msg, Integer chatRoomId, Integer chatUserId){
		this.message = msg;
		this.chatRoomId = chatRoomId;
		this.chatUserId = chatUserId;
	}
	
	public ChatMessage(){
		
	}
	
	public Integer getChatMessageId(){
		return this.messageId;
	}
	
	public void setChatMessageId(Integer msgId){
		this.messageId = msgId;
	}
	
	public String getChatUsername(){
		return this.chatUser.getUsername();
	}
	
	public ChatUser getChatUser(){
		return this.chatUser;
	}
	
	public Integer getChatUserId(){
		return this.chatUserId;
	}
	
	public void setChatUserId(Integer chatUserId){
		this.chatUserId = chatUserId;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public Integer getChatRoomId(){
		return this.chatRoomId;
	}
	
	public void setChatRoomId(Integer chatRoomId){
		this.chatRoomId = chatRoomId;
	}
}
