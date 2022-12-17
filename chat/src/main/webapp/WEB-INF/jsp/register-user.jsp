<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>User Registration Form</title>
		<link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
		
		<script>
			// Function to check whether the username is empty or populated
			function checkUsername(form){
				username = form.username.value;
				
				if(username == ''){
					alert("Please enter username");
					return false;
			}
		
            // Function to check Whether both passwords is same or not.
            function checkPassword(form){
                password1 = form.password1.value;
                password2 = form.password2.value;
  
                // If password not entered
                if (password1 == ''){
                    alert ("Please enter Password");
                    return false;
                }
                // If confirm password not entered
                else if (password2 == ''){
                    alert ("Please enter confirmation password");
                    return false;
                }                      
                // If Not same return False.    
                else if (password1 != password2) {
					//document.createNewChatUserForm.action
                    //alert ("\nPassword did not match: Please try again...")
                    return false;
                }
                else if(password1 == password2){
                    //alert("Password Match: Welcome to the Chat!")
                    //document.createNewChatUserForm.submit(); // Form will be submitted by it's name
                    return true;
                }
                else{
					return false;
				}
            }
        </script>
	</head>
	<%
		//String error = request.getParameter("error");
	%>
	<body>
		<div class="header">
			<ul>
				<li><a href="/home">Home</a></li>
				<li><a href="/chat-browser">Chat Browser</a></li>
				<li><a href="/login">Log In</a></li>
			</ul>
		</div>
		<h1>User Registration Form</h1>
		
		<c:if test="${param.error == 'userAlreadyExists'}">
			<div class="error">
				<p>Error - User Already Exists - Choose another username.</p>
				</br>
			</div>
		</c:if>
		
		<c:if test="${param.error == 'passwordsDoNotMatch'}">
			<div class="error">
				<p>Error - Passwords not match. Please try again.</p>
				</br>
			</div>
		</c:if>
		
		<div class="registerForm">
			<form onsubmit="return checkPassword(this)" method="POST" id="createNewChatUser" action="register-user-submit" modelAttribute="username" modelAttribute="password1" modelAttribute="password2">
				Username: <input type="text" name="username" id="username"><br/><br/>
				Password: <input type="password" name="password1" id="password1"><br/><br/>
				Re-enter Password: <input type="password" name="password2" id="password2"><br/><br/>
				<input type="submit" id="submit" value="Submit"/>
			</form>
		</div>
	</body>
</html>
