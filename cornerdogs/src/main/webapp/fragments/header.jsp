<%-- 
	Common page header for the jsp's.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.all9ssolutions.cornerdogs.models.Customer,com.all9ssolutions.cornerdogs.models.IConstants,com.all9ssolutions.cornerdogs.models.Order" %>
<%
	Customer customer = (Customer) request.getSession().getAttribute(IConstants.CUSTOMER_SESSION_KEY);
	Order order = null != customer ? customer.getOrder() : null;
	boolean orderInProgress = null != order && 0 < order.getHotDogCount();
%>
<header>
	<nav class="navbar fixed-top">
		<div class="container">
			<%
				if (orderInProgress) {
			%>
				<a class="navbar-brand link-primary" href="#" data-bs-toggle="modal" data-bs-target="#cancelModal">
					<img src="<%=request.getContextPath()%>/images/cornerdogs-logo.png" alt="Corner Dogs logo"><span>Corner Dogs</span>
				</a>
			<%
				} else {
			%>
				<a class="navbar-brand link-primary" href="<%=request.getContextPath()%>">
					<img src="<%=request.getContextPath()%>/images/cornerdogs-logo.png" alt="Corner Dogs logo"><span>Corner Dogs</span>
				</a>
			<%
				}
			%>
			<%
				if (orderInProgress) {
			%>
				<div class="ms-auto">
					<a class="link-primary" href="#" data-bs-toggle="modal" data-bs-target="#cancelModal">
						Cancel Order
					</a>
				</div>
			<%
				}
			%>
		</div>
	</nav>
	<%
		if (orderInProgress) {
	%>
	<div id="cancelModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Cancel Order</h4>
				</div>
				<div class="modal-body">
					<p>
						Are you sure you want to abandon your order?
					</p>
				</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button" id="continue" value="previous" data-bs-dismiss="modal">Continue</button>
					<button class="btn btn-primary" type="submit" id="cancel" value="next" data-bs-dismiss="modal" onclick="location.href='<%=request.getContextPath()%>/cancel';">Cancel Order</button>
				</div>
			</div>
		</div>
	</div>
	<%
		}
	%>
</header>
