<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>
<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.signuppage.title" />		
	</jsp:attribute>
	<jsp:body>
		<form:form action="register" commandName="user" method="POST" enctype="utf8" role="form">
			 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<button type="submit" class="btn-facebook btn-connect"><spring:message code="label.submit.signup.facebook"/></button>
		</form:form>
	</jsp:body>
</l:base>