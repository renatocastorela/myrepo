<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib
	uri="http://www.springframework.org/spring-social/facebook/tags"
	prefix="facebook"%>
<%@ page session="false"%>
        <div class="col-xs-12 col-sm-9">
          <p class="pull-right visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          <!-- 
           -->
          <div class="jumbotron">
            <h1>Proximos eventos!!</h1>
            <p></p>
          </div>
           <div class="row">
           <c:if test="${ not empty events }">
           
           <c:forEach var="ev" items="${events}"> 
           		<div class="col-6 col-sm-6 col-lg-4" >
           		<div class="panel panel-danger">
           		
           		<div class="panel-heading">${ev.name}</div>
  					<div class="panel-body">
  						<dl>
  							<dt>Organiza</dt>
  							<dd> ${ev.host}<dd>
  							<dt>Recinto</dt>
  							<dd>${ev.location}<dd>
  							<dt>Fecha</dt>
  							<dd>
  							Del <fmt:formatDate value="${ev.startDate}"
								pattern="yyyy-MM-dd " /> al  
							<fmt:formatDate value="${ev.endDate}"
								pattern="yyyy-MM-dd " />
							<dd>
							<dt>Descripcion</dt>
  							<dd>${ev.description}</dd>
  							<dt>Tipo</dt>
  			 				<dd>${ev.category.name}</dd>
  						</dl>
  						<a class="btn btn-default" href="${pageContext.request.contextPath}/details/${ev.nodeId}" role="button">Ver detalles &raquo;</a>
  					</div>
           		</div>
              
            </div><!--/span-->
           </c:forEach>
           
           </c:if>
           </div>
        </div>
        <div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
          <span class="label label-default">Categorias</span>
          <div class="list-group">
          <a href="${pageContext.request.contextPath}/" class="list-group-item <c:if test="${ empty active }"> active </c:if> ">Todos</a>
           
          <c:forEach var="c" items="${categories }">
          	<a href="${pageContext.request.contextPath}/filter/${c.name}" class="list-group-item <c:if test="${ active == c.name }"> active </c:if>" >${ c.name} <span class="badge">${c.count}</span></a>
          </c:forEach>
          
          </div>
        </div>
      
      