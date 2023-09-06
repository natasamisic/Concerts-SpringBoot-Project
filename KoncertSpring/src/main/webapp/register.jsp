<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
<link href="${pageContext.request.contextPath }/styles/login.css" rel="stylesheet" >
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Registracija</title>
</head>
<body>
	 <form:form class="box" action="/Koncert/auth/register" method="post" modelAttribute="korisnik">
		<h1>Registracija</h1> 
 		 <form:input path="ime" placeholder="Ime"/>
 		 <form:input path="prezime" placeholder="Prezime"/>
 		 <form:input path="email" placeholder="Email"/>
 		 <form:input path="username" placeholder="Username"/>
 		 <form:input type="password" path="password" placeholder="Password"/>
 		 <input type="submit" value="Registruj me"/>
 		 <p>${poruka }</p>
	</form:form>
</body>
</html>