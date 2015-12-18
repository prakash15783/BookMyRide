
<%@ page import="bookmyride.RideRequest" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'rideRequest.label', default: 'RideRequest')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-rideRequest" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-rideRequest" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list rideRequest">
			
				<g:if test="${rideRequestInstance?.requester}">
				<li class="fieldcontain">
					<span id="requester-label" class="property-label"><g:message code="rideRequest.requester.label" default="Requester" /></span>
					
						<span class="property-value" aria-labelledby="requester-label"><g:link controller="user" action="show" id="${rideRequestInstance?.requester?.id}">${rideRequestInstance?.requester?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${rideRequestInstance?.startLatitude}">
				<li class="fieldcontain">
					<span id="startLatitude-label" class="property-label"><g:message code="rideRequest.startLatitude.label" default="Start Latitude" /></span>
					
						<span class="property-value" aria-labelledby="startLatitude-label"><g:fieldValue bean="${rideRequestInstance}" field="startLatitude"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rideRequestInstance?.startLongitude}">
				<li class="fieldcontain">
					<span id="startLongitude-label" class="property-label"><g:message code="rideRequest.startLongitude.label" default="Start Longitude" /></span>
					
						<span class="property-value" aria-labelledby="startLongitude-label"><g:fieldValue bean="${rideRequestInstance}" field="startLongitude"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rideRequestInstance?.endLatitude}">
				<li class="fieldcontain">
					<span id="endLatitude-label" class="property-label"><g:message code="rideRequest.endLatitude.label" default="End Latitude" /></span>
					
						<span class="property-value" aria-labelledby="endLatitude-label"><g:fieldValue bean="${rideRequestInstance}" field="endLatitude"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rideRequestInstance?.surgeConfirmationId}">
				<li class="fieldcontain">
					<span id="surgeConfirmationId-label" class="property-label"><g:message code="rideRequest.surgeConfirmationId.label" default="Surge Confirmation Id" /></span>
					
						<span class="property-value" aria-labelledby="surgeConfirmationId-label"><g:fieldValue bean="${rideRequestInstance}" field="surgeConfirmationId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rideRequestInstance?.productId}">
				<li class="fieldcontain">
					<span id="productId-label" class="property-label"><g:message code="rideRequest.productId.label" default="Product Id" /></span>
					
						<span class="property-value" aria-labelledby="productId-label"><g:fieldValue bean="${rideRequestInstance}" field="productId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rideRequestInstance?.requestStatus}">
				<li class="fieldcontain">
					<span id="requestStatus-label" class="property-label"><g:message code="rideRequest.requestStatus.label" default="Request Status" /></span>
					
						<span class="property-value" aria-labelledby="requestStatus-label"><g:fieldValue bean="${rideRequestInstance}" field="requestStatus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${rideRequestInstance?.endLongitude}">
				<li class="fieldcontain">
					<span id="endLongitude-label" class="property-label"><g:message code="rideRequest.endLongitude.label" default="End Longitude" /></span>
					
						<span class="property-value" aria-labelledby="endLongitude-label"><g:fieldValue bean="${rideRequestInstance}" field="endLongitude"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${rideRequestInstance?.id}" />
					<g:link class="edit" action="edit" id="${rideRequestInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
