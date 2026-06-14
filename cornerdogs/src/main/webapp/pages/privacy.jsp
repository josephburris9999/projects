<%--
	Privacy Policy page.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en-US">
	<head>
		<jsp:include page="/fragments/tags.jsp"/>
		<title>Corner Dogs &#8212; Privacy Policy</title>
	</head>
	<body>
		<jsp:include page="/fragments/header.jsp"/>
		<main>
			<section class="jumbotron page-hero w-100">
				<div class="hero-inner">
					<img class="hero-logo" src="<%=request.getContextPath()%>/images/cornerdogs-logo.png" alt="Corner Dogs logo">
					<h1>Privacy Policy</h1>
				</div>
			</section>
			<section class="container mt-5 legal-page">
				<div class="row pt-5 my-3">
					<div class="col-md-12">
						<h2 class="text-center">Privacy Policy</h2>
						<p class="text-muted text-center">Effective June 13, 2026</p>
					</div>
				</div>
				<div class="row mb-4">
					<div class="col-md-10 col-lg-8 mx-auto">
						<h3>Overview</h3>
						<p>
							This Privacy Policy explains how all9s Solutions LLC handles information related to Corner Dogs.
							Corner Dogs is a portfolio demonstration application and is not a real restaurant ordering system.
						</p>
						<h3>Information you provide</h3>
						<p>
							The order flow asks for a name, order location, and hot dog quantities so the demo can show a multi-step
							checkout experience. This information is held in the server session while you move through the demo and
							is used only to render the demo pages, calculate the sample bill, and display the confirmation flow.
						</p>
						<h3>Information collected automatically</h3>
						<p>
							Like most websites, hosting providers, browsers, and security tools may process basic technical
							information such as IP address, browser type, device type, pages requested, referring pages, and request
							timestamps. The application may also create routine technical logs, such as server events or errors, to
							support troubleshooting.
						</p>
						<h3>Analytics</h3>
						<p>
							The site uses Google Analytics to understand general site usage, such as page views, browser information,
							approximate location, and referring pages. Google Analytics may use cookies or similar technologies to
							provide those measurements.
						</p>
						<h3>Downloads and external services</h3>
						<p>
							When you open external links, your browser may connect to hosting services, CDN providers, social
							platforms, or other third-party websites. Those services may process information under their own privacy
							policies.
						</p>
						<h3>Cookies and sessions</h3>
						<p>
							The application may use a standard session cookie so the wizard can remember your progress between pages.
							Google Analytics may also use cookies or similar technologies for site measurement. The application
							session is cleared when an order is canceled or the demo flow is completed.
						</p>
						<h3>Third-party services</h3>
						<p>
							The site uses Google Analytics and loads Bootstrap and Bootstrap Icons from jsDelivr CDN. Your browser
							may request those assets and services directly from those providers.
						</p>
						<h3>How information is used</h3>
						<p>
							Information is used to operate the demo, render requested pages, calculate sample order totals, respond
							to messages, protect the website, maintain business records, and improve the visitor experience.
						</p>
						<h3>How information is shared</h3>
						<p>
							We do not sell personal information. We may share information with service providers who help operate the
							website, when required by law, or when necessary to protect our rights, users, or services.
						</p>
						<h3>Data security</h3>
						<p>
							We use reasonable safeguards appropriate for a portfolio demonstration site. No method of transmission or
							storage is completely secure, so we cannot guarantee absolute security.
						</p>
						<h3>Data retention</h3>
						<p>
							The demo does not intentionally save your submitted name or order details to a database. We keep other
							information only as long as reasonably needed for the purposes described in this policy, unless a longer
							retention period is required or permitted by law.
						</p>
						<h3>Your choices</h3>
						<p>
							You may contact us to ask questions about information you have provided, request an update, or ask that
							we delete information when we are not required to retain it.
						</p>
						<h3>Changes</h3>
						<p>
							We may update this Privacy Policy from time to time. The updated version will be posted on this page with
							a revised date.
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
