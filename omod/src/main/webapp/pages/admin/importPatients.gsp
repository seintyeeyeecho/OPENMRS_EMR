<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

	def menuItems = [
			[ label: "Import Legacy Data", iconProvider: "kenyaemr", icon: "buttons/patient_merge.png", href: ui.pageLink("kenyaemr", "admin/importPatients", [ returnUrl: ui.thisUrl() ]) ],
			[ label: "Back to home", iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back to home", href: ui.pageLink("kenyaemr", "admin/adminHome") ]
	]
%>
<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>


<div class="ke-page-content">
		${ ui.includeFragment("kenyaemr", "patient/importPatientsList") }
</div>