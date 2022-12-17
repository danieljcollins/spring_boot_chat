<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
        <title>Chat - Home</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
		<div class="header">
			<ul>
				<li><a href="/admin/home-admin">Home</a></li>
				<li><a href="/admin/chat-browser-admin">Chat Browser</a></li>				
				<li><a href="/logout">Log Out</a></li>
			</ul>
		</div>
		<div class="home">
			<h1>Welcome to the Chat!</h1>
			</br>
			<h3>Chat is made using Spring Boot and JSP</h3>
			</br>
			<p>Chat is intended to be a portfolio project; it is not meant for production use (in it's current form). I hope that you enjoy taking a look at the project!</p>
		</div>
     </body>
</html>
