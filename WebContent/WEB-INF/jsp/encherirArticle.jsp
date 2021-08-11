<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<title>Enchérir</title>

<link href="css/styles.css" rel="stylesheet">

</head>

<body>

	<%@include file="../html/header.html"%>

	
			<!-- Affichage du module de connexion -->

	<nav class="navbar navbar-light bg-light px-3">
		<div class="container-fluid">
			<a href="${pageContext.request.contextPath}/Accueil" class="navbar-brand"><img src="img/logoG5.png"  alt="logo Team G5"></a>
		</div>
	</nav>
	
	
	<!-- Affichage des Erreurs s'il y en a -->

	<div class="row">
		<c:if test="${!empty error}">
			<div class="col-lg-12 col-md-12 col-sm-12 portfolio-item">
				<div class="card h-100 alert">
					<div class="card-body alert alert-danger">
						<p class="card-text center">${error}</p>
					</div>
					<c:if test="${user.credit<=article.getDerniereEnchere().getMontantEnchere()}">
						<p class="probleme text-center text-danger">Vous n'avez pas assez de crédit pour enchérir sur cet article</p>
					</c:if>
				</div>
			</div>
		</c:if>
		<c:if test="${article.getDerniereEnchere().getUtilisateur().getPseudo().equals(user.pseudo)}">
			<div class="col-lg-12 col-md-12 col-sm-12 col-12 portfolio-item">
				<div class="card h-100 info">
					<div class="card-body alert alert-info titre">
						<h2 class="card-text center">info : </h2>
						<p class="card-text center">Vous êtes déjà le dernier enchérisseur sur cet article</p>
					</div>
				</div>
			</div>								
		</c:if>
	</div>

	
	
	<!-- Affichage de l'article s'il n'y a pas d'erreur -->

	<div class="container-fluid bg-light">
		<div class="row g-0">
			<div class="col-lg-12 col-md-12 col-sm-12 col-12 bg-light">
				<h2 id="titre">Détail vente</h2>
			</div>
		</div>
		
		
		
		<div class="col-lg-12 col-md-12 col-sm-12 col-12">
				<div class="row g-0">
					<div class="col-lg-4 col-md-4 d-none d-md-block d-lg-block d-xl-block">
						<div class=" bg-light ">
							<img src="${pageContext.request.contextPath}/img/" alt="image de l'article" class="img-fluid rounded-start">
						</div>
					</div>	
					<div class="col-lg-8 col-md-8 col-sm-12 bg-light">
						<table>
							<tr>
								<td class="profil" colspan="2" ><h4>${article.nomArticle}</h4></td>
							</tr>
								<tr class="d-md-none d-lg-none d-xl-none">
									<td colspan="2" width="100%">
										<div class="bg-light">
											<img src="${pageContext.request.contextPath}/img/" alt="image de l'article">
										</div>
									</td>
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
								<td class="profil">Fin de l'enchère :</td>
								<td class="profil">${article.finEnchere}</td>
							</tr>
							<tr>
								<td class="profil">Retrait :</td>
								<td class="profil">${article.getRetrait().getNomRue()} <br/> ${article.getRetrait().getCodePostal()}  ${article.getRetrait().getNomVille()}  </td>
							</tr>
							<tr>
								<td class="profil">Vendeur :</td>
								<td class="profil">${article.getVendeur().getPseudo() }</td>
							</tr>
							<tr>	
								<c:if test="${user.credit > article.getDerniereEnchere().getMontantEnchere()}">
									<c:if test="${!terminee}">
										<c:if test="${!article.getDerniereEnchere().getUtilisateur().getPseudo().equals(user.pseudo)}">
											<td class="profil">Ma proposition :</td>
											<td class="profil">
												<form action="${pageContext.request.contextPath}/EncherirArticle" method="post">
													<input type="text" hidden="hidden" name="idArticle" value="${article.getNo_article()}">
													<input type="text" hidden="hidden" name="noEncherisseur" value="${user.no_users}">
													<input type="text" hidden="hidden" name="encherisseurPrecedent" value="${article.getDerniereEnchere().getUtilisateur().getPseudo()}">
													<input type="text" hidden="hidden" name="derniereEnchere" value="${article.getDerniereEnchere().getMontantEnchere()}">
													<input type="text" hidden="hidden" name="creditEncherisseur" value="${user.credit}">
													<input type="text" hidden="hidden" name="creditEncherisseur" value="${user.pseudo}">
													<input type="number" id="proposition" min="${article.getDerniereEnchere().getMontantEnchere()+1}" max="${user.credit}" name="proposition">
													<input type="submit" value="Enchérir" class=" btn mb-1 shadow p-1">
												</form>
											</td>
										</c:if>
									</c:if>
								</c:if>
							</tr>
						</table>
					</div>
				</div>
				<div class="row g-0 btn-group-sm" role="group" aria-label="Un groupe de boutons">
					<div class="col-lg-3 col-md-3 col-sm-3 col-4 bg-light center">
					</div>
					<div class="col-lg-6 col-md-6 col-sm-6 col-8 bg-light center">
						<a href="${pageContext.request.contextPath}/Accueil"><input type="button" value ="Retour" class="btn btn-sm mb-2 shadow p-3"/></a>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-3 col-12 bg-light center">
					</div>
				</div>
			</div>
		</div>

	
	



	<%@include file="../html/footer.html"%>
</body>
</html>