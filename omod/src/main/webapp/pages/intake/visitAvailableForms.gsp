<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Available Visit Forms" ])

	config.require("visit")

	def onFormClick = { form ->
		def visitId = currentVisit ? currentVisit.id : activeVisit.id
		def opts = [ appId: currentApp.id, visitId: visitId, patientId: currentPatient.id, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
    	"""ui.navigate('${ ui.pageLink('kenyaemr', 'intake/enterLabResultMain', opts) }');"""
	}
%>

${ ui.includeFragment("kenyaui", "widget/formStack", [ forms: availableForms, onFormClick: onFormClick ]) }