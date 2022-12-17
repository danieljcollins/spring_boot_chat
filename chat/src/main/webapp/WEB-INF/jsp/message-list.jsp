<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>User List</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Message</th>
                    <th>Date Entered</th>                    
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${messages}" var="message">
                    <tr>
                        <td>${message.getUsername()}</td>
                        <td>${message.getMessage()}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
