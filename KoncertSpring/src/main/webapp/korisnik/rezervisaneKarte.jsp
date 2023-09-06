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
					<li><a href="/Koncert/pocetna.jsp">Početna</a></li>
					<li><a href="/Koncert/koncerti/repertoar">Repertoar</a></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Pretraži koncerte<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/Koncert/korisnik/pretragaKoncerta.jsp">Pretraži po izvođaču</a></li>
							<li><a href="/Koncert/user/getZanroveZaPretragu">Pretraži po žanru</a></li>
						</ul></li>
					<li><a href="/Koncert/user/getKoncerti">Svi koncerti</a></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Moj profil <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/Koncert/user/getRezervisaneKarte">Rezervisane karte</a></li>
							<li><a href="/Koncert/user/getListuOmiljenihKoncerata">Lista omiljenih koncerata</a></li>
							<li><a href="/Koncert/koncerti/getProfil">Ažuriraj podatke</a></li>
							<li><a href="/Koncert/user/mojiKomentari">Moji komentari</a></li>
							<li><a href="/Koncert/auth/logout">Odjavi me</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container text-center">
		<c:if test="${!empty karte }">
				<table class="table table-bordered">
					<tr>
						<th>Izvodjač</th>
						<th>Datum izvođenja</th>
						<th>Trajanje</th>
						<th>Grad</th>
						<th>Adresa</th>
						<th>Cena karte</th>
						<th>Datum rezervacije</th>
					</tr>
					<c:forEach items="${karte }" var="k">
						<tr>
							<td>${k.koncert.izvodjac }</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${k.koncert.datumIzvodjenja }"/></td>
							<td>${k.koncert.trajanje }</td>
							<td>${k.koncert.grad }</td>
							<td>${k.koncert.adresa }</td>
							<td>${k.koncert.cena }</td>
							<td>${k.datumRezervacije }</td>
							<td><form action="/Koncert/user/otkaziRezervaciju?idK=${k.idKarta }" method="post"><button class="btn" type="submit">Otkaži rezervaciju</button></form></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			${poruka }
	</div>
</body>
</html>