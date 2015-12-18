<%@ page import="bookmyride.RideRequest" %>



<div class="fieldcontain ${hasErrors(bean: rideRequestInstance, field: 'requester', 'error')} required">
	<label for="requester">
		<g:message code="rideRequest.requester.label" default="Requester" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="requester" name="requester.id" from="${bookmyride.User.list()}" optionKey="id" required="" value="${rideRequestInstance?.requester?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rideRequestInstance, field: 'startLatitude', 'error')} required">
	<label for="startLatitude">
		<g:message code="rideRequest.startLatitude.label" default="Start Latitude" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="startLatitude" value="${fieldValue(bean: rideRequestInstance, field: 'startLatitude')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: rideRequestInstance, field: 'startLongitude', 'error')} required">
	<label for="startLongitude">
		<g:message code="rideRequest.startLongitude.label" default="Start Longitude" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="startLongitude" value="${fieldValue(bean: rideRequestInstance, field: 'startLongitude')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: rideRequestInstance, field: 'endLatitude', 'error')} required">
	<label for="endLatitude">
		<g:message code="rideRequest.endLatitude.label" default="End Latitude" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="endLatitude" value="${fieldValue(bean: rideRequestInstance, field: 'endLatitude')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: rideRequestInstance, field: 'surgeConfirmationId', 'error')} ">
	<label for="surgeConfirmationId">
		<g:message code="rideRequest.surgeConfirmationId.label" default="Surge Confirmation Id" />
		
	</label>
	<g:textField name="surgeConfirmationId" value="${rideRequestInstance?.surgeConfirmationId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rideRequestInstance, field: 'productId', 'error')} ">
	<label for="productId">
		<g:message code="rideRequest.productId.label" default="Product Id" />
		
	</label>
	<g:textField name="productId" value="${rideRequestInstance?.productId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rideRequestInstance, field: 'requestStatus', 'error')} required">
	<label for="requestStatus">
		<g:message code="rideRequest.requestStatus.label" default="Request Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="requestStatus" from="${bookmyride.RequestStatus?.values()}" keys="${bookmyride.RequestStatus.values()*.name()}" required="" value="${rideRequestInstance?.requestStatus?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: rideRequestInstance, field: 'endLongitude', 'error')} required">
	<label for="endLongitude">
		<g:message code="rideRequest.endLongitude.label" default="End Longitude" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="endLongitude" value="${fieldValue(bean: rideRequestInstance, field: 'endLongitude')}" required=""/>
</div>

