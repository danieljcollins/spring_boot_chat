package com.chat;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ChatUserRepository extends CrudRepository<ChatUser, Integer>, JpaSpecificationExecutor<ChatUser>{
	
	@Query(value="SELECT user_id from users WHERE user_name=?1", nativeQuery=true)
	Integer getUserID(String username);
	
	ChatUser findByUsername(String username);

	ChatUser findByChatUserId(Integer chatUserId);
	
	// Take user input and find a user where the user's import matches a portion of an actual user (satisfies the SQL %LIKE% condition)
	@Query(value="SELECT * FROM users WHERE user_name=?1", nativeQuery=true)
	ArrayList<ChatUser> findChatUser(String usernameSearchCriteria);
}
