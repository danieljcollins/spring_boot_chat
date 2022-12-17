<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<%
		String loginFailure = request.getParameter("loginFailure");
	%>
	<head>
        <title>Chat Login</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
        <script>
			window.onload = function() {
				document.getElementById("username").focus();
			}
		</script>
    </head>
    <body>
		<div class="header">
			<ul>
				<li><a href="/home">Home</a></li>
				<li><a href="/chat-browser">Chat Browser</a></li>
			</ul>
		</div>
		<div class="loginForm">
			<h3>Login Form</h3>		 
			
			<br/>
			
			<c:if test="${param.loginFailure != null}">
				<div class="error">
					<p>Error - Bad Credentials</p>
					</br>
				</div>
			</c:if>
			
			<form method="POST" action="/perform-login">
				Username:<input type="text" id="username" name="username"/><br/><br/>
				Password:<input type="password" name="password"/><br/><br/>
				<input type="submit" value="login"/>
			</form>
		 </div>
     </body>
</html>
