<%@tag description="Base Pages template" pageEncoding="UTF-8"%>
<%@attribute name="pageTitle" 	fragment="true" %>
<%@attribute name="header" 	fragment="true" %>
<%@attribute name="footer" 	fragment="true" %>
<!DOCTYPE html>
<html>
  <head>
  	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/cokie_60.png" />
    <title>
    	<jsp:invoke fragment="pageTitle" />
    </title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
  	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
  	<!-- Mapbox -->
  	<link href='//api.tiles.mapbox.com/mapbox.js/v1.6.0/mapbox.css' rel='stylesheet' />
  	<!-- JQuery UI --> 
  	<link href='http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css' rel='stylesheet' />
	 
	<!--[if lte IE 8]>
    <link href='//api.tiles.mapbox.com/mapbox.js/v1.3.1/mapbox.ie.css' rel='stylesheet'>
  	<![endif]--> 
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
        <!-- Oiga 	-->
  	<link href="${pageContext.request.contextPath}/resources/css/oiga.css" rel="stylesheet" media="screen">
  	<!-- Mapbox -->
  	<link href="${pageContext.request.contextPath}/resources/css/map.css" rel="stylesheet" media="screen">
    <script src='//api.tiles.mapbox.com/mapbox.js/v1.6.0/mapbox.js'></script>
    <!-- jQuery -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<!-- jQuery plugins -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.cookie.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/geoPosition.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/geoPositionSimulator.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/mustache.js"></script>
  </head>
  <body>
  
  <script type="text/javascript">
		var ctx = "${pageContext.request.contextPath}";
		
		$.cookie.json=true;
		if(typeof $.cookie("location") == 'undefined'){
			$.ajax({
				dataType: "json",
				url: "https://freegeoip.net/json/",
				async: false
				})
				.done(function(data) {
					$.cookie("location", data, {expires:1});
					console.debug("Location : "+$.cookie("location").region_name);
				});						
		}else{
			console.debug("Location : "+$.cookie("location").region_name);
		}
		function getParameterByName(name) {
		    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
		    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		        results = regex.exec(location.search);
		    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
		}
	</script>
    <jsp:include page="/WEB-INF/layouts/navbar.jsp" />
    <jsp:doBody />
  </body>
</html>