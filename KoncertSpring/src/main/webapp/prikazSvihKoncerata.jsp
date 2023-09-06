<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Lato"
	rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Montserrat"
	rel="stylesheet" type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath }/styles/pocetna.css"
	rel="stylesheet">
<style>table{box-shadow: 1px 1px 2px 2px;}</style>
<title>SvetMuzike</title>
</head>
<body id="myPage" data-spy="scroll" data-target=".navbar" data-offset="50">
	<body id="myPage" data-spy="scroll" data-target=".navbar" data-offset="50">
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			<a class="navbar-brand" href="#myPage">SvetMuzike</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/Koncert/index.jsp">Početna</a></li>
					<li><a href="/Koncert/auth/getKoncerti">Svi koncerti</a></li>
					<li><a href="/Koncert/login.jsp">Login</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container text-center">
		<c:if test="${!empty koncerti }">
			<table class="table table-bordered">
				<tr>
					<th>Izvodjač</th>
					<th>Datum izvođenja</th>
					<th>Trajanje</th>
					<th>Grad</th>
					<th>Adresa</th>
					<th>Cena karte</th>
					<th>Opis</th>
				</tr>
				<c:forEach items="${koncerti }" var="k">
					<tr>
					<td>${k.izvodjac }</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${k.datumIzvodjenja }"/></td>
					<td>${k.trajanje }</td>
					<td>${k.grad }</td>
					<td>${k.adresa }</td>
					<td>${k.cena }</td>
					<td>${k.opis }</td>
				</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
</body>
</html>