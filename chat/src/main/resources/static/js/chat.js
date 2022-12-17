/*
 * chat.js
 * The purpose of chat.js is to add some functions that work with the UI of the view (chat-window.jsp).
 * Since JSP is run on the server, it's important to note that a few JS functions refer to Java objects
 * from the Spring Boot instance, and are thus located in chat-window.jsp itself.
 * 
 * To de-clutter chat-window.jsp a bit, it seemed helpful to move the less impactful functionality to
 * an external JS file.
 */ 

window.onload = function() {
	// hide UI elements that we don't require to be visible on page load (for UI design reasons)
	document.getElementById("userList").style.display = 'none';
	document.getElementById("hideUserListButton").style.display = 'none';
	
	loadMessages();	// initial load of messages
	setInterval(loadMessages, 3000);	// load messages every 3 seconds
	document.getElementById("bottomOfChat").scrollIntoView();	// scroll to the most current message in the thread
	
	document.addEventListener("scroll", disableAutoScrollingText);	// if a user is scrolling(looking for a previous message), don't keep automatically scrolling to the bottom
	document.getElementById("messageTextArea").focus();
}

function showUserList(){
	document.getElementById("showUserListButton").style.display = 'none';
	document.getElementById("userList").style.display = 'inline';
	document.getElementById("hideUserListButton").style.display = 'block';
}

function hideUserList(){
	document.getElementById("showUserListButton").style.display = 'block';
	document.getElementById("userList").style.display = 'none';
	document.getElementById("hideUserListButton").style.display = 'none';
}

function displayUserSearchResults(){
	document.getElementById("hideSearchResultsButton").style.display = 'inline';
}

function loadUserSearchResults() {
	var usernameSearchCriteria = document.getElementById("usernameSearch").value;
	
	const dbParam = JSON.stringify({table:"userSearchResultsTable"});
	const xmlhttp = new XMLHttpRequest();
	xmlhttp.onload = function() {
		const myObj = JSON.parse(this.responseText);
		let text = "<table border='1'>"
		for (let x in myObj) {
			text += "<tr><td>" + myObj[x].username + "</td><td>" +  "<button type='button' onclick=inviteToChatRoom(" + myObj[x].chatUserId  +") id=inviteToRoomButton>Invite to room</button></td></tr>";
		}
		text += "</table>"    
		document.getElementById("userSearchResultsTable").innerHTML = text;
	};
	xmlhttp.open("GET", "/search-users?usernameSearchCriteria=" + usernameSearchCriteria);
	xmlhttp.send();
	
	// display the DOM components
	document.getElementById("hideSearchResultsButton").style.display = 'inline';
	document.getElementById("userSearchResults").style.display = 'block';
}

function hideUserSearchResults(){
	document.getElementById("userSearchResults").style.display = 'none';
	document.getElementById("hideSearchResultsButton").style.display = 'none';
}

function onKeyPressed(event){
	if(event.keyCode == 13){
		sendMessage();
	}
}

function scrollToBottom(){
	document.getElementById("bottomOfChat").scrollIntoView();
}

function disableAutoScrollingText(){
	followTextScroll = false;
	clearInterval(refreshIntervalId);
}

function enableAutoScrollingText(){
	refreshIntervalId = setInterval(scrollToBottom, 3000);
	followTextScroll = true;
}

function displayUserList(){
	var x = document.getElementById("userList");
	if(window.getComputedStyle(x).display === "none"){
		document.getElementById("userList").style.display = 'block';
		document.getElementById("displayUserList").textContent = "Hide User List";
	}
	else{
		document.getElementById("userList").style.display = 'none';
		document.getElementById("displayUserList").textContent = "Show User List";
	}
}

function displayUserSearchControls(){
	var x = document.getElementById("userSearchControls");
	if(window.getComputedStyle(x).display === "none"){
		document.getElementById("userSearchControls").style.display = 'block';
		document.getElementById("displayUserSearchControls").textContent = "Hide User Search Controls";
	}
	else{
		document.getElementById("userSearchControls").style.display = 'none';
		document.getElementById("displayUserSearchControls").textContent = "Show User Search Controls";
	}
}
