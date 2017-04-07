<%
	ui.includeJavascript("kenyaemr", "controllers/header.js")

	def closeChartUrl = config.closeChartUrl ?: appHomepageUrl
%>
<script type="text/javascript">
	function ke_closeChart() {
		kenyaui.openConfirmDialog({ heading: 'Patient chart', message: 'Close this patient chart?', okCallback: function() {
			ui.navigate('${ ui.escapeJs(closeChartUrl) }');
		}});
	}
</script>

<div class="ke-patientheader" ng-controller="PatientHeader" ng-init="init(${ currentApp ? ("'" + currentApp.id + "'") : "null" }, ${ patient.id })">
	<div style="float: left; width: 35%;">
		<div style="float: left; padding-right: 5px">
			<img width="32" height="32" ng-src="{{ iconUrl }}" />
		</div>
		<span class="ke-patient-name">${patientName}</span><br/>
		<span class="ke-patient-gender">{{ patient.gender | keGender }}</span>,
		<span class="ke-patient-age">{{ patient.age }} <small>(${ kenyaui.formatPersonBirthdate(patient) })</small></span>
	</div>
	
	<div style="float: left; width: 30%; text-align: center">
		<div ng-repeat="identifier in patient.identifiers">
				<span class="ke-identifier-type">{{ identifier.identifierType }} :</span>
				<span class="ke-identifier-value">{{ identifier.identifier }}</span>
		</div>
		<div>
			<% if( ingoName!='') { %>
			
			<span class="ke-ingo-type">NAME OF INGO : </span>
			<span class="ke-ingo-name">${ ingoName}</span>
			<% } %>
		</div>
	</div>
	
	<div style="float: right">
		<button class="ke-compact" title="Close this patient chart" onclick="ke_closeChart()"><img src="${ ui.resourceLink("kenyaui", "images/glyphs/close.png") }"/> Close Dashboard</button>
	</div>

	<div style="clear: both; height: 5px;"></div>

	<div style="width: 50%; float: left; overflow: auto; text-align: left">
		<span ng-if="patient.dead" class="ke-deadtag">Deceased since <strong>{{ patient.deathDate | keDateAuto }}</strong></span>
		<span ng-if="patient.voided" class="ke-voidedtag">Voided since <strong>{{ patient.dateVoided | keDateAuto }}</strong></span>
		<span ng-repeat="flag in flags" class="ke-flagtag" style="margin-right: 5px">{{ flag.message }}</span>
	</div>

	<div style="width: 50%; float: right; overflow: auto; text-align: right">
		<span class="ke-tip">Current visit</span>
		<span ng-if="patient.activeVisit" class="ke-visittag"> since <strong>{{ patient.activeVisit.startDatetime | keDateAuto }}</strong></span>
		<span ng-if="!patient.activeVisit" style="font-style: italic">${ ui.message("general.none") }</span>
	</div>
</div>