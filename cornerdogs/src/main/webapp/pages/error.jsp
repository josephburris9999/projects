<%-- 
	Error page.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en-US">
	<head>
		<jsp:include page="/fragments/tags.jsp"/>
		<title>Corner Dogs &#8212; Error</title>
	</head>
	<body>
		<jsp:include page="/fragments/header.jsp"/>
		<main>
			<section class="jumbotron page-hero w-100">
				<div class="hero-inner">
					<img class="hero-logo" src="<%=request.getContextPath()%>/images/cornerdogs-logo.png" alt="Corner Dogs logo">
					<h1>Something went wrong</h1>
				</div>
			</section>
			<section class="container mt-5">
				<div class="row pt-5 my-3">
					<div class="col-md-12">
						<h2 class="text-center text-danger">You have encountered an error!</h2>
					</div>
				</div>
				<div class="row mb-4">
					<div class="col-md-10 col-lg-8 mx-auto">
						<p>
							Please contact the administrator and inform them of this error and how you got here.
						</p>
						<p>
							You may try using the back button to go back and attempt your action again.
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 d-grid d-sm-flex justify-content-sm-end">
						<button class="btn btn-md btn-primary" type="button" value="previous" onclick="window.history.back();">Back</button>
					</div>
				</div>
			</section>
		</main>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/scripts.jsp"/>
	</body>
</html>
