<%-- 
	Common breadcrumb header for the jsp's.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.all9ssolutions.cornerdogs.models.Breadcrumb,com.all9ssolutions.cornerdogs.models.IConstants, java.util.Iterator" %>

<div class="breadcrumb w-100">
	<%
		Breadcrumb breadcrumb = (Breadcrumb) request.getSession().getAttribute(IConstants.BREADCRUMB_SESSION_KEY);
		if (null != breadcrumb) {
			for (Iterator<String> iter = breadcrumb.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
	%>
				<a class="mx-2" href="<%=request.getContextPath()%><%=key%>"><%=breadcrumb.get(key)%></a>
	<%
				if (iter.hasNext()) {
	%>
				/
	<%
				}
			}
		}
	%>
</div>
