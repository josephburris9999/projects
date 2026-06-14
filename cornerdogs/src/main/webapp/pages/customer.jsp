<%-- 
	Customer page.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.all9ssolutions.cornerdogs.models.Customer,com.all9ssolutions.cornerdogs.models.IConstants" %>
<%@ taglib uri="/WEB-INF/tlds/custom-tags.tld" prefix="render" %>
<%
	Customer model = ((Customer) request.getSession().getAttribute(IConstants.CUSTOMER_SESSION_KEY));
%>

<!DOCTYPE html>
<html lang="en-US">
	<head>
		<jsp:include page="/fragments/tags.jsp"/>
		<title>Corner Dogs &#8212; Customer Page</title>
	</head>
	<body>
		<jsp:include page="/fragments/header.jsp"/>
		<jsp:include page="/fragments/breadcrumb.jsp"/>
		<main class="has-page-hero">
			<section class="jumbotron page-hero w-100">
				<div class="hero-inner">
					<img class="hero-logo" src="<%=request.getContextPath()%>/images/cornerdogs-logo.png" alt="Corner Dogs logo">
					<h1>Customer</h1>
				</div>
			</section>
			<form method="post" action="<%=request.getContextPath()%>/customer">
				<section class="container mt-5">
					<div class="row pt-5 my-3">
						<div class="col-md-12 text-center">
							<h2>Whose name do we call?</h2>
							<p class="text-muted">Items marked with an asterisk ( * ) are required.</p>
						</div>
					</div>
					<div class="row mb-4 justify-content-center">
						<div class="col-12 col-sm-9 col-md-6 form-group">
							<render:text type="text" id="name" name="name" label="Name*" value='<%=model.getName()%>' error='<%=model.getError("nameError")%>' />
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 d-grid gap-2 d-sm-flex justify-content-sm-between">
							<button class="btn btn-md btn-primary order-sm-2" type="submit" name="submit" value="next">Order Page</button>
							<button class="btn btn-md btn-primary order-sm-1" type="submit" name="submit" value="previous">Welcome!</button>
						</div>
					</div>
				</section>
			</form>
		</main>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/scripts.jsp"/>
		<script>
			document.addEventListener('DOMContentLoaded', function() {
				const nameInput = document.querySelector('input[name="name"]');
				if (nameInput) {
					nameInput.setAttribute('maxlength', '25');
					nameInput.focus();
				}
			});
		</script>
	</body>
</html>
