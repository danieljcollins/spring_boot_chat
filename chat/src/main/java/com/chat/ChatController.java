package com.chat;

import org.springframework.beans.factory.annotation.Autowired;

// for Spring Web MVC (HTTP requests)
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.transaction.annotation.Transactional;	// for performing database DELETE's which affect multiple tables

// gather the logged in user, in order to send it to the chat-window.
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

// for logout() implementation
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

// for sending information from this controller class to the jsp front-end
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;	// for receiving data from the jsp front-end
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.web.bind.annotation.CrossOrigin;	// for accessing a React front-end operating on a different port (different implementation)

// there are used to encode the user entered password during a new user registration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// local project imports
import com.chat.ChatUserRepository;
import com.chat.ChatMessageRepository;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)	// if i want to hook in a React or Angular front-end
@Controller
public class ChatController{
	
	@Autowired
	private ChatUserRepository chatUserRepository;
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Autowired
	private ChatRoomAuthenticatedUserRepository chatRoomAuthenticatedUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/home")
    public String homePage(){
		return "home";
	}
	
	@GetMapping("/admin/home-admin")
    public String homePageAdmin(){
		return "/admin/home-admin";
	}
    
	@Transactional
	@PostMapping("/chat-submit")
	@ResponseBody
	public void createNewMesssage(@RequestParam String message, @RequestParam Integer chatRoomId, @RequestParam Integer chatUserId){
		ChatMessage cm = new ChatMessage(message, chatRoomId, chatUserId);
		chatMessageRepository.save(cm);
	}
	
	@Transactional
	@PostMapping("/admin/chat-submit-admin")
	@ResponseBody
	public ModelAndView createNewMesssageAdmin(@RequestParam String message, @RequestParam Integer chatRoomId, @RequestParam Integer chatUserId){
		ChatMessage cm = new ChatMessage(message, chatRoomId, chatUserId);
		chatMessageRepository.save(cm);
		
		ModelAndView modelAndView = new ModelAndView();		
		return new ModelAndView("redirect:/admin/chat-window-admin?chatRoomId=" + chatRoomId);
	}
	
	// deletes the selected message from the chatMessageRepository
	@Transactional
	@GetMapping("/admin/delete-chat-message/")
	@ResponseBody
	public ModelAndView deleteMesssageAdmin(@RequestParam Integer chatMessageId, @RequestParam Integer chatRoomId){
		//System.out.println("deleting message with id: " + chatMessageId);
		chatMessageRepository.deleteByMessageId(chatMessageId);	// deletes the selected message
		
		// return the admin (user) back to the same url, refreshing the page
		ModelAndView modelAndView = new ModelAndView();		
		return new ModelAndView("redirect:/admin/chat-window-admin?chatRoomId=" + chatRoomId);
	}
	
	// takes the form input from the .jsp chat-window.jsp and places it into the ChatMessageRepository
	// Dev note: add public or private chat room option later.
	// for public chat rooms, anyone can view and join. For private chat rooms, only the creator
	// can invite users into it.
	@Transactional
    @RequestMapping("/chat-room-creation-submit")
    @ResponseBody
	public ModelAndView createNewChatRoom(@RequestParam String chatRoomName, @RequestParam String chatRoomType){		
		String currentUsername = getCurrentlySignedInUser(); // to print current logged in user
		ChatUser cu = chatUserRepository.findByUsername(currentUsername);	// attach the ChatUser who created the room to the ChatRoom
		
		Boolean chatRoomTypeBoolean = false;		
		
		// determine whether the ChatRoom is publically viewable, or private (invite only)
		if(chatRoomType.equals("Private")){
			chatRoomTypeBoolean = true;
			// for a private room, initialize data to populate the ChatRoomAuthenticatedUsers object
			// upon creation, only the ChatUser who created the private room will be authorized.
			// the user can then invite other users upon entering the room and adding authenticated users
			
			ChatRoom cr = new ChatRoom(chatRoomName, cu, chatRoomTypeBoolean);	//cu.getUserId());
			chatRoomRepository.save(cr);
			
			ChatRoomAuthenticatedUser crau = new ChatRoomAuthenticatedUser(cr.getChatRoomId(), cu.getChatUserId());
			chatRoomAuthenticatedUserRepository.save(crau);
		}
		else if(chatRoomType.equals("Public")){
			chatRoomTypeBoolean = false;
			
			// This violates the DRY principle (Don't Repeat Yourself), however it needs to be 
			// determined whether the ChatRoom is public or private before creating the room.
			// and the ChatRoom needs to be created before adding to the AuthUsers object.
			// so there's no circular dependency, but the if condition needs to either be evaluated twice,
			// or I need to create the ChatRoom object in both conditions. Both violating DRY.
			ChatRoom cr = new ChatRoom(chatRoomName, cu, chatRoomTypeBoolean);	//cu.getUserId());
			chatRoomRepository.save(cr);
		}
		
		// return the admin (user) back to the same url, refreshing the page
		ModelAndView modelAndView = new ModelAndView();		// this is redundant remove later
		return new ModelAndView("redirect:/chat-browser");
	}
	
