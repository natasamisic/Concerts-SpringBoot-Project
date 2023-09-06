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
<title>SvetMuzike</title>

<style>
	img{
		width:500px;
		height:600px;
		object-fit:contain;
		border: 1px solid #ddd;
 		padding: 5px;
	}
	textarea {
	  width: 600px;
	  height: 150px;
	  padding: 12px 20px;
	  box-sizing: border-box;
	  border: 2px solid #ccc;
	  border-radius: 4px;
	  background-color: #f9f9f9;
	  font-size: 16px;
	  box-shadow: 2px 2px 2px 2px;
	}
	table{box-shadow: 1px 1px 2px 2px;}
</style>
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
	
	<c:if test="${!empty Kkoncert }"><br><br>
		<div class="container text-center">
			<table class="table table-bordered">
				<tr>
					<th>Izvodjač</th>
					<th>Datum izvođenja</th>
					<th>Trajanje</th>
					<th>Grad</th>
					<th>Adresa</th>
					<th>Cena karte</th>
					<th>Opis</th>
					<th>Rezervacija</th>
				</tr>
				<tr>
					<td>${Kkoncert.izvodjac }</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${Kkoncert.datumIzvodjenja }"/></td>
					<td>${Kkoncert.trajanje }</td>
					<td>${Kkoncert.grad }</td>
					<td>${Kkoncert.adresa }</td>
					<td>${Kkoncert.cena }</td>
					<td>${Kkoncert.opis }</td>
					<td><form action="/Koncert/worker/getKoncertZaAzuriranje"><button class="btn" type="submit">Ažuriraj podatke</button></form></td>
				</tr>
			</table><br>
			<button class="btn" data-toggle="modal" data-target="#myModal">Obriši koncert</button>
		</div>	
		<div class="container">
			<c:if test="${!empty komentari }">
				<label>Komentari:</label><br><br>
				<c:forEach items="${komentari }" var="k">
					<div class="form-group">
	    				<label>-${k.korisnik.username }-</label><br>
	     				<textarea disabled="disabled">${k.tekst }</textarea>
		 			 </div>
				</c:forEach>
			</c:if>
			<form method="post" action="/Koncert/worker/saveSlika" enctype="multipart/form-data">
				    Postavite sliku: <input class="btn" type="file" name="file" /><br>
		   			<input class="btn" type="submit" value="Postavi" />
			</form>
			${porukaOSlici }
		</div>
		<div class="container">	
			<c:if test="${!empty slike }">
				<label>Slike:</label><br><br>
				<c:forEach items="${slike }" var="s">
					 <img src="/Koncert/koncerti/getSlika?idS=${s.idSlika}">   
				</c:forEach>
			</c:if>  
		</div>
	</c:if>
	<div class="container text-center">${porukaOBrisanju }</div>	
	
	<!-- Modal -->
	  <div class="modal fade" id="myModal" role="dialog">
	    <div class="modal-dialog">
	    
	      <!-- Modal content-->
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">×</button>
	          <h4><span class="glyphicon glyphicon-lock"></span> Brisanje koncerta</h4>
	        </div>
	        <div class="modal-body">
	          <form action="/Koncert/worker/obrisiKoncert" method="post">
	            <div class="form-group">
	              <label for="psw">Želite da obrišete koncert i sve njegove podatke?</label>
	            </div>
	              <input type="submit" class="btn btn-block" value="Obriši"> 
	          </form>
	        </div>
	        <div class="modal-footer">
	          <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal">
	            <span class="glyphicon glyphicon-remove"></span> Otkaži
	          </button>
	        </div>
	      </div>
	    </div>
	  </div>
</body>
</html>