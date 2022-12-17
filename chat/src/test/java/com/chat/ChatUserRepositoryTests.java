package com.chat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.domain.Specification;

import com.chat.ChatUser;
import com.chat.ChatUserRepository;
//import com.chat.ChatUserSpecifications;

import java.util.List;

@SpringBootTest
class ChatUserRepositoryTests {
	
	@Autowired
	ChatUserRepository cur;
	
	ChatUser cu;

	@Test
	void contextLoads() {
		//assertEquals(true);
	}
	
	@Test
	@Transactional
	void createNewChatUserInRepository(){
		cu = new ChatUser("Batman", "commissioner", 2);
		cur.save(cu);
		
		ChatUser cuTest = cur.findByUsername("Batman");
		String username = cuTest.getUsername();
		Assertions.assertEquals("Batman", username);
	}
	
	/*
	@Test
	public void searchForMessagesByChatRoomId(){
		Specification<ChatUser> specification = ChatUserSpecifications.hasMessageInChatRoom(1);
		
		List<ChatUser> messagesInChatRoom = cur.findAll(specification);
		
		assertThat(messagesInChatRoom).hasSize(2);
	}
	*/
}
