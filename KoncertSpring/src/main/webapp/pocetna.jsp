<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix='sec'
	uri='http://www.springframework.org/security/tags'%>
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
				<sec:authorize access="hasRole('radnik')">
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
				</sec:authorize>
				<sec:authorize access="hasRole('korisnik')">
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
					</sec:authorize>
				</ul>
			</div>
		</div>
	</nav>

	<div id="myCarousel" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
			<li data-target="#myCarousel" data-slide-to="2"></li>
		</ol>

		<!-- Wrapper for slides -->
		<div class="carousel-inner" role="listbox">
			<div class="item active">
				<img src="https://gurukulgroup.co.in/wp-content/uploads/2018/08/aditya-chinchure-494048-unsplash.jpg" alt="New York" width="900" height="500">
			</div>

			<div class="item">
				<img src="https://bibliotekadoljevac.org/wp-content/uploads/2018/08/austin-neill-247047-unsplash.jpg" alt="Chicago" width="900" height="500">
			</div>

			<div class="item">
				<img src="https://rockymountevents.com/wp-content/uploads/2019/06/Concert-crowd-with-silhouettes-of-people-hold-their-hands-up.jpg" alt="Los Angeles" width="900" height="500">
			</div>
		</div>

		<!-- Left and right controls -->
		<a class="left carousel-control" href="#myCarousel" role="button"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#myCarousel" role="button"
			data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>
	<script>
		$(document).ready(
				function() {
					// Initialize Tooltip
					$('[data-toggle="tooltip"]').tooltip();

					// Add smooth scrolling to all links in navbar + footer link
					$(".navbar a, footer a[href='#myPage']").on('click',
							function(event) {

								// Make sure this.hash has a value before overriding default behavior
								if (this.hash !== "") {

									// Prevent default anchor click behavior
									event.preventDefault();

									// Store hash
									var hash = this.hash;

									// Using jQuery's animate() method to add smooth page scroll
									// The optional number (900) specifies the number of milliseconds it takes to scroll to the specified area
									$('html, body').animate({
										scrollTop : $(hash).offset().top
									}, 900, function() {

										// Add hash (#) to URL when done scrolling (default click behavior)
										window.location.hash = hash;
									});
								} // End if
							});
				})
	</script>
</body>
</html>