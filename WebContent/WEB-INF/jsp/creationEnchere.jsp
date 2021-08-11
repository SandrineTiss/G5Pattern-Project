<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<title>Créer une enchère</title>
</head>

<body>
 
	<%@include file="../html/header.html"%>

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
	
<!-- formulaire creation enchere -->
	<div class="container-fluid bg-light">


				<div class="row g-0">
					<div class="col-lg-4 col-md-4 col-sm-12 col-12 bg-light center">
					</div>
					<div class="col-lg-4 col-md-4 col-sm-12 col-12 bg-light">

							<form action="${pageContext.request.contextPath}/Enchere" method="post">
								<br/> 
								<table>
									<tr>
										<td><label for="article" class="my-2">Article :</label> </td>
										<td><input type="text" name="nomArticle" id="article" required="required" value="<c:out value=" ${nomArticle }" />" /></td> 
									</tr>
									<tr>
										<td><label for="description" class="my-2">Description :</label></td>
										<td><textarea id="description" name="description" placeholder="<c:out value=" ${description }" />"  rows="4" cols="24" >${description }</textarea></td>
									</tr>
										 
									<tr>
										<td><label for="categorie" class="my-2">Catégorie</label></td>
										<td><select style="width:135px" class="ms-3" id="categorie" name="categorie" value="<c:out value="${categorie }" />" />
												<option value="informatique" <c:if test="${categorie == 'informatique'}">selected</c:if>>Informatique</option>
												<option value="ameublement"<c:if test="${categorie == 'ameublement'}">selected</c:if>>Ameublement</option>
												<option value="vetement"<c:if test="${categorie == 'vetement'}">selected</c:if>>Vêtement</option>
												<option value="sportLoisir"<c:if test="${categorie == 'sportLoisir'}">selected</c:if>>Sport&Loisirs</option>
											</select>
										</td>
									</tr> 
									<tr>
										<td><label for="prixInit" class="my-2">Mise à prix :</label></td>
										<td><input type="number" name="prixInit" id="prixInit" min="0" size="10" required="required" value="${prixInit }"/></td>
									</tr>
									<tr>
										<td><label for="debutEnchere" class="my-2">Début de l'enchère</label></td>
										<td><input type="date" name="debutEnchere" id="debutEnchere" min="${dateDuJour}" value="${dateDuJour}" required="required" /></td>
									</tr>
									<tr>
										<td><label for="finEnchere" class="my-2">Fin de l'enchère</label> </td>
										<td><input type="date" name="finEnchere" id="finEnchere" min="${jourPlus1}" value="${jourPlus1}" required="required" /></td>
									</tr> 
								</table>
								<fieldset>
								<legend>Retrait</legend>
									<table>	
											<tr>
												<td><label for="adresse" class="my-2">Rue :</label> </td>
												<td><input type="text" name="rue" id="adresse" required="required" value="${rueUser}" /></td>
											</tr>
											<tr>
												<td><label for="adresse" class="my-2">Code Postal :</label> </td>
												<td><input type="text" name="cp" id="adresse" required="required" value="${cpUser}"/></td>
											</tr>
											<tr>
												<td><label for="adresse" class="my-2">Ville :</label> </td>
												<td><input type="text" name="ville" id="adresse" required="required" value="${villeUser}"/></td>
											</tr>
									</table>
								</fieldset>
								<br/>
								<div class="row g-0 ">
									<div class="col-lg-6 col-md-6 col-sm-7 col-7 bg-light center">
										<input type="submit" class=" btn mb-1 shadow p-3" value ="Enregistrer"/>
									</div>
									<div class="col-lg-6 col-md-6 col-sm-5 col-5 bg-light center">
										<a href="${pageContext.request.contextPath}/Accueil"><input type="button" class=" btn mb-1 shadow p-3" value ="Annuler"/></a>
									</div>
								</div>
								<br/>
							</form>
						</div>
						<div class="col-lg-4 col-md-4 col-sm-12 col-12 bg-light center">
						</div>
					</div>
				</div>


	<%@include file="../html/footer.html"%>
</body>
</html>