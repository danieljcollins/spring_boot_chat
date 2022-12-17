package com.chat;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ChatRoomAuthenticatedUserRepository extends CrudRepository<ChatRoomAuthenticatedUser, Integer>{
	
	// Check to see if a ChatUser is authorized to view a specific (private) ChatRoom.
	@Query(value="SELECT * FROM chat_room_auth_users u WHERE u.chat_room_id=?1 AND u.user_id=?2", nativeQuery=true)
	ChatRoomAuthenticatedUser isUserAuthorizedToViewChatRoom(Integer chatRoomId, Integer chatUserId);
	
	ArrayList<ChatRoomAuthenticatedUser> findByChatRoomAuthenticatedUserId(Integer chatRoomId);
	ChatRoomAuthenticatedUser findByChatUserUsername(String username);
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM chat_room_auth_users WHERE chat_room_id=?1", nativeQuery=true)
	void deleteByChatRoomId(Integer chatRoomId);
}