	@Transactional
	@RequestMapping("/admin/chat-room-creation-submit-admin")
	@ResponseBody
	public ModelAndView createNewChatRoomAdmin(@RequestParam String chatRoomName, @RequestParam String chatRoomType){		
		String currentUsername = getCurrentlySignedInUser(); // to print current logged in user
		ChatUser cu = chatUserRepository.findByUsername(currentUsername);
		
		Boolean chatRoomTypeBoolean = false;;
		if(chatRoomType.equals("Private")){
			chatRoomTypeBoolean = true;
		}
		else if(chatRoomType.equals("Public")){
			chatRoomTypeBoolean = false;
		}
		
		ChatRoom cr = new ChatRoom(chatRoomName, cu, chatRoomTypeBoolean);	//cu.getUserId());
		chatRoomRepository.save(cr);
		
		// return the admin (user) back to the same url, refreshing the page
		ModelAndView modelAndView = new ModelAndView();
		return new ModelAndView("redirect:/admin/chat-browser-admin");
	}
	
	@Transactional
	@RequestMapping("/admin/delete-chat-room")
	public String deleteChatRoom(Integer chatRoomId){
		chatRoomAuthenticatedUserRepository.deleteByChatRoomId(chatRoomId);	// delete the table which indicates who the authenticated users are (to view and post in the chat room)
		chatMessageRepository.deleteByChatRoomId(chatRoomId);	// delete all of the messages in that chat room
		chatRoomRepository.deleteByChatRoomId(chatRoomId);	// delete the chat room
		
		return "redirect:/admin/chat-browser-admin";
	}
	
	// Returns the String representation of the authenticated user from Spring Security (Context)
	public String getCurrentlySignedInUser(){
		String currentUsername = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			currentUsername = ((UserDetails)principal).getUsername();
		} 
		else {
			currentUsername = principal.toString();
		}
		
