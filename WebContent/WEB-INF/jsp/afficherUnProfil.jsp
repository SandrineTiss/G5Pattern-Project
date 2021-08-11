<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<title>Profil Utilisateur</title>

<link href="css/styles.css" rel="stylesheet">

</head>

<body>

	<%@include file="../html/header.html"%>


	
			<!-- Affichage du module de connexion -->

	<nav class="navbar navbar-light bg-light px-3">
		<div class="container-fluid">
			<a href="${pageContext.request.contextPath}/Accueil" class="navbar-brand"><img src="img/logoG5.png" alt="logo Team G5"></a>
		</div>
	</nav>
	
		<!-- Affichage des Erreurs s'il y en a -->

	<div class="row">
		<c:if test="${!empty error}">
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100 alert">
					<div class="card-body alert alert-danger">
						<h2>Erreur</h2>
						<p class="card-text">${error}</p>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	
	
	<!-- Affichage du profil s'il n'y a pas d'erreur -->

	<div class="container-fluid ">
		<div class="col-md-12">
			<div class="row g-0">
				<div class="col-lg-12 col-md-12 col-sm-12 bg-light">
					<h2 id="titre">Profil</h2>
				</div>
				<div class="col-lg-4 col-md-4 bg-light">
				</div>
				<div class="col-lg-8 col-md-8 col-sm-12 bg-light">
					<table>
						<tr>
							<td class="profil">Pseudo :</td>
							<td class="profil">${user_pseudo.pseudo}</td>
						</tr>
						<tr>
							<td class="profil">Nom :</td>
							<td class="profil">${user_pseudo.nom}</td>
						</tr>
						<tr>
							<td class="profil">Prénom :</td>
							<td class="profil">${user_pseudo.prenom}</td>
						</tr>
						<tr>
							<td class="profil">Email :</td>
							<td class="profil">${user_pseudo.mail}</td>
						</tr>
						<tr>
							<td class="profil">Téléphone :</td>
							<td class="profil">${user_pseudo.phone}</td>
						</tr>
						<tr>
							<td class="profil">Rue :</td>
							<td class="profil">${user_pseudo.rue}</td>
						</tr>
						<tr>
							<td class="profil">Code postal :</td>
							<td class="profil">${user_pseudo.cp}</td>
						</tr>
						<tr>
							<td class="profil">Ville :</td>
							<td class="profil">${user_pseudo.ville}</td>
						</tr>

					</table>
				</div>
			</div>
		</div>
	
		<c:if test="${monProfil}" >
			<div class="row g-0">
				<div class="col-lg-4 col-md-4 bg-light">
				</div>
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">
					<table classe="affiche_profil">
						<tr>									
							<td class="profil">Crédit :</td>
							<td class="profil">${user_pseudo.credit}</td>
						</tr>
					</table>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-12 bg-light">
				</div>
			</div>
			<div class="row g-0">
				<div class="col-lg-4 col-md-4 bg-light">
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-6 bg-light center">
					<a href="${pageContext.request.contextPath}/ModifierMonProfil"><input type="button" class="btn btn-sm mb-2 shadow p-3" name="modifier_profil" value="Modifier"></a>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-6 col-6 bg-light center">
					<a href="${pageContext.request.contextPath}/Accueil"><input type="button" value ="Retour" class="btn btn-sm mb-2 shadow p-3"/></a>
				</div>
				<div class="col-lg-4 col-md-4 bg-light">
				</div>
			</div>
		</c:if>
		<c:if test="${!monProfil}" >
			<div class="row g-0">
				<div class="col-lg-4 col-md-4 bg-light">
				</div>
				<div class="col-lg-4 col-md-4 col-sm-12 col-12 bg-light center">
					<a href="${pageContext.request.contextPath}/Accueil"><input type="button" value ="Retour" class="btn btn-sm mb-2 shadow p-3"/></a>
				</div>
				<div class="col-lg-4 col-md-4 bg-light">
				</div>
			</div>
		
		
		</c:if>
	</div>
	
	



	<%@include file="../html/footer.html"%>
</body>
</html>