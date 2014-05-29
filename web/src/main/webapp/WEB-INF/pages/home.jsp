<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>

<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.homepage.title" />		
	</jsp:attribute>
	<jsp:body>
       <jsp:include page="/WEB-INF/layouts/recommendations.jsp"/> 
	</jsp:body>
</l:base>