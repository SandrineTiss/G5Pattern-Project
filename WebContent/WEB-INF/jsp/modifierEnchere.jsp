<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Modifier enchère</title>
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
	<div class="row">
		<c:if test="${!empty info}">
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100 info">
					<div class="card-body alert alert-info">
						<h2>info</h2>
						<p class="card-text">${info}</p>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	
<!-- formulaire creation enchere -->
	<div class="container-fluid bg-light">
		<div class="row g-0">
			<div class="col-md-4 col-sm-12 col-12 col-lg-4  bg-light">
			</div>
			<div class="col-md-4 col-sm-12 col-12 col-lg-4  bg-light">
					<br/> 
					<c:choose>
						<c:when test="${article.debutEnchere <= dateDuJour || debutEnchere<=dateDuJour }">
							<h5 class="m-0 text-center">Enchère en cours</h5>
							<br/>
							<table>
								<tr>
									<td class="profil">Vendeur :</td>
									<td class="profil">${article.getVendeur().getPseudo() }</td>
								</tr>
								<tr>
									<td class="profil" colspan="2" ><h5>${article.nomArticle}</h5></td>
								</tr>
								<tr >
									<td class="profil">Description :</td>
									<td class="profil">${article.description}</td>
								</tr>
								<tr>
									<td class="profil">Catégorie :</td>
									<td class="profil">${article.getCategorie().getLibelle()}</td>
								</tr>
								<tr>
									<td class="profil">Meilleure Offre :</td>
									<td class="profil">${article.getDerniereEnchere().getMontantEnchere()} pts par ${article.getDerniereEnchere().getUtilisateur().getPseudo()}</td>
								</tr>
								<tr>
									<td class="profil">Mise à prix :</td>
									<td class="profil">${article.prixInit}</td>
								</tr>
								<tr>
									<td class="profil">Enchère démarrée depuis: </td>
									<td class="profil"> ${article.debutEnchere}</td>
								</tr>
								<tr>
									<td class="profil">Enchère se fini le: </td>
									<td class="profil"> ${article.finEnchere}</td>
								</tr>
								<tr>
									<td class="profil">Retrait :</td>
									<td class="profil">${article.getRetrait().getNomRue()} <br/> ${article.getRetrait().getCodePostal()}  ${article.getRetrait().getNomVille()}  </td>
								</tr>
								<tr>
									<td colspan="2"><a href="${pageContext.request.contextPath}/Accueil"><input type="button" class=" btn mb-1 shadow p-3" value ="Retour"/></a></td>
								</tr>
							</table>
							
						</c:when>
							
						<c:otherwise>
							<h5 class="m-0 text-center">Modification enchère</h5>
							<br>
							<form action="${pageContext.request.contextPath}/AfficherUneEnchere" method="post">
								<table>
									<tr>
										<td class="profil">Vendeur :</td>
										<td class="profil">${article.getVendeur().getPseudo() }</td>
									</tr>
									<tr>
										<td><label for="article" class="my-2">Article :</label> </td>
										<td><input type="text" name="nomArticle" id="article" value="${article.nomArticle}" required="required" /></td> 
									</tr>
									<tr>
										<td><label for="description" class="my-2">Description :</label></td>
										<td><textarea id="description" name="description" rows="3" cols="20">${article.description}</textarea></td>
									</tr>
									<tr>
										<td><label for="categorie" class="my-2">Catégorie</label></td>
										<td><select style="width:135px" class="ms-3" id="categorie" name="categorie">
												<option value="informatique" <c:if test="${article.getCategorie().getLibelle() == 'informatique'}">selected="selected"</c:if>>Informatique</option>
												<option value="ameublement" <c:if test="${article.getCategorie().getLibelle() == 'ameublement'}">selected="selected"</c:if>>Ameublement</option>
												<option value="vetement" <c:if test="${article.getCategorie().getLibelle() == 'vetement'}">selected="selected"</c:if>>Vêtement</option>
												<option value="sportLoisir" <c:if test="${article.getCategorie().getLibelle() == 'sportLoisir'}">selected="selected"</c:if>>Sport&Loisirs</option>
											</select>
										</td>
									</tr>
									<tr>
										<td><label for="prixInit" class="my-2">Mise à prix :</label></td>
										<td><input type="number" name="prixInit" id="prixInit" min="0" size="10" value="${article.prixInit}" required="required" /></td>
									</tr>
									<tr>
										<td><label for="debutEnchere" class="my-2">Début de l'enchère</label></td>
										<td><input type="date" name="debutEnchere" id="debutEnchere" min="${dateDuJour}" value="${article.debutEnchere}" required="required" /></td>
									</tr>
									<tr>
										<td><label for="finEnchere" class="my-2">Fin de l'enchère</label></td>
										<td><input type="date" name="finEnchere" id="finEnchere" min="${jourPlus2}" value="${article.finEnchere}" required="required" /></td>
									</tr>
								</table>
								<br>
								<fieldset><legend>Retrait</legend>
									<table>
										<tr>
											<td><label for="adresse" class="my-2">Rue :</label> </td>
											<td><input type="text" name="rue" id="adresse" required="required" value="${article.getRetrait().getNomRue()}" /></td>
										</tr>
										<tr>
											<td><label for="adresse" class="my-2">Code Postal :</label> </td>
											<td><input type="text" name="cp" id="adresse" required="required" value="${article.getRetrait().getCodePostal()}"/></td>
										</tr>
										<tr>
											<td><label for="adresse" class="my-2">Ville :</label> </td>
											<td><input type="text" name="ville" id="adresse" required="required" value="${article.getRetrait().getNomVille()}"/></td>
										</tr>

										
	   								</table>
	  								</fieldset>	
	  								<input type="text" hidden="hidden" name="idArticle" value="${article.getNo_article()}">
	  								<input type="text" hidden="hidden" name="vendeur" value="${vendeur}">
	  								<table>
	  									<tr>
											<td><input type="submit" class=" btn mb-1 shadow p-3" value ="Enregistrer"/></td>
											<td><a href="${pageContext.request.contextPath}/Accueil"><input type="button" class=" btn mb-1 shadow p-3" value ="Retour"/></a></td>
										</tr>
										<tr>
											<td colspan="2"><a href="${pageContext.request.contextPath}/SupprimerUneVente?idArticle=${article.getNo_article()}"><input type="button" class=" btn mb-1 shadow p-3" value ="Annuler la vente"/></a></td>
										</tr>
	 								</table>
									
									
							</form>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="col-md-4 col-sm-12 col-12 col-lg-4 bg-light">
			</div>
		</div>




	<%@include file="../html/footer.html"%>
</body>
</html>