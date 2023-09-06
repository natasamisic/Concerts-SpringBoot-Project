<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>SvetMuzike</title>
<style>table{box-shadow: 1px 1px 2px 2px;}</style>
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
							<li><a href="/Koncert/radnik/pretragaKoncerta.jsp">Pretraži po izvođaču</a></li>
							<li><a href="/Koncert/worker/getZanroveZaPretragu">Pretraži po žanru</a></li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Prikazi<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/Koncert/worker/getSviKoncerti">Prikaz svih koncerata</a></li>
							<li><a href="/Koncert/worker/getKoncertiKarte">Prikaz rezervisanih karata</a></li>
							<li><a href="/Koncert/worker/getKoncerteBrKarata">Prikaz broja rezervisanih karata</a></li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Dodaj<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/Koncert/worker/getZanrove">Dodaj koncert</a></li>
							<li><a href="/Koncert/worker/noviRadnik">Dodaj radnika</a></li>
							<li><a href="/Koncert/radnik/dodajZanr.jsp">Dodaj žanr</a></li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Moj profil <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/Koncert/koncerti/getProfil">Ažuriraj podatke</a></li>
							<li><a href="/Koncert/auth/logout">Odjavi me</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container text-center">
		<form action="/Koncert/worker/getKarte" method="get">
			Odaberite izvodjača koncerta:
					<select name="koncert">
						<c:forEach items="${koncertiKar }" var="kon">
							<option value="${kon.idKoncert }">${kon.izvodjac }</option>
						</c:forEach>
					</select>
				<input class="btn" type="submit" value="Prikaži">
		</form><br><br>
		<c:if test="${!empty karte }">
			<table class="table table-bordered">
				<tr>
					<th>Korisnik</th>
					<th>Izvodjač</th>
					<th>Datum rezervacije</th>
				</tr>
				<c:forEach items="${karte }" var="k">
					<tr>
						<td>${k.korisnik.ime } ${k.korisnik.prezime }</td>
						<td>${k.koncert.izvodjac }</td>
						<td>${k.datumRezervacije }</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		${poruka }
	</div>
</body>
</html>