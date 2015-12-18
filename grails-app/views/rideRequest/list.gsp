
<%@ page import="bookmyride.RideRequest" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'rideRequest.label', default: 'RideRequest')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-rideRequest" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-rideRequest" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="rideRequest.requester.label" default="Requester" /></th>
					
						<g:sortableColumn property="startLatitude" title="${message(code: 'rideRequest.startLatitude.label', default: 'Start Latitude')}" />
					
						<g:sortableColumn property="startLongitude" title="${message(code: 'rideRequest.startLongitude.label', default: 'Start Longitude')}" />
					
						<g:sortableColumn property="endLatitude" title="${message(code: 'rideRequest.endLatitude.label', default: 'End Latitude')}" />
					
						<g:sortableColumn property="surgeConfirmationId" title="${message(code: 'rideRequest.surgeConfirmationId.label', default: 'Surge Confirmation Id')}" />
					
						<g:sortableColumn property="productId" title="${message(code: 'rideRequest.productId.label', default: 'Product Id')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${rideRequestInstanceList}" status="i" var="rideRequestInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${rideRequestInstance.id}">${fieldValue(bean: rideRequestInstance, field: "requester")}</g:link></td>
					
						<td>${fieldValue(bean: rideRequestInstance, field: "startLatitude")}</td>
					
						<td>${fieldValue(bean: rideRequestInstance, field: "startLongitude")}</td>
					
						<td>${fieldValue(bean: rideRequestInstance, field: "endLatitude")}</td>
					
						<td>${fieldValue(bean: rideRequestInstance, field: "surgeConfirmationId")}</td>
					
						<td>${fieldValue(bean: rideRequestInstance, field: "productId")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${rideRequestInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
