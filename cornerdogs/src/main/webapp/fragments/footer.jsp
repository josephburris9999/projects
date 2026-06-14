<%-- 
	Common page footer for the jsp's.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<footer class="footer w-100 p-3 text-center">
	<div class="footer-content">
		<small class="fs-7">
			Copyright &copy; 2026 <a class="link-primary" href="http://localhost:9091/" target="_blank">all9s Solutions LLC</a>, all rights reserved.
		</small>
		<nav class="footer-links" aria-label="Legal links">
			<a class="link-primary" href="<%=request.getContextPath()%>/pages/terms.jsp">Terms</a>
			<a class="link-primary" href="<%=request.getContextPath()%>/pages/privacy.jsp">Privacy Policy</a>
		</nav>
	</div>
</footer>
