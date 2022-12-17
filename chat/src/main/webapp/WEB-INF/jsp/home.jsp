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
				<li><a href="/home">Home</a></li>
				<li><a href="/chat-browser">Chat Browser</a></li>
				<li><a href="/register-user">Register</a></li>
				<li><a href="/login">Log In</a></li>
			</ul>
		</div>
		<h1>Welcome to the Chat!</h1>
		<div class="section1">
			<h2>Why Chat?</h2>
			<p>Chat allows you to connect with friends, family, co-workers, and there's new people to meet!</p>
		</div>
		<div class="section2">
			<h2>Simple to use.</h2>
			<p>Create or join existing chat rooms in just a couple of clicks!</p>
		</div>
		<div class="section3">
			<h2>Private chats.</h2>
			<p>Create a private room, and invite people to that room for one on one chats, or invite your entire baseball team for a curated list of people you know. For anyone else, access denied!</p>
		</div>
		<div class="section4">
			<h2>Get started today.</h2>
			<a href="/register-user" id="registerButton">Register</a>
			<a href="/login" id="loginButton">Log In</a>
		</div>
     </body>
</html>
