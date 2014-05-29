<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="facebook" uri="http://www.springframework.org/spring-social/facebook/tags" %>
<%@ taglib prefix="social" uri="http://www.springframework.org/spring-social/social/tags" %>


<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
	
	<!-- Navbar header -->
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-content">
			<span class="sr-only"><spring:message code="label.button.toggle"/></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="${pageContext.request.contextPath}" id="oiga_logo"> 
			<img src="${pageContext.request.contextPath}/resources/img/oiga_logo.png" alt="Oiga logo" />
		</a>
	</div>
	<!-- Navbar collapse -->
	<div class="collapse navbar-collapse" id="navbar-content">
		<form class="navbar-form navbar-left" role="search" action="${pageContext.request.contextPath}/events/explore" id="search-form">
			<div class="form-group">
				<input type="text" class="form-control"
					placeholder="Eventos en mi ciudad" id="search-input" name="q" >
			</div>
			<input type="hidden" name="ln" />
			<input type="hidden" name="lt" />
			<button type="submit" class="btn btn-pistache btn-lg "
				id="search-btn">Buscar</button>
		</form>
		<ul class="nav navbar-nav navbar-right">
			<!-- Anonymous -->
				<sec:authorize access="isAnonymous()">
					<li>
						<button type="button" class="btn btn-primary navbar-btn btn-lg"
							data-toggle="modal" data-target="#signup-modal">
							<spring:message code="label.signup" />
						</button>
					</li>
					<li>
						<button type="button" class="btn-pink navbar-btn btn-lg"
							data-toggle="modal" data-target="#signin-modal">
							<spring:message code="label.login" />
						</button>
					</li>
				</sec:authorize>
				<!-- Authenticated -->
			<sec:authorize access="isAuthenticated()">
				<li>
					<a href="#" >
						<sec:authentication property="principal.username"/>
						<img alt="User profile" src="<sec:authentication property="principal.imageUrl"/>" class="img-rounded" style="height: 35px;s" >
					</a>
					
				</li>
				<li>
					<a href="<c:url value="/signout" />">Cerrar sesion</a>
				</li>
			</sec:authorize>
		</ul>
	</div>
	</div>
</nav>
<!-- Signin modal -->
<div class="modal fade" id="signin-modal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Iniciar sesion</h4>
			</div>
			<div class="modal-body">
				<p>Inicia sesión con tu red social<p>
				<form action="${pageContext.request.contextPath}/signin/facebook" method="POST">
  					 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  					 <input type="hidden" name="scope" value="email" />
  					<button type="submit" class="btn btn-facebook btn-connect">
  						Facebook
  					</button>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>
<!-- Signup modal -->
<div class="modal fade" id="signup-modal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Registrarse</h4>
			</div>
			<div class="modal-body">
				<p class="text-muted">Registrarse usando tu red social<p>
				<form action="${pageContext.request.contextPath}/signin/facebook" method="POST">
  					 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  					 <input type="hidden" name="scope" value="email" />
  					<button type="submit" class="btn btn-facebook btn-connect">
  						Facebook
  					</button>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	/*Busqueda Global*/
 	console.debug("config autocomplete");
	$("input[name=lt]").val(  $.cookie("location").latitude );
	$("input[name=ln]").val(  $.cookie("location").longitude );
$("#search-input").autocomplete({
	source : function(request, response) {
		$.getJSON(ctx+"/events/search/like",
				{
					q : request.term,
					lt : $.cookie("location").latitude,
					ln : $.cookie("location").longitude
				},
				function(data) {
					response($.map(data, function(item) {
					return {
						label : item.name,
						value : item.name
					}
				}))
			})
		}
});
</script>
