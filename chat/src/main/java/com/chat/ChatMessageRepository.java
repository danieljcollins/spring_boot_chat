package com.chat;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Integer>, JpaSpecificationExecutor<ChatMessage>{
	
	ArrayList<ChatMessage> findByChatRoomId(Integer chatRoomId);
	
	@Modifying
	@Transactional
	@Query(value="DELETE from messages WHERE chat_room_id=?1", nativeQuery=true)
	void deleteByChatRoomId(Integer chatRoomId);
	
	@Modifying
	@Transactional
	@Query(value="DELETE from messages WHERE message_id=?1", nativeQuery=true)
	void deleteByMessageId(Integer chatMessageId);
}
