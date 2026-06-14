<%-- 
	Common link to external javascript files and inline javascript functions for the jsp's.
	@author Joseph Burris, all9s Solutions LLC
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
	document.addEventListener('shown.bs.modal', function(event) {
		if (event.target.id !== 'cancelModal') {
			return;
		}

		const continueButton = event.target.querySelector('#continue');
		if (continueButton) {
			continueButton.focus();
		}
	});
</script>
