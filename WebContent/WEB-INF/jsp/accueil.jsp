<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport"
				content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
		<link href="css/styles.css" rel="stylesheet">
		<title>Page d'accueil</title>
	</head>
<body>
	

	<%@include file="../html/header.html"%>
	
	
		<!-- Affichage des Erreurs s'il y en a -->
<div class="container-fluid px-1 bg-light">
	

	<!-- modification de l'affichage de la navbar suivant présence d'un login-->
	<c:choose>
		<c:when test="${!empty pseudo}">
			<nav class="navbar navbar-light bg-light">
					<div class="col-md-12">
						<div class="row g-0">
							<a class="col-lg-4 col-md- col-sm-12"href="${pageContext.request.contextPath}/Accueil" class="navbar-brand"><img src="img/logoG5.png" alt="logo Team G5"></a>
							<a class="col-lg-2 col-md-2 col-sm-3" href="${pageContext.request.contextPath}/Enchere">Vendre un article </a>
						 	<a class="col-lg-2 col-md-2 col-sm-3" href="${pageContext.request.contextPath}/AfficherMonProfil" name="pseudo" value="${pseudo}">Mon profil: ${pseudo }</a>
						 	<a class="col-lg-2 col-md-2 col-sm-3" href="${pageContext.request.contextPath}/SeDeconnecter">Déconnexion</a>
						</div>
				</div>
			</nav>
		</c:when>
		<c:otherwise>
			<nav class="navbar navbar-light bg-light">
				<div class="container-fluid">
					<a href="${pageContext.request.contextPath}/Accueil" class="navbar-brand"><img src="img/logoG5.png" alt="logo Team G5"></a>
					<a href="${pageContext.request.contextPath}/Connexion">S'inscrire - Se connecter</a>
				</div>
			</nav>
		</c:otherwise>
	</c:choose>

	<div class="container-fluid px-1 bg-light">
		<h4 class="py-3 text-center">Liste des enchères</h4>
		<form action="${pageContext.request.contextPath}/RechercherEncheres2" method="POST">
		
	<div class="row">
		<c:if test="${!empty error}">
			<div class="col-lg-12 col-md-12 col-sm-12 col-12">
				<div class="card h-100 alert alert-danger ">
						<h2 class="card-text center">Problème :</h2>
						<p class="card-text center">${error}</p>
				</div>
			</div>
		</c:if>
	</div>	
		
		
		
	<div class="row">
		<c:if test="${!empty info}">
			<div class="col-lg-12 col-md-12 col-sm-12 col-12">
				<div class="card h-100 info alert alert-info">
						<h2 class="card-text center">info : </h2>
						<p class="card-text center">${info}</p>
				</div>
			</div>
		</c:if>
	</div>
		
		
		
		
		
		

						<!-- affichage champs de recherche déconnecté -->
				<div class="container-fluid bg-light">
							<div class="row g-0">
								<div class="col-lg-12 col-md-12 col-sm-12 bg-light">
									<label for="filtres">Filtres:</label><br/>  <br/>  
								</div>
							</div>
							
							<div class="row g-0">
							
								<div class="col-lg-9 col-md-9 col-12">
								
									<div class="col-lg-8 col-md-8 col-12">
										<div class="row g-0">
											<div class="row g-0">
												<div class="col-lg-12 col-md-12 col-sm-12 bg-light">
													<input type="text" class="form-control " placeholder="<c:if test="${!empty filtres}">${filtres}</c:if><c:if test="${empty filtres}">filtrer par mot-clé</c:if>" name="filtres" >
												</div>
											</div>
											
											<div class="row g-0">
												<br/>
											</div>
											
											<div class="row g-0">
												<div class="col-lg-4 col-md-4 col-sm-3 bg-light">
													<label for="categorie">Catégorie :</label>
												</div>
												<div class="col-lg-8 col-md-8 col-sm-9 col-9 bg-light">
													<select style="width:300px" class="ms-3" name="categorie">
														<option value="toutes"<c:if test="${categorie.equals(toutes)}">selected</c:if>>Toutes</option>
														<option value="informatique" <c:if test="${categorie == 'informatique'}">selected</c:if>>Informatique</option>
														<option value="ameublement"<c:if test="${categorie == 'ameublement'}">selected</c:if>>Ameublement</option>
														<option value="vetement"<c:if test="${categorie == 'vetement'}">selected</c:if>>Vêtement</option>
														<option value="sportLoisir"<c:if test="${categorie == 'sportLoisir'}">selected</c:if>>Sport&Loisirs</option>
													</select>
												</div>
					
											</div>
										</div>
									</div>
	
									<div class="row g-0">
									<br/>
									</div>
									
									
									<div class="column col-12 col-lg-9 col-md-9">
											<!-- affichage de nouvelles options suivant présence login -->
											<c:if test="${!empty pseudo}"> 
								 				<div class="row g-0">
								  					<div class="column col-12 col-lg-3 col-md-4">
														<input type="radio" id="achat" name="choix" value="achats" onclick="this.form.submit()" <c:if test="${achat.equals('achats')}">checked="checked"</c:if>>
														<label for="achat">Achats</label><br/>
														<div class="row">
															<div class="column col-1 col-lg-1 col-md-1">
															</div>
															<div class="column col-11 col-lg-11 col-md-11">
											  					<input type="checkbox" name="enchereOuvert" <c:if test="${vente.equals('ventes')}">disabled="disabled"</c:if> <c:if test="${enchereOuvert.equals('on') || (not(enchereEnCours.equals('on')) && not(enchereGagne.equals('on')))}">checked="checked"</c:if> >
											  					<label for="ouvert">Enchères ouvertes</label><br>
											  					<input type="checkbox" name="enchereEnCours" <c:if test="${vente.equals('ventes')}">disabled="disabled"</c:if> <c:if test="${enchereEnCours.equals('on')}">checked="checked"</c:if>>
											  					<label for="enCours">Mes enchères</label><br>
											  					<input type="checkbox" name="enchereGagne" <c:if test="${vente.equals('ventes')}">disabled="disabled"</c:if> <c:if test="${enchereGagne.equals('on')}">checked="checked"</c:if>>
											  					<label for="gagne">Enchères gagnées</label><br>
											  				</div>
											  			</div>
									  				</div>
									  				<div class="column col-12 col-lg-3 col-md-4">
										  				<input type="radio" id="vente" name="choix" value="ventes" onclick="this.form.submit()" <c:if test="${vente.equals('ventes')}">checked="checked"</c:if>>
										  				<label for="vente">Mes ventes</label><br/>
										  				<div class="row">
															<div class="column col-1 col-lg-1 col-md-1">
															</div>
															<div class="column col-11 col-lg-11 col-md-11">
											  					<input type="checkbox" name="venteEnCours" <c:if test="${achat.equals('achats')}">disabled="disabled"</c:if> <c:if test="${venteEnCours.equals('on')}">checked="checked"</c:if>>
											  					<label for="venteEnCours">Ventes en cours</label><br>
											  					<input type="checkbox" name="venteNonDebute" <c:if test="${achat.equals('achats')}">disabled="disabled"</c:if> <c:if test="${venteNonDebute.equals('on') || (not(venteEnCours.equals('on')) && not(venteTermine.equals('on')))}">checked="checked"</c:if>>
											  					<label for="nonDebute">Ventes à venir</label><br>
											  					<input type="checkbox" name="venteTermine" <c:if test="${achat.equals('achats')}">disabled="disabled"</c:if> <c:if test="${venteTermine.equals('on')}">checked="checked"</c:if>>
											  					<label for="termine">Ventes terminées</label><br/><br/>
										  					</div>
										  				</div>
									  				</div>
										  		</div>	
											</c:if>
											
										</div>
									</div>
									<div class="col-lg-3 col-md-3 col-12">
										<div class="col-lg-4 col-md-4 col-12 center"> 	
							  				<br/>
										  	<div class="input-group-append">
										   	 	<input type="submit" value="Rechercher" class=" btn mb-5 shadow p-5">
										  	</div>
							 			</div>
									</div>
								</div>
						
							</div>
						 
				</form>
				
				
				</div>
					<div class="row g-0">
					<c:if test="${!empty rech}">
						<div class="col-lg-12 col-md-12 col-sm-12 col-12">
							<div class="card h-100 info alert alert-secondary">
									<p class="card-text center">${rech}</p>
							</div>
						</div>
					</c:if>
				</div>
	
	
	
	
	<c:if test="${!empty pseudo}">
		<div class="container-fluid bg-light">
		<div class="row g-0 bg-light">
				<c:forEach var="article" items="${affichageDemande }">
					<div class="card mb-3 bg-light" style="max-width: 450px;">
							<div class="card-body bg-light">
								<div class="row g-0">
									<div class="col-4 col-lg-4 col-md-4 bg-light">
										<img src="" class="img-fluid rounded-start" alt="image article">
									</div>
									<div class="col-8 col-lg-8 col-md-8 bg-light"> <!-- ${article.no_article} --> 
										<a href="${pageContext.request.contextPath}/EncherirArticle?id=${article.no_article}"><h5 class="card-title">${article.getNomArticle()}</h5></a>
										<p class="card-text">Prix: ${article.getDerniereEnchere().getMontantEnchere()==0?article.prixInit:article.getDerniereEnchere().getMontantEnchere() } points</p>
										<p class="card-text">Fin de l'enchère: ${article.finEnchere}</p> 
										<p class="card-text">
											<!--  à la place de pseudo dans l'url, il faudra mettre une variable pour le pseudo du vendeur !! -->
											<a href="${pageContext.request.contextPath}/AfficherUnProfil?profilVendeur=${ article.getVendeur().getPseudo()}" >${ article.getVendeur().getPseudo()}</a>
										</p>
									</div>
								</div>
							</div>
						</div>
				</c:forEach>
			</div>
		</div>
	</c:if>
	
	<c:if test="${empty pseudo}">

	
		<div class="container-fluid bg-light">
		<div class="row g-0 bg-light">
				<c:forEach var="article" items="${affichageDemande}">
					<div class="card m-1  bg-light" style="max-width: 450px;">
							<div class="card-body bg-light p-2">
								<div class="row g-0">
									<div class="col-4 col-lg-4 col-md-4 bg-light">
										<img src="" class="img-fluid rounded-start" alt="image article">
									</div>
									<div class="col-8 col-lg-8 col-md-8 bg-light"> <!-- ${article.no_article} --> 
										<h5 class="card-title">${article.getNomArticle() }</h5>
										<p class="card-text">Prix: ${article.getDerniereEnchere().getMontantEnchere()==0?article.prixInit:article.getDerniereEnchere().getMontantEnchere() } points</p>
										<p class="card-text">Fin de l'enchère: ${article.finEnchere}</p> 
										<p class="card-text">${ article.getVendeur().getPseudo()}</p>
											<!--  à la place de pseudo dans l'url, il faudra mettre une variable pour le pseudo du vendeur !! -->
									</div>
								</div>
							</div>
						</div>
						
				</c:forEach>
			</div>
		</div>
	</c:if>
</div>
	<%@include file="../html/footer.html"%>

</body>
</html>