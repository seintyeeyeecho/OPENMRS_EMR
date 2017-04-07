<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Search for a Patient" ])

	ui.includeJavascript("kenyaemr", "controllers/patient.js")

	def defaultWhich = config.defaultWhich ?: "all"

	def id = config.id ?: ui.generateId();
%>
<form id="${ id }" ng-controller="PatientSearchForm3" ng-init="init('${ defaultWhich }')">
	<label  class="ke-field-label">Dispensing</label>

	<label class="ke-field-label">Patient ID or Name (3 chars min)</label>
	<span class="ke-field-content">
		<input type="text" name="query" ng-model="query" ng-change="updateSearch()" style="width: 260px" />
	</span>
	
	<label class="ke-field-label">Dispensing Date</label>
	<span class="ke-field-content">
	${ ui.includeFragment("kenyaui", "field/java.util.Date" ,[id:'dispensedDate',  formFieldName:'date', showTime: false])}
	
	</span>
	<input type="button" value="Search" ng-click="updateSearch();"/>
</form>