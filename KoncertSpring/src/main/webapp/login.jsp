<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link href="${pageContext.request.contextPath }/styles/login.css" rel="stylesheet" >
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login</title>
</head>
<body>	
	 <c:url var="loginUrl" value="/login" />
	<form class="box" action="${loginUrl}" method="post">
	 	 <h1>LOGIN</h1>
 		 <input type="text" name="username" placeholder="Username">
 		 <input type="password" name="password" placeholder="Password">
 		 <input type="submit" value="Login">
 		 
 		 <input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}" />
 		 <p>${poruka }</p>
	</form>
	<div class="box1">
		<a href="/Koncert/auth/noviProfil" type="submit">Nemate profil? Kliknite ovde</a>
	</div>
	
	<div class="lines">
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
            </div>
</body>
</html>