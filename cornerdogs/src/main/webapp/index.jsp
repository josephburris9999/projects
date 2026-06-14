<%-- 
	Entry point (welcome-page) for the site.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en-US">
	<head>
		<jsp:include page="/fragments/tags.jsp"/>
		<title>Corner Dogs &#8212; Welcome</title>
	</head>
	<body>
		<jsp:include page="/fragments/header.jsp"/>
		<main>
			<section class="jumbotron w-100">
				<div class="hero-inner">
					<img class="hero-logo" src="<%=request.getContextPath()%>/images/cornerdogs-logo.png" alt="Corner Dogs logo">
					<h1 class="py-5 text-center">Welcome to<br>Corner Dogs!</h1>
				</div>
			</section>
			<section class="container">
				<div class="row pt-5 my-3">
					<div class="col-md-12">
						<h2 class="text-center">May we take your order?</h2>
					</div>
				</div>
				<div class="row mb-4">
					<div class="col-md-10 col-lg-8">
						<p>
							Corner Dogs is a hot dog bar offering quick service and tasty foot-longs!
							We have just 2 options guaranteed to satisfy your hunger:
						</p>
						<ul>
							<li>The Dog &#8212; A frank and bun with mustard.</li>
							<li>The Dog with Everything &#8212; A frank and bun with chili, onions, and mustard.</li>
						</ul>
						<p>
							At 99&cent; for either, we recommend going with Everything!
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 d-grid d-sm-flex justify-content-sm-end">
						<a class="btn btn-md btn-primary" href="<%=request.getContextPath()%>/customer">Put me in line!</a>
					</div>
				</div>
			</section>
		</main>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/scripts.jsp"/>
	</body>
</html>
