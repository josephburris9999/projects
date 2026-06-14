<%-- 
	Order page.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.all9ssolutions.cornerdogs.models.Customer,com.all9ssolutions.cornerdogs.models.IConstants,com.all9ssolutions.cornerdogs.models.Order"%>
<%@ taglib uri="/WEB-INF/tlds/custom-tags.tld" prefix="render" %>
<%
	Customer customer = ((Customer)request.getSession().getAttribute(IConstants.CUSTOMER_SESSION_KEY));
	Order model = customer.getOrder();
%>

<!DOCTYPE html>
<html lang="en-US">
	<head>
		<jsp:include page="/fragments/tags.jsp"/>
		<title>Corner Dogs &#8212; Order Page</title>
	</head>
	<body>
		<jsp:include page="/fragments/header.jsp"/>
		<jsp:include page="/fragments/breadcrumb.jsp"/>
		<main class="has-page-hero">
			<section class="jumbotron page-hero w-100">
				<div class="hero-inner">
					<img class="hero-logo" src="<%=request.getContextPath()%>/images/cornerdogs-logo.png" alt="Corner Dogs logo">
					<h1>Order</h1>
				</div>
			</section>
			<form method="post" action="<%=request.getContextPath()%>/order">
				<section class="container mt-5">
					<div class="row pt-5 my-3">
						<div class="col-md-12 text-center">
							<h2>What can we get for you?</h2>
							<p class="text-muted">Items marked with an asterisk ( * ) are required.</p>
						</div>
					</div>
					<div class="row mb-4 justify-content-center">
						<div class="col-12 col-sm-9 col-md-6">
							<render:select id="where" name="where" label="Where will you take your order?*" value='<%=model.getWhere().getKey()%>' qualifiedPath="com.all9ssolutions.cornerdogs.enums.Where" error='<%=model.getError("whereError")%>'/>
						</div>
					</div>
					<div class="row mb-3 justify-content-center">
						<div class="col-12 col-sm-9 col-md-6">
							<p>
								How many hot dogs would you like?*
								<span class="error"><%=model.getError("orderError")%></span>
							</p>
						</div>
					</div>
					<div class="row mb-4 justify-content-center">
						<div class="col-12 col-sm-9 col-md-6">
							<div class="row">
								<div class="col-12 col-lg-6 form-group quantity-field mb-3 mb-lg-0">
									<render:text type="number" id="hotDogs" name="hotDogs" label="Hot Dogs" value='<%=model.getHotDogs()%>' error='<%=model.getError("hotDogsError")%>' />
								</div>
								<div class="col-12 col-lg-6 form-group quantity-field">
									<render:text type="number" id="withEverything" name="withEverything" label="Hot Dogs with Everything" value='<%=model.getWithEverything()%>' error='<%=model.getError("withEverythingError")%>' />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 d-grid gap-2 d-sm-flex justify-content-sm-between">
							<button class="btn btn-md btn-primary order-sm-2" type="submit" name="submit" value="next">Verification</button>
							<button class="btn btn-md btn-primary order-sm-1" type="submit" name="submit" value="previous">Customer Page</button>
						</div>
					</div>
				</section>
			</form>
		</main>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/scripts.jsp"/>
		<script>
			document.addEventListener('DOMContentLoaded', function() {
				const hotDogsInput = document.querySelector('input[name="hotDogs"]');
				const withEverythingInput = document.querySelector('input[name="withEverything"]');
				const whereSelect = document.querySelector('select[name="where"]');

				[hotDogsInput, withEverythingInput].forEach(function(input) {
					if (input) {
						input.setAttribute('min', '0');
						input.setAttribute('max', '99');
					}
				});

				if (whereSelect) {
					whereSelect.focus();
				}
			});
		</script>
	</body>
</html>
