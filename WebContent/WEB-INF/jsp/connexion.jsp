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
	
<title>Connexion</title>

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

	<div class="container-fluid ">

	<div class="row g-0">
		<div class="col-lg-3 col-md-3 col-sm-1 col-0 bg-light">
		</div>
		<div class="col-lg-6 col-md-6 col-sm-10 col-12 ">
				<div class="col-lg-12 col-md-12 col-sm-12 bg-light">
					<form action="${pageContext.request.contextPath}/Connexion" method="post" id="for_con">
						<div class="row g-0">
							<label class="col-lg-5 col-md-5 col-sm-5 col-5 col-5 bg-light " for="identifiant"> Identifiant :</label> 
							<input class="col-lg-5 col-md-5 col-sm-7 col-7 col-7 bg-light " type="text" name="identifiant" id="identifiant" required="required" />
						</div>
						<br/>
						<div class="row g-0">
							<label class="col-lg-5 col-md-5 col-sm-5 col-5 col-5 bg-light " for="password"> Mot de passe* :</label> 
							<input class="col-lg-5 col-md-5 col-sm-7 col-7 col-7 bg-light " type="password" name="password" id="password" required="required" />
						</div>
						<br/>
						<div class="row g-0">
							<div class="col-lg-5 col-md-5 col-sm-5 col-5  bg-light center">
								<input type="submit" value ="Connexion" class="btn  mb-2 shadow p-3"/>
							</div>
							<div class="col-lg-5 col-md-5 col-sm-7 col-7 bg-light">
								<input type="checkbox" value="Se souvenir de moi" id="cookie" name="cookie"> Se souvenir de moi<br /> 
								<a href="${pageContext.request.contextPath}/MotDePasseOublie">Mot de passe oublié</a>
							</div>
						</div>
						<br/>
						<div class="row g-0">
							<div class="col-lg-12 col-md-12 col-sm-12 col-12 bg-light center">
								<a href="${pageContext.request.contextPath}/CreationUtilisateur"><input type="button" value="Créer un compte"class=" btn mb-1 shadow p-3"/></a>
								<br /> <br /> 
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-1 col-0 bg-light">
			</div>
		</div>
	</div>
	<%@include file="../html/footer.html"%>
</body>
</html>