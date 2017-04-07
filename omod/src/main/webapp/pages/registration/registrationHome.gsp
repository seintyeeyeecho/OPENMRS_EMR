<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

	def menuItems = [
			[ label: "Create new patient", extra: "Patient can't be found", iconProvider: "kenyaui", icon: "buttons/patient_add.png", href: ui.pageLink("kenyaemr", "registration/createPatient2") ],
			
	]
%>

<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaemr", "patient/patientSearchForm", [ defaultWhich: "all" ]) }

	${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
	
		<div class="ke-panel-frame">
		<div class="ke-panel-heading">Help</div>
		<div class="ke-panel-content">
			<div id="new">
				<h3><u>Create new Patient</u></h3>
				Use this option if you are registering the patient for the first time in the system.

			</div>
			
		</div>
	</div>
	
</div>

<div class="ke-page-content">
	${ ui.includeFragment("kenyaemr", "patient/patientSearchResults", [ pageProvider: "kenyaemr", page: "registration/registrationViewPatient" ]) }
</div>



<script type="text/javascript">
	jQuery(function() {
		jQuery('input[name="query"]').focus();
	});
</script>