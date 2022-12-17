package com.chat;

import javax.persistence.*;

// This class corresponds to a database table, that is a join table between chat_rooms and users.
// The idea being, for private ChatRooms, there is a list of authenticated users which can access
// that ChatRoom. This will be checked in the ChatController.class before allowing a user to access
// that resource.

@Entity
@Table(name="chat_room_auth_users")
public class ChatRoomAuthenticatedUser{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="chat_room_auth_user_id")
	public Integer chatRoomAuthenticatedUserId;
	
	@Column(name = "chat_room_id")
	public Integer chatRoomId;
	
	@OneToOne
    @JoinColumn(name = "chat_room_id", insertable=false, updatable=false)
	public ChatRoom chatRoom;
	
	@Column(name = "user_id")
	public Integer chatUserId;
	
	@OneToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
	public ChatUser chatUser;
	
	public ChatRoomAuthenticatedUser(ChatRoom cr, ChatUser cu){
		this.chatRoom = cr;
		this.chatUser = cu;
	}
	
	public ChatRoomAuthenticatedUser(){
		
	}
	
	public ChatRoomAuthenticatedUser(Integer chatRoomId, Integer chatUserId){
		this.chatRoomId = chatRoomId;
		this.chatUserId = chatUserId;
	}
	
	public Integer getChatRoomAuthenticatedUserId(){
		return this.chatRoomAuthenticatedUserId;
	}
	
	public void setChatRoomAuthenticatedUserId(Integer crauId){
		this.chatRoomAuthenticatedUserId = crauId;
	}
	
	public ChatUser getChatUser(){
		return this.chatUser;
	}
	
	public String getChatUsername(){
		return this.chatUser.getUsername();
	}
	
	public void setChatUser(ChatUser cu){
		this.chatUser = cu;
	}
	
	public ChatRoom getChatRoom(){
		return this.chatRoom;
	}
	
	public void setChatRoom(ChatRoom cr){
		this.chatRoom = cr;
	}
	
	// add getters in case hibernate needs them to write
	public Integer getChatRoomId(){
		return this.chatRoomId;
	}
	
	public void setChatRoomId(Integer crId){
		this.chatRoomId = crId;
	}
	
	public Integer getChatUserId(){
		return this.chatUserId;
	}
	
	public void setChatUserId(Integer cuId){
		this.chatUserId = cuId;
	}
}
