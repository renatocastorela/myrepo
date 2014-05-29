<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="l" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<l:base>
	<jsp:attribute name="pageTitle">
		<spring:message code="label.homepage.title" />		
	</jsp:attribute>
	<jsp:body>
	
	<div id="events-result">
	</div>
	<div id="map-wrapper">
		<div id="map" class="map"><!-- Load MapBox Map--></div>
    </div>
	<div hidden="true" id="event-template">
		<div class='panel panel-danger display-none' nodeid='{{nodeId}}'>
			<div class='panel-heading'  >
					<h3 class='panel-title'> {{name}}</h3>
					</div>
				<div class='panel-body'>
	    		<dl>
	    			<dt>Fecha</dt>
	    			<dd>{{startDate}} - {{endDate}}<dd>
					<dt>Organiza</dt>
  					<dd> {{host}}<dd>
  					<dt>Recinto</dt>
  					<dd>{{location}}<dd>
  					<dt>Tipo</dt>
  					<dd>{{category.name}}</dd>
				</dl>		
				<a href='${pageContext.request.contextPath}/events/details/{{nodeId}}' target="_blank" class="btn btn-primary btn-xs">Detalles</a>
			</div>
		</div>"
	</div>
	<script type="text/javascript">
	var map;
    var markerLayer;
    var template = $("#event-template").html();
    
    function init_navbar() 
	{
    	$("#search-form").submit(function(event){
    		console.debug("submiting");
    		if (typeof window.history.pushState == 'function') {
    			console.debug("state");
    			history.pushState({}, null, ctx+"/events/explore?"
    					+"q="+encodeURIComponent(this.q.value)
    					+"&lt="+encodeURIComponent(this.lt.value)
    					+"&ln="+encodeURIComponent(this.ln.value));
    			
    		}
    		load_events();
    		event.preventDefault();
    	});
	}
    
    function update_params_from_uri(){
    	$("input[name=q]").val(getParameterByName("q"));
    	$("input[name=lt]").val(getParameterByName("lt"));
    	$("input[name=ln]").val(getParameterByName("ln"));
    }

	function load_map(){
		
		
		load_events();
	}
	
	function parse_wkt(text)
	{
		var result={};
		var match = text.match(/\([^\(\)]+\)/g);
	   	if (match !== null) {
	        for (var i = 0; i < match.length; i++) {
	            tmp = match[i].match(/-?\d+\.?\d*/g);
	            if (tmp !== null) {
	            	/*Nota: Solo soporta puntos*/
	                for (j = 0, tmpArr = []; j < tmp.length; j+=2) {
	                    lng = Number(tmp[j]);
	                    lat = Number(tmp[j + 1]);
	                    result  = { coords: {
	        	        	latitude:   lat,
	                        longitude: lng
	                        } 
	                    };
	                }
	            }
	        }
	    }
		return result; 
	}
	
	function load_events_on_map(data) {
		var features = [];
		var items = [];
		var bounds = [];
		
		$.each(data, function(key, val) {
			if (val.venue.adress == null) {
				console.warn("Adress is null");
				return true;
			}
			var p = parse_wkt(val.venue.adress.wkt);
			bounds.push([ p.coords.latitude, p.coords.longitude]);
			/*Cargando features*/
			features.push({
				type : 'Feature',
				geometry : {
					type : 'Point',
					coordinates : [ p.coords.longitude,
							p.coords.latitude ]
				},
				properties : {
					'marker-color' : '#000',
					'marker-symbol' : 'star-stroked',
					'title' : val.name,
					'id' : val.nodeId
	
				}
			});
			/*Cargando Items*/
			var output = Mustache.render(template, val);					
			items.push(output);
		});
		if(typeof map == 'undefined'){
			map = L.mapbox.map('map', 'renato-castorela.map-hgnx2ffm')
				.fitBounds( bounds , {
					paddingTopRight : [ 400, 0 ]
			
				});
		}else{
			map.fitBounds(bounds);
		}
		if(typeof markerLayer != 'undefined'){
				markerLayer.clearLayers();
			}
		markerLayer = L.geoJson({
			type : 'FeatureCollection',
			features : features },
			{onEachFeature: function (feature, layer) {
			    	if (feature.properties) {
			        	layer.bindPopup(feature.properties.title);
			   		}
				}
			}).addTo(map);
	/*Registrando eventos en la capa de marker layer*/
		markerLayer.on('click', function(e) {
			/*Centrando el mapa a la posicion del marker*/
			map.panTo(e.layer.getLatLng());
			var nodeId = e.layer.feature.properties.id;
			/*Scrolling to element*/
			$('html, body').animate(
					{
						scrollTop : $(
								"div[nodeid='" + nodeId
										+ "']")
								.offset().top - 60
					}, 200);
			$(
					"#events-result > div[nodeid!='"
							+ nodeId + "']")
					.removeClass("panel-primary");
			$("div[nodeid='" + nodeId + "']")
					.toggleClass("panel-primary");
		});
	
		$("#events-result > div").slideUp();
		$("#events-result").empty();
		$("#events-result").append(items.join(" "));
		$("#events-result > div").slideDown();
		$("#events-result > div").click(function(e) {
			$(".panel-primary").removeClass("panel-primary");
			$(this).addClass("panel-primary");
			var nodeId = $(this).attr("nodeid");
			$('html, body').animate({
				scrollTop : $(this).offset().top - 60
			}, 200);
			markerLayer.eachLayer(function(e) {
				if (e.feature.properties.id == nodeId) {
					console.debug(e.feature.properties.id);
					map.panTo(e.getLatLng());
					e.openPopup();
					return false;
				}
			});
		});
		$("#map").click(function(e) {
			$("#events-result > div").removeClass("panel-primary");
		});
}

function load_events() {
	$.getJSON(ctx+"/events/search/like/",
			{
				q : $("input[name=q]").val(),
				lt : $("input[name=lt]").val(),
				ln : $("input[name=ln]").val(),
			},
			load_events_on_map);
}
function init_page(){
	init_navbar();
	update_params_from_uri();
	load_events();
}
window.onpopstate = function (event) {
	  update_params_from_uri();
	  load_events();
	}
	init_page();
</script>
	
	</jsp:body>
</l:base>
