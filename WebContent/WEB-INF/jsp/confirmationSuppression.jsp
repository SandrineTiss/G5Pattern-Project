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
	
<title>Suppression compte</title>

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
	
	
	


	<div class="container-fluid">
		<div class="col-md-12">
			<div class="row g-0">
				<div class="col-lg-12 col-md-12 col-sm-12 bg-light">
						<form action="${pageContext.request.contextPath}/SupprimerMonCompte"
							method="post" id="for_con">
							<br /> 
							<label for="identifiant">Etes-vous sûr de vouloir supprimer votre compte ?</label> 
							<br/>
							<br/>
							<label for="password">Saisissez votre mot de passe pour valider la suppression :</label>
							<input type="password" name="password" id="password" required="required" />
							<input type="submit" value="Supprimer mon compte définitivement" id="con-button" />
						</form>
					</div>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 bg-light">
			<br/><br/>
			</div>
	</div>


	<%@include file="../html/footer.html"%>
</body>
</html>