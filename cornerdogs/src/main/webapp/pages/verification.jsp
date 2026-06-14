<%-- 
	Verification page.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.all9ssolutions.cornerdogs.models.Customer,com.all9ssolutions.cornerdogs.models.IConstants,com.all9ssolutions.cornerdogs.models.Order,com.all9ssolutions.cornerdogs.models.Verification,com.all9ssolutions.wizard.tags.AbstractTag" %>
<%@ taglib uri="/WEB-INF/tlds/custom-tags.tld" prefix="render" %>
<%
	Customer customer = ((Customer) request.getSession().getAttribute(IConstants.CUSTOMER_SESSION_KEY));
	Order order = customer.getOrder();
	Verification model = customer.getVerification();
%>

<!DOCTYPE html>
<html lang="en-US">
	<head>
		<jsp:include page="/fragments/tags.jsp"/>
		<title>Corner Dogs &#8212; Verification Page</title>
	</head>
	<body>
		<jsp:include page="/fragments/header.jsp"/>
		<jsp:include page="/fragments/breadcrumb.jsp"/>
		<main class="has-page-hero">
			<section class="jumbotron page-hero w-100">
				<div class="hero-inner">
					<img class="hero-logo" src="<%=request.getContextPath()%>/images/cornerdogs-logo.png" alt="Corner Dogs logo">
					<h1>Verification</h1>
				</div>
			</section>
			<form id="verificationForm" method="post" action="<%=request.getContextPath()%>/verification">
				<section class="container mt-5">
					<div class="row pt-5 my-3">
						<div class="col-md-12 text-center">
							<h2>Please verify your bill.</h2>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-12 col-md-6">
							Customer Name: <%=AbstractTag.escapeHtml(customer.getName())%>
						</div>
						<div class="col-12 col-md-6 text-md-end">
							Order Time: <%=order.getFormattedWhen()%>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-12">
							<div class="table table-responsive">
								<table class="w-100">
									<thead>
										<tr>
											<th class="w-50 border-end-0">Item</th>
											<th class="border-start-0" style="width:20%;"></th>
											<th style="width:10%;">Count</th>
											<th class="text-end" style="width:20%;">Price</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td colspan="2">Hot Dog(s)</td>
											<td><%=order.getHotDogs()%></td>
											<td class="text-end"><%=order.getHotDogsPrice()%></td>
										</tr>
										<tr>
											<td colspan="2">Hot Dog(s) with Everything</td>
											<td><%=order.getWithEverything()%></td>
											<td class="text-end"><%=order.getWithEverythingPrice()%></td>
										</tr>
										<tr>
											<td class="text-end" colspan="2">Total Hot Dog(s)</td>
											<td colspan="2"><%=order.getHotDogCount()%></td>
										</tr>
										<tr class="text-end">
											<td colspan="2">Sub-Total</td>
											<td colspan="2"><%=order.getSubTotal()%></td>
										</tr>
										<tr class="text-end">
											<td colspan="2">Tax</td>
											<td colspan="2"><%=order.getTax()%></td>
										</tr>
										<tr class="text-end">
											<td colspan="2">Total</td>
											<td colspan="2"><%=order.getTotal()%></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 d-grid gap-2 d-sm-flex justify-content-sm-between">
							<button class="btn btn-md btn-primary order-sm-2" type="button" data-bs-toggle="modal" data-bs-target="#submitModal">Confirmation</button>
							<button class="btn btn-md btn-primary order-sm-1" type="submit" name="submit" value="previous">Order Page</button>
						</div>
					</div>
				</section>
			</form>
		</main>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/scripts.jsp"/>
		<div id="submitModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Confirmation</h4>
					</div>
					<div class="modal-body">
						<p>
							You may still review your order and order more hot dogs.
						</p>
						<p>
							Are you sure you want to place your order?
						</p>
					</div>
					<div class="modal-footer">
						<button class="btn btn-secondary" type="button" name="submit" value="previous" data-bs-dismiss="modal">Cancel</button>
						<button class="btn btn-primary" type="submit" form="verificationForm" name="submit" value="next">Fill My Order</button>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
