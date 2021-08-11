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

<title>Réinitialisation du mot de passe</title>

<link href="css/styles.css" rel="stylesheet">
</head>
<body>

	<%@include file="../html/header.html"%>

	<div class="container-fluid ">

		<br/><br/><br/><br/><br/><br/>
	
		<p>Vous avez demandé un lien pour réinitialiser votre mot de passe oublié</p>
		<p>Réinitialisation du Mot de passe : </p>
		
		<form action="${pageContext.request.contextPath}/MotDePasseOublie" method="post">
			<table>
				<tr>
					<td><label  for="pseudo"> Votre pseudo :</label> </td>
					<td><input  type="text" name="pseudo" id="pseudo" required="required" /></td>
				</tr>
				<tr >
					<td><label  for="email"> Votre adresse email :</label> </td>
					<td><input type="email" name="email" id="email" required="required" /></td>
				</tr>
		
		
				<tr >
					<td><label for="password"> Nouveau mot de passe :</label> </td>
					<td><input  type="password" name="password" id="password" required="required" /></td>
				</tr>
				<tr c>
					<td><label for="confirmation"> Confirmation :</label> </td>
					<td><input type="password" name="confirmation" id="confirmation" required="required" /></td>
				</tr>
			</table>	
				
			<div class="row g-0">
			<br/><br/><br/>
				<div class="col-lg-6 col-md-6 col-sm-12 col-12  bg-light center">
					<input type="submit" value="Réinitialiser" class=" btn mb-1 shadow p-1">
				</div>
				<div class="col-lg-6 col-md-6  bg-light center">
				</div>
			</div>
			<br/>
		</form>
	</div>

	<%@include file="../html/footer.html"%>

</body>
</html>