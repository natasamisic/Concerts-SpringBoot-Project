<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
</head>
<body id="myPage" data-spy="scroll" data-target=".navbar"
	data-offset="50">
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
	<div class="container">
		<form action="/Koncert/koncerti/azurirajPodatke" method="post">
				<div class="form-group">
    				<label>Ime:</label>
     				<input class="form-control" type="text" name="ime" value="${korisnik.ime}"/>
	 			 </div>
	 			 <div class="form-group">
	    			<label>Prezime:</label>
	     			<input class="form-control" name="prezime" value="${korisnik.prezime}"/>
	 			 </div>
	 			 <div class="form-group">
	    			<label>Email:</label>
					<input class="form-control" name="email" value="${korisnik.email}"/>
	 			 </div>
	 			 <div class="form-group">
	    			<label>Username:</label>
					<input class="form-control" type="text" disabled="disabled" value="${korisnik.username}"/>
	 			 </div>
	 			 <input class="btn" type="submit" value="Sačuvaj">
		</form>
		${poruka}
	</div>
	<div class="container">
		<button class="btn" data-toggle="modal" data-target="#myModal">Želite da obrišete Vaš profil?</button>
	</div>
	<!-- Modal -->
	  <div class="modal fade" id="myModal" role="dialog">
	    <div class="modal-dialog">
	    
	      <!-- Modal content-->
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">×</button>
	          <h4><span class="glyphicon glyphicon-lock"></span> Brisanje profila</h4>
	        </div>
	        <div class="modal-body">
	          <form action="/Koncert/koncerti/obrisiProfil" method="post">
	            <div class="form-group">
	              <label for="psw">Sigurno želite da obrišete Vaš profil?</label>
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