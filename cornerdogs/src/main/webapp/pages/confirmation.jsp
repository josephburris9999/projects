<%-- 
	Submit confirmation page.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en-US">
	<head>
		<jsp:include page="/fragments/tags.jsp"/>
		<title>Corner Dogs &#8212; Order Confirmation</title>
	</head>
	<body>
		<jsp:include page="/fragments/header.jsp"/>
		<main>
			<section class="jumbotron page-hero w-100">
				<div class="hero-inner">
					<img class="hero-logo" src="<%=request.getContextPath()%>/images/cornerdogs-logo.png" alt="Corner Dogs logo">
					<h1>Order submitted!</h1>
				</div>
			</section>
			<section class="container mt-5">
				<div class="row pt-5 my-3">
					<div class="col-md-12">
						<h2 class="text-center">Your order has been submitted!</h2>
					</div>
				</div>
				<div class="row mb-4">
					<div class="col-md-10 col-lg-8 mx-auto text-center">
						Don&#39;t go anywhere! Your order will be ready for pick-up
						in just a moment!
						<p class="text-muted mt-3">
							Demo note: this submission was not persisted or sent to a kitchen system.
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 d-grid d-sm-flex justify-content-sm-end">
						<button class="btn btn-md btn-primary" type="button" name="submit" value="next" onclick="location.href='<%=request.getContextPath()%>/index.jsp'">Do it again!</button>
					</div>
				</div>
			</section>
		</main>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/scripts.jsp"/>
	</body>
</html>
