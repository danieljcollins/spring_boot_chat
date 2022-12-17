<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Chat Browser - Admin</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
		<div class="header">
			<ul>
				<li><a href="/admin/chat-browser-admin">Chat Browser</a></li>
				<li><a href="/admin/user-list">User List</a></li>
				<li><a href="/logout">Logout</a></li>
			</ul>
		</div>		
		<div class="chatRoomList">
			<table id="chatRoomListTable">
				<thead>
					<tr>
						<th>Chat Room Name</th>
						<th>Created By</th>
						<th>Tools</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${chatRooms}" var="chatRooms">
						<tr>
							<td><a href="/admin/chat-window-admin?chatRoomId=${chatRooms.getChatRoomId()}">Go to chat room</a> ${chatRooms.getChatRoomName()}</td>
							<td>${chatRooms.getChatUser().getUsername()}</td>
							<td><a href="/admin/delete-chat-room?chatRoomId=${chatRooms.getChatRoomId()}">Delete chat room</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="chatRoomTools">
			<form method="POST" id="createNewChatRoom" action="/admin/chat-room-creation-submit-admin" modelAttribute="chatRoomName">
				Enter room name: <input type="text" name="chatRoomName">
				<input type="submit" id="submit" value="Submit"/>
			</form>
		</div>
    </body>
</html>
