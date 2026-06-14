<%--
	Terms of Service page.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en-US">
	<head>
		<jsp:include page="/fragments/tags.jsp"/>
		<title>Corner Dogs &#8212; Terms of Service</title>
	</head>
	<body>
		<jsp:include page="/fragments/header.jsp"/>
		<main>
			<section class="jumbotron page-hero w-100">
				<div class="hero-inner">
					<img class="hero-logo" src="<%=request.getContextPath()%>/images/cornerdogs-logo.png" alt="Corner Dogs logo">
					<h1>Terms of Service</h1>
				</div>
			</section>
			<section class="container mt-5 legal-page">
				<div class="row pt-5 my-3">
					<div class="col-md-12">
						<h2 class="text-center">Terms of Service</h2>
						<p class="text-muted text-center">Effective June 13, 2026</p>
					</div>
				</div>
				<div class="row mb-4">
					<div class="col-md-10 col-lg-8 mx-auto">
						<h3>Acceptance</h3>
						<p>
							By accessing Corner Dogs, you agree to these Terms of Service. If you do not agree with these terms,
							do not use the demo.
						</p>
						<h3>About the demo</h3>
						<p>
							Corner Dogs is a portfolio demonstration application operated by Joseph Burris, all9s Solutions LLC.
							It is not a real restaurant, commerce platform, payment system, or ordering service. Any order submitted
							through this site is simulated and will not be prepared, delivered, charged, stored as a real transaction,
							or fulfilled.
						</p>
						<h3>Permitted use</h3>
						<p>
							You may view and interact with the demo for evaluation, learning, and portfolio review purposes.
							Use of the demo must remain lawful, non-disruptive, and consistent with its demonstration purpose.
						</p>
						<h3>Responsible use</h3>
						<p>
							You agree not to misuse the site, interfere with its operation, attempt unauthorized access, bypass
							security controls, submit malicious content, or use the demo in a way that could impair the service or
							other systems.
						</p>
						<h3>Ownership</h3>
						<p>
							Unless a separate license states otherwise, the application code, design, branding, and content remain
							the property of all9s Solutions LLC or their respective owners. No rights are granted beyond viewing and
							evaluating the demo.
						</p>
						<h3>No warranty</h3>
						<p>
							The site is provided as is and as available for portfolio review and technical demonstration. all9s
							Solutions LLC does not guarantee that the demo will be uninterrupted, error-free, secure, or suitable for
							production use.
						</p>
						<h3>Limitation of liability</h3>
						<p>
							To the fullest extent permitted by law, all9s Solutions LLC will not be liable for indirect, incidental,
							special, consequential, or punitive damages arising from your use of the site or demo.
						</p>
						<h3>External links</h3>
						<p>
							This site may link to third-party sites or services. Those sites are controlled by their own owners, and
							their terms and policies apply when you visit them.
						</p>
						<h3>Changes</h3>
						<p>
							These terms may be updated as the demo evolves. The updated version will be posted on this page with a
							revised date, and continued use of the demo after changes are posted means you accept the updated terms.
						</p>
						<h3>Contact</h3>
						<p>
							Questions regarding Corner Dogs may be directed to all9s Solutions LLC through
							hello@all9ssolutions.com. Source code, issue reporting, and project releases are available
							through the project's GitHub repositories.
						</p>
					</div>
				</div>
			</section>
		</main>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/scripts.jsp"/>
	</body>
</html>
