package com.chat;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Integer>{
	 ChatRoom findByChatRoomId(Integer chatRoomId);
	 
	 @Modifying
	 @Transactional
	 void deleteByChatRoomId(Integer chatRoomId);
	 
	 //ChatRoom findChatRoomName(Integer chatRoomId);
	 
	 //@Query("SELECT username FROM users WHERE user_id = ?1")
	 //String findUserName(Integer chatUserId);
	 
	 //ChatUser findByUserId(Integer chatUserId);
}
