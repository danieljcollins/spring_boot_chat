<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<html>
    <head>
        <title>Chat Window</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="/js/chat.js"></script>
        <script>
			// NOTE: The reason that these JS functions are here is that they refer to JSP variables, which run on the server. JSP references can't be loaded into the client 
			// (the view) at run-time like regular Javascript
			
			var followTextScroll;
			var refreshIntervalId;
        
			function loadMessages() {
				const dbParam = JSON.stringify({table:"chatWindowTable"});
				const xmlhttp = new XMLHttpRequest();
				xmlhttp.onload = function() {
					const myObj = JSON.parse(this.responseText);
					
					console.log( JSON.parse(this.responseText) );
					
					let text = "<table border='1'>"
					for (let x in myObj) {
						text += "<tr><td>" + myObj[x].chatUser.username + "</td><td>" + myObj[x].message + "</td><td><a href=" + "/admin/delete-chat-message/?chatMessageId=" + myObj[x].chatMessageId + "&chatRoomId=" + myObj[x].chatRoomId + ">Delete chat message</a></td></tr>";
					}
					text += "</table>"    
					document.getElementById("chatWindowTable").innerHTML = text;
				};
				xmlhttp.open("GET", "refresh-chat-window?chatRoomId=${chatRoomId}");
				xmlhttp.send();
				
				if(followTextScroll == true){
					document.getElementById("bottomOfChat").scrollIntoView();
				}
			}

			function sendMessage(){
				var message = document.getElementById("messageTextArea").value;
				
				const xmlhttp = new XMLHttpRequest();
				
				var postContent = "/chat-submit?message=" + message + "&chatRoomId=${chatRoomId}" + "&chatUserId=${chatUserId}";
				
				xmlhttp.open("POST", postContent);
				xmlhttp.send();
				
				document.getElementById("messageTextArea").value = "";
				document.getElementById("messageTextArea").focus();
			}

			function inviteToChatRoom(chatUserId){
				const xmlhttp = new XMLHttpRequest();
				
				var postContent = "/invite-to-chat-room?chatRoomId=${chatRoomId}&chatUserId=" + chatUserId;
				
				xmlhttp.open("POST", postContent);
				xmlhttp.send();
			}
        </script>
        
	</head>
    <%
		String chatRoomId = request.getParameter("chatRoomId");
		String chatRoomName = request.getParameter("chatRoomName");
		String chatUserId = request.getParameter("chatUserId");
	%>
    <body>
		<div class="header">
			<ul>
				<li><a href="/admin/chat-browser-admin">Chat Browser</a></li>
				<li><a href="/admin/user-list">User List</a></li>
				<li><a href="/logout">Logout</a></li>
			</ul>
		</div>
		<div class="userControls">
			<button type="button" id="displayUserList" value="displayUserList" onclick="displayUserList()">Display User List</button>
			<button type="button" id="displayUserSearchControls" value="displayUserSearchControls" onclick="displayUserSearchControls()">Display User Search Controls</button>
		</div>
		<div class="userSearchControls" id="userSearchControls">
			<label for="usernameSearch">Username Search: </label>
			<input type="text" id="usernameSearch" name="usernameSearch">
			<button type="button" name="searchButton" onclick="loadUserSearchResults()">Search</button>
			<button type="button" id="hideSearchResultsButton" onclick="hideUserSearchResults()">Hide Results</button>
		</div>
		<div class="userSearchResults" id="userSearchResults">
			<table id="userSearchResultsTable">
			</table>
			<button type="button" id="showUserListButton" onclick="showUserList()">Show User List</button>
			<button type="button" id="hideUserListButton" onclick="hideUserList()">Hide User List</button>
		</div>
		<div class="userList" id="userList">
			<h3>Users</h3>
			<table id="userListTable">
				<c:forEach items="${chatUserArray}" var="chatUserArray">
					<tr>
						<td>${chatUserArray.getUsername()}</td>
					</tr>
				</c:forEach>
			</table>		
		</div>
		<div class="chatWindow" id="chatWindow">
			<h2>${chatRoomName}</h2>
			<table id="chatWindowTableHeader">
				<thead>
					<tr>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<table id="chatWindowTable">
			</table>
			<p id="bottomOfChat"></p>
		</div>
		<div class="chatWindowToolsArea">
			<input type="image" id="enableAutoScroll" value="autoScrollButton" alt="enable auto-scrolling text" src="/img/auto_scroll_text.png" onclick="enableAutoScrollingText()">
			<input type="image" id="scrollToBottom" value="scrollToBottom" alt="scroll to the bottom of page" src="/img/scroll_to_bottom.png" onclick="scrollToBottom()">
		</div>
		<div class="messageInputArea">
			<input type="text" name="messageTextArea" id="messageTextArea" onkeypress="return onKeyPressed(event)">
		</div>
	</body>
</html>
