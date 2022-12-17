<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Chat Browser</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
		<div class="header">
			<ul>
				<li><a href="/chat-browser">Chat Browser</a></li>
				<li><a href="/logout">Logout</a></li>
			</ul>
		</div>		
		<div class="chatRoomList">
			<table id="chatRoomListTable">
				<thead>
					<tr>
						<th>Chat Room Name</th>
						<th>Room Type</th>
						<th>Created By</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${chatRooms}" var="chatRooms">
						<tr>
							<td><a href="/chat-window?chatRoomId=${chatRooms.getChatRoomId()}">${chatRooms.getChatRoomName()}</a></td>
							<c:if test="${chatRooms.getPrivateRoom() == true}">
								<td>Private</td>
							</c:if>
							<c:if test="${chatRooms.getPrivateRoom() == false}">
								<td>Public</td>
							</c:if>
							<td>${chatRooms.getChatUser().getUsername()}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="chatRoomToolsArea">
			<form method="POST" id="createNewChatRoom" action="chat-room-creation-submit" modelAttribute="chatRoomName" modelAttribute="chatRoomType">
				<div class="chatRoomToolsControls">
					Create room: <input type="text" name="chatRoomName">
					<input type="radio" id="public" name="chatRoomType" value="Public" checked="checked">
					<label for="public">Public</label>
					<input type="radio" id="private" name="chatRoomType" value="Private">
					<label for="private">Private</label>
					<input type="submit" id="submit" value="Create"/>
				</div>
			</form>
		</div>
    </body>
</html>
