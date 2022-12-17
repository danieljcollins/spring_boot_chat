<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Discussion Window</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
        <style>
			.userSearchResultsTest{
				display: none;
			}
			.userSearchResults{
				display:none;
			}
			
			#hideSearchResultsButton{
				display: none;
			}
			
			.userList{
				float: left;
				display: none;
				margin-left: 20px;
				margin-right: 20px;
			}
			
			#hideUserListButton{
				display: none;
			}
			
			#userListTable{
				border: 1px solid;
			}
			
			.chatWindow{
				float: left;
			}
		</style>
        <script>
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
						text += "<tr><td>" + myObj[x].username + "</td></tr>";
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
				<li><a href="/home">Home</a></li>
				<li><a href="/chat-browser">Chat Browser</a></li>
				<li><a href="/logout">Logout</a></li>
			</ul>
		</div>
		<div class="userSearchControls">
			<label for="usernameSearch">Username Search: </label>
			<input type="text" id="usernameSearch" name="usernameSearch">
			<button type="button" name="searchButton" onclick="loadUserSearchResults()">Search</button>
			<button type="button" id="hideSearchResultsButton" onclick="hideUserSearchResults()">Hide Results</button>
		</div>
		<div class="userSearchResultsTest" id="userSearchResultsTest">
			<table>
				<tr>
					<td>Username found: Dan</td>
					<td><button type="button" name="inviteButton" onclick="">Invite</button></td>
				</tr>
				<tr>
					<td>Username found: Zell</td>
					<td><button type="button" name="inviteButton" onclick="">Invite</button></td>
				</tr>
			</table>
		</div>		
		<div class="userListControls">
			<div class="userSearchResults" id="userSearchResults">
				<table id="userSearchResultsTable">
				</table>
			</div>
			<hr />
		
			<button type="button" id="showUserListButton" onclick="showUserList()">Show User List</button>
			<button type="button" id="hideUserListButton" onclick="hideUserList()">Hide User List</button>
		</div>
		<div class="userList" id="userList">
			<h3>Users</h3>
			<table id="userListTable">
				<tr>
					<td>User 1</td>
				</tr>
				<tr>
					<td>Really Long Username</td>
				</tr>
				<tr>
					<td>User 3</td>
				</tr>
				<tr>
					<td>User 4</td>
				</tr>
			</table>		
		</div>
		<div class="chatWindow" id="chatWindow">
			<h2>${chatRoomName}</h2>
			<h1>This is where the original discussion post that will guide the discussion will go.</h1>
			<br />
			<table id="chatWindowTableHeader">
				<thead>
					<tr>
						<th>Username</th>
						<th>Message (300 character limit)</th>
						<th>Date Entered</th>
					</tr>
					<c:forEach items="${chatMessageArray}" var="chatMessageArray">
						<tr>
							<td>${chatMessageArray.getChatUsername()}</td>
							<td>${chatMessageArray.getMessage()}</td>
						</tr>
					</c:forEach>
				</thead>
				<tbody>
				</tbody>
			</table>
			<table id="chatWindowTable">
			</table>
			<p id="bottomOfChat"></p>
		</div>
		<div class="chatControls">
			<div class="messageInput">
				<input type="hidden" id="username" name="username" value="${currentUsername}">
				Chat: <input type="text" name="message" id="message">
				
				<button type="button" id="submit" value="Submit" onclick="sendMessage()">Submit</button>
				<button type="button" id="autoScrollButton" value="autoScrollButton" onclick="enableAutoScrollingText()">Enable Auto-Scrolling Text</button>
				<button type="button" value="scrollToBottom" onclick="scrollToBottom()">Scroll to bottom</button>
			</div>
		</div>
		
		<script>
			window.onload = function() {
				// hide UI elements that we don't require to be visible on page load (for UI design reasons)
				document.getElementById("userList").style.display = 'none';
				document.getElementById("hideUserListButton").style.display = 'none';
				
				document.getElementById("bottomOfChat").scrollIntoView();	// scroll to the most current message in the thread
			}
			
			document.addEventListener("scroll", disableAutoScrollingText);	// if a user is scrolling(looking for a previous message), don't keep automatically scrolling to the bottom
			document.getElementById("message").focus();
			
			var followTextScroll;
			var refreshIntervalId;
			
			function sendMessage(){
				var message = document.getElementById("message").value;
				
				const xmlhttp = new XMLHttpRequest();
				
				var postContent = "/chat-submit?username=${currentUsername}&message=" + message + "&chatRoomId=${chatRoomId}" + "&chatUserId=${chatUserId}";
				
				xmlhttp.open("POST", postContent);
				xmlhttp.send();
				
				document.getElementById("message").value = "";
				document.getElementById("message").focus();
			}
			
			function scrollToBottom(){
				document.getElementById("bottomOfChat").scrollIntoView();
			}
			
			function disableAutoScrollingText(){
				followTextScroll = false;
				clearInterval(refreshIntervalId);
			}
			
			function enableAutoScrollingText(){
				refreshIntervalId = setInterval(scrollToBottom, 10000);
				followTextScroll = true;
			}
		</script>
	</body>
</html>
