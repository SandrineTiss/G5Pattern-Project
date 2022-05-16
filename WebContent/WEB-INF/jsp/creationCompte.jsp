<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html >
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
	
	
	
<title>Créer un compte</title>

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
	
	
	
	
<!-- FORMULAIRE CREATION DE COMPTE -->
<div class="container-fluid">
	<div class="col-md-12">
		<div class="row g-0">
			<div class="col-lg-12 col-md-12 col-sm-12 bg-light">
				<h2 id="titre">Mon Compte</h2>
			</div>
		</div>
		
		
		<form method="post" action="${pageContext.request.contextPath}/CreationUtilisateur"
					method="post" id="for_create">
			<div class="row g-0">
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">
					<table classe="affiche_profil">
						<tr>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><label for="pseudo">Pseudo* :</label></td>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><input type="text" name="pseudo" id="pseudo" required="required" value="<c:out value="${user_pseudo.pseudo}" />" /></td>
						</tr>
					</table>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">
					<table classe="affiche_profil">
						<tr>	
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><label for="nom">Nom* :</label></td>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><input type="text" name="nom" id="nom" required="required" value="<c:out value="${user_pseudo.nom}" />"/></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="row g-0">
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">
					<table classe="affiche_profil">
						<tr>	
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><label for="prenom">Prénom* :</label> </td>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><input type="text" name="prenom" id="prenom" required="required" value="<c:out value="${user_pseudo.prenom}" />"/></td>
						</tr>
					</table>
				</div>					
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">
					<table classe="affiche_profil">
						<tr>	
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><label for="email">E-mail* :</label> </td>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><input type="email" name="email" id="email" required="required" value="<c:out value="${user_pseudo.mail}"/>"/></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="row g-0">						
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">
					<table classe="affiche_profil">
						<tr>	
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><label for="phone">Téléphone* :</label> </td>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><input type="text" name="phone" id="phone" required="required" value="<c:out value="${user_pseudo.phone}"/>"/></td> 
						</tr>
					</table>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">
					<table classe="affiche_profil">
						<tr>	
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><label for="adresse">Rue* :</label> </td>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><input type="text" name="rue" id="rue" required="required" value="<c:out value="${user_pseudo.rue}"/>"/></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="row g-0">						
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">
					<table classe="affiche_profil">
						<tr>	
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><label for="adresse">Code Postal* :</label> </td>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><input type="text" name="cp" id="adresse" required="required" value="<c:out value="${user_pseudo.cp}"/>"/></td>
						</tr>
					</table>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">
					<table classe="affiche_profil">
						<tr>	
							
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><label for="adresse">Ville* :</label> </td>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><input type="text" name="ville" id="ville" required="required" value="<c:out value="${user_pseudo.ville}"/>"/></td>
						</tr> 
					</table> 
				</div>
			</div>
			
			<div class="row g-0">						
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">      
					<table classe="affiche_profil">
						<tr>	
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><label for="password">Mot de passe* :</label> </td>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><input type="password"	name="password" id="password" /></td>
						</tr>
					</table> 
				</div>
				<div class="col-lg-6 col-md-6 col-sm-12 bg-light">   
					<table classe="affiche_profil"> 
						<tr>									
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><label for="confirmation">Confirmation* :</label> </td>
							<td class="profil col-lg-6 col-md-6 col-sm-6 col-6"><input type="password"	name="confirmation" id="confirmation"/></td>
						</tr> 
					</table>
				</div>						
			</div>
			<div class="row g-0">
				<div class="col-lg-12 col-md-12 col-sm-12 bg-light center">
					<p>* Champs obligatoires</p>
				</div>
			</div>

				<div class="row g-0 btn-group-sm" role="group" aria-label="Un groupe de boutons">
					<div class="col-lg-3 col-md-3 col-sm-12 col-12 bg-light center">
					</div>
					<div class="col-lg-2 col-md-2 col-sm-6 col-6 bg-light center">
						<input type="submit" value="Creer" class="btn btn-sm mb-2 shadow p-3"/>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-6 col-6 bg-light center">
						<input type="reset" value="annuler" class="btn btn-sm mb-2 shadow p-3"/>
					</div>
					<div class="col-lg-2 col-md-2 col-sm-12 col-12 bg-light center">
						<a href="${pageContext.request.contextPath}/Connexion"><input type="button" value ="Retour" class="btn btn-sm mb-2 shadow p-3"/></a>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-12 col-12 bg-light center">
					</div>
			</div>
		</form>

	</div>
</div>





	<%@include file="../html/footer.html"%>
</body>
</html>