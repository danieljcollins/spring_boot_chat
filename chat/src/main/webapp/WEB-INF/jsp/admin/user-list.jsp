<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>User List</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <%
		String chatUsers = request.getParameter("chatUsers");
	%>
    <body>
		<div class="header">
			<ul>
				<li><a href="/admin/chat-browser-admin">Chat Browser</a></li>
				<li><a href="/admin/user-list">User List</a></li>
				<li><a href="/logout">Logout</a></li>
			</ul>
		</div>
		<div class="userListAdmin">
			<table>
				<thead>
					<tr>
						<th>Username</th>
						<th>password</th>
						<th>Role</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${chatUsers}" var="chatUsers">
						<tr>
							<td>${chatUsers.getUsername()}</td>
							<td>${chatUsers.getPassword()}</td>
							<td>${chatUsers.getRoleName()}</td>
							<td><button type="button">Delete user</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
        </div>
    </body>
</html>