		return currentUsername;
	}
	
	// returns the user ID of the currently signed in user
	public Integer getCurrentlySignedInUserId(String username){
		ChatUser cu = chatUserRepository.findByUsername(username);
		Integer currentUserId = cu.getChatUserId();
		return currentUserId;
	}
	
	// This method returns a ResponseBody which can be parsed by Javascript to make AJAX
	// calls to refresh the data without refreshing the page.
	@GetMapping("/refresh-chat-window")
	public @ResponseBody Iterable<ChatMessage> refreshChatWindow(Integer chatRoomId){
	//public ArrayList chatMessages(Integer chatRoomId){
		ChatRoom cr = chatRoomRepository.findByChatRoomId(chatRoomId);
		String chatRoomName = cr.getChatRoomName();
		
		ArrayList<ChatMessage> chatMessageArray = new ArrayList<ChatMessage>();
		for(ChatMessage cm : chatMessageRepository.findByChatRoomId(chatRoomId)){
			chatMessageArray.add(cm);
		}
		
		return chatMessageArray;
	}
	
	@GetMapping("/admin/refresh-chat-window")
	public @ResponseBody Iterable<ChatMessage> refreshChatWindowAdmin(Integer chatRoomId){
	//public ArrayList chatMessages(Integer chatRoomId){
		ChatRoom cr = chatRoomRepository.findByChatRoomId(chatRoomId);
		String chatRoomName = cr.getChatRoomName();
		
		ArrayList<ChatMessage> chatMessageArray = new ArrayList<ChatMessage>();
		for(ChatMessage cm : chatMessageRepository.findByChatRoomId(chatRoomId)){
			chatMessageArray.add(cm);
		}
		
		return chatMessageArray;
	}
	
	/* The purpose of this method is to send text representing the chat room id in order to return
	 * the correct chat room data from the JPA repository to the view from the JSP chat-window.jsp.
	 * This will be called from the JSP itself, since it's a bit ungainly to pass a Model around all
	 * jsp pages, this method will be able to build the Model in order to load the data from a JSP call.
	 * It technically violates the MVC pattern, but only slightly; and is probably the most elegant way
	 * to be able to navigate throughout all of the web pages and be able to land on this page and still
	 * pull the Spring JPA data.
	 */
	/*
	 * Additionally, this controller method needs to determine whether the ChatRoom is publically or
	 * privately viewable. From there, it needs to pull from the list of ChatRoomAuthenticatedUsers
	 * to determine whether the user is on the list and is authorized to view the ChatRoom
	 */ 
	@GetMapping("/chat-window")
	public ModelAndView chatWindow(Integer chatRoomId){
		
		String currentUsername = getCurrentlySignedInUser(); // to print current logged in user
		Integer currentUserId = getCurrentlySignedInUserId(currentUsername);	// to get the user ID
		
		ModelMap modelMap = new ModelMap();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("chat-window");
		
		ChatRoom cr = chatRoomRepository.findByChatRoomId(chatRoomId);
		String chatRoomName = cr.getChatRoomName();
		
		ChatUser cu = chatUserRepository.findByUsername(currentUsername);
		
		if(cr.getPrivateRoom()){
			// pseudo-code: check to see if the currently signed in username matches a name in the 
			// ChatRoomAuthenticatedUsers list. If they are present, let them into the room.
			// If they are not present, notify the user, and go back to the chat-browser
			ChatRoomAuthenticatedUser crau = chatRoomAuthenticatedUserRepository.isUserAuthorizedToViewChatRoom(cr.getChatRoomId(), cu.getChatUserId());
			//System.out.println("chatRoomAuthenticatedUserRepository found: " + crau.getChatUsername());	//+ foundUser.getUsername());
			if(crau == null){	// if crau is null, the user is not authorized to view that ChatRoom
				return new ModelAndView("redirect:/chat-browser");
			}
		}
		
		// By arriving to this portion of the code, it means that either:
		// A) The room is publically viewable, so prepare the view
		// B) The user is authorized to view the private room, so prepare the view
		ArrayList<ChatMessage> chatMessageArray = new ArrayList<ChatMessage>();	// this will store all of the ChatMessages from that particular ChatRoom
		HashSet<ChatUser> chatUserArray = new HashSet<ChatUser>();	// this will store all of the ChatUsers that have posted in that particular ChatRoom
		for(ChatMessage cm : chatMessageRepository.findByChatRoomId(chatRoomId)){
			chatMessageArray.add(cm);
			chatUserArray.add(cm.getChatUser());
		}
		
		modelMap.addAttribute("chatRoomName", chatRoomName);
		modelMap.addAttribute("chatUserArray", chatUserArray);
		modelMap.addAttribute("chatMessageArray", chatMessageArray);
		modelMap.addAttribute("chatRoomId", chatRoomId);
		modelMap.addAttribute("currentUsername", currentUsername);
		modelMap.addAttribute("chatUserId", currentUserId);
		modelAndView.addAllObjects(modelMap);
		return modelAndView;
	}
	
	@GetMapping("/admin/chat-window-admin")
	public ModelAndView chatWindowAdmin(Integer chatRoomId){
		
		String currentUsername = getCurrentlySignedInUser(); // to print current logged in user
		Integer currentUserId = getCurrentlySignedInUserId(currentUsername);	// to get the user ID
		
		ModelMap modelMap = new ModelMap();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/admin/chat-window-admin");
		
		ChatRoom cr = chatRoomRepository.findByChatRoomId(chatRoomId);
		String chatRoomName = cr.getChatRoomName();
		
		ArrayList<ChatMessage> chatMessageArray = new ArrayList<ChatMessage>();	// this will store all of the ChatMessages from that particular ChatRoom
		HashSet<ChatUser> chatUserArray = new HashSet<ChatUser>();	// this will store all of the ChatUsers that have posted in that particular ChatRoom
		for(ChatMessage cm : chatMessageRepository.findByChatRoomId(chatRoomId)){
			chatMessageArray.add(cm);
			chatUserArray.add(cm.getChatUser());
		}
				
		modelMap.addAttribute("chatRoomName", chatRoomName);
		modelMap.addAttribute("chatUserArray", chatUserArray);
		modelMap.addAttribute("chatMessageArray", chatMessageArray);
		modelMap.addAttribute("chatRoomId", chatRoomId);
		modelMap.addAttribute("currentUsername", currentUsername);
		modelMap.addAttribute("chatUserId", currentUserId);
		modelAndView.addAllObjects(modelMap);
		return modelAndView;
	}
	
	// this mapping will return the results of a search
	// this will be called via an AJAX request in the chat-window.jsp
	// it may also be a standalone .jsp.
	@GetMapping("/search-users")
	public @ResponseBody Iterable<ChatUser> searchChatUsers(String usernameSearchCriteria){
		//System.out.println("usernameSearchCriteria = " + usernameSearchCriteria);
		
		ArrayList<ChatUser> chatUserList = new ArrayList<ChatUser>();
		
		for(ChatUser cu : chatUserRepository.findChatUser(usernameSearchCriteria)){
			//System.out.println("Username found: " + cu.getUsername());
			chatUserList.add(cu);	// send in the user search criteria into the custom sql query in the JPA repo
		}
		
		return chatUserList;
	}
	
	@GetMapping("/admin/user-list")
	public ModelAndView userListPage(){
		ModelMap modelMap = new ModelMap();
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("/admin/user-list");
		modelMap.addAttribute("chatUsers", chatUserRepository.findAll());
		modelAndView.addAllObjects(modelMap);
		return modelAndView;
	}
	
	@Transactional
	@PostMapping("/invite-to-chat-room")
	@ResponseBody
	public void inviteToChatRoom(@RequestParam Integer chatRoomId, @RequestParam Integer chatUserId){
		
		ChatRoom cr = chatRoomRepository.findByChatRoomId(chatRoomId);
		ChatUser cu = chatUserRepository.findByChatUserId(chatUserId);
		
		ChatRoomAuthenticatedUser crau = new ChatRoomAuthenticatedUser(cr.getChatRoomId(), cu.getChatUserId());
		
		chatRoomAuthenticatedUserRepository.save(crau);
	}
	
	@GetMapping("/chat-browser")
	public String chatBrowser(Model model){
		model.addAttribute("chatRooms", chatRoomRepository.findAll());
		return "chat-browser";
	}
	
	@GetMapping("/admin/chat-browser-admin")
	public String chatBrowserAdmin(Model model){
		model.addAttribute("chatRooms", chatRoomRepository.findAll());
		return "/admin/chat-browser-admin";
	}
	
	@GetMapping("/register-user")
	public String registerUserPage(){
		return "register-user";
	}
	
	// check to see if there's already a user with that name
	// and check to see if both passwords match
	@Transactional
	@PostMapping("/register-user-submit")
	public String createNewChatUser(@RequestParam String username, @RequestParam String password1, @RequestParam String password2){
		
		if(validateUserRegistration(username) && validateUserRegistrationPassword(password1, password2)){
			ChatUser cu = new ChatUser(username, passwordEncoder.encode(password1), 2);	// 2 equals a regular user, not an admin or other role
			chatUserRepository.save(cu);
			return "register-user-submit";
		}
		else if(!validateUserRegistration(username)){
			return "redirect:/register-user?error=userAlreadyExists";
		}		
		else if(!validateUserRegistrationPassword(password1, password2)){
			return "redirect:/register-user?error=passwordsDoNotMatch";
		}
		else{
			return "/register-user?error=unknownErrorOccurred";
		}
	}
	
	// Method to determine if a username already exists during user registration
	public boolean validateUserRegistration(String u1){
		ChatUser cu = chatUserRepository.findByUsername(u1);
		
		// if it's null then no user by that name exists; therefore we can create a new user by that name
		if(cu == null){
			return true;
		}
		else if(cu != null){
			return false;	// if it's populated then there's already an existing user by that name
		}
		else{
			return false;
		}
	}
	
	// to determine whether the user entered two identical passwords in the form, or not
	public boolean validateUserRegistrationPassword(String p1, String p2){
		if(p1.equals(p2) && !p1.equals("")){
			return true;
		}
		else{
			return false;
		}
	}
		
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	@PostMapping("/perform-login")
	public String processLogin(@RequestBody String requestBody){
		//System.out.println("/perform-login @RequestBody: " + requestBody);
		return "perform-login";
	}
	
	@RequestMapping("/login-redirect")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/chat-browser-admin";
        }
        else if (request.isUserInRole("ROLE_USER")) {
            return "redirect:/chat-browser";
        }
        return "redirect:/home";
    }
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
}
