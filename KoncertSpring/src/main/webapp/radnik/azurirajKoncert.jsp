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
	<link href="${pageContext.request.contextPath }/styles/pocetna.css" rel="stylesheet" >
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
	<div class="container">
	<c:if test="${!empty koncertA }">
			<form action="/Koncert/worker/azuriraj" method="post">
				<div class="form-group">
    				<label>Izvodjač:</label>
     				<input class="form-control" type="text" name="izvodjac" value="${koncertA.izvodjac }"/>
	 			 </div>
	 			 <div class="form-group">
    				<label>Datum izvođenja:</label>
     				<input class="form-control" name="datumIzvodjenja" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${koncertA.datumIzvodjenja }"/>'/>
	 			 </div>
	 			 <div class="form-group">
	    			<label>Max broj karata:</label>
	     			<input class="form-control" name="maxBrojKarata" value="${koncertA.maxBrojKarata }"/>
	 			 </div>
	 			 <div class="form-group">
	    			<label>Trajanje:</label>
					<input class="form-control" name="trajanje" value="${koncertA.trajanje }"/>
	 			 </div>
	 			 <div class="form-group">
	    			<label>Opis:</label>
					<input class="form-control" type="text" name="opis" value="${koncertA.opis}"/>
	 			 </div>
	 			 <div class="form-group">
	    			<label>Grad:</label>
					<input class="form-control" type="text" name="grad" value="${koncertA.grad }"/>
	 			 </div>
				 <div class="form-group">
		    		<label>Adresa:</label>
		     		<input class="form-control" type="text" name="adresa" value="${koncertA.adresa }"/>
	 			 </div>
	 			 <div class="form-group">
    				<label>Cena karte:</label>
     				<input class="form-control" type="text" name="cena" value="${koncertA.cena }"/>
	 			 </div>
	 			 <input class="btn" type="submit" value="Sačuvaj">
			</form>
	</c:if><br>
	<c:if test="${!empty azuriran}">
			Koncert posle ažuriranja:<br>
			<table class="table table-bordered">
				<tr>
					<th>Izvodjač</th>
					<th>Datum izvođenja</th>
					<th>Max broj karata</th>
					<th>Trajanje</th>
					<th>Grad</th>
					<th>Adresa</th>
					<th>Cena karte</th>
					<th>Opis</th>
				</tr>
				<tr>
					<td>${azuriran.izvodjac }</td>
					<td><fmt:formatDate pattern = "yyyy-MM-dd" value = "${azuriran.datumIzvodjenja }" /></td>
					<td>${azuriran.maxBrojKarata }</td>
					<td>${azuriran.trajanje }</td>
					<td>${azuriran.grad }</td>
					<td>${azuriran.adresa }</td>
					<td>${azuriran.cena }</td>
					<td>${azuriran.opis }</td>
				</tr>
			</table>
	</c:if>
	${poruka }
	</div>
</body>
</html>