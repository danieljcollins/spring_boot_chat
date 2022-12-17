package com.chat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.springframework.boot.test.context.SpringBootTest;

import com.chat.ChatUser;

@SpringBootTest
class ChatUserTests {
	
	ChatUser cu;

	@Test
	void contextLoads() {
		//assertEquals(true);
	}
	
	@Test
	void createNewChatUser(){
		cu = new ChatUser("TestUser", "pass", 2);
		String username = cu.getUsername();
		Assertions.assertEquals("TestUser", username);
	}
	
}
