<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-content">

	${ /*ui.includeFragment("kenyaui", "widget/tabMenu", [ items: [
			[ label: "Overview", tabid: "overview" ],
			[ label: "Lab Tests", tabid: "labtests" ],
			[ label: "Prescriptions", tabid: "prescriptions" ]
	] ])*/ "" }

	<!--<div class="ke-tab" data-tabid="overview">-->
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="30%" valign="top" style="padding-left: 5px">
					<% if (activeVisit) { %>
					${ ui.includeFragment("kenyaemr", "visitAvailableForms", [ visit: activeVisit ]) }
					${ ui.includeFragment("kenyaemr", "nextAppointmentForm", [ patient: currentPatient, visit: activeVisit ]) }

					<% } %>
					${ ui.includeFragment("kenyaemr", "program/programHistories", [ patient: currentPatient, showClinicalData: true ]) }
				</td>
				<td width="40%" valign="top">
					${ ui.includeFragment("kenyaemr", "patient/patientSummary", [ patient: currentPatient ]) }
			<!-- 	${ ui.includeFragment("kenyaemr", "patient/patientRelationships", [ patient: currentPatient ]) } -->
					${ ui.includeFragment("kenyaemr", "program/programCarePanels", [ patient: currentPatient, complete: false, activeOnly: true ]) }

					${ ui.includeFragment("kenyaemr", "program/tb/tbCarePanel", [ patient: currentPatient, complete: false, activeOnly: false ]) }	
				</td>
				<td width="30%" valign="top" style="padding-left: 5px">
					
					${ ui.includeFragment("kenyaemr", "visitMenu", [ patient: currentPatient, visit: activeVisit ]) }
					
					${ ui.includeFragment("kenyaemr", "patient/patientWhiteCard", [ patient: currentPatient ]) }
					${ ui.includeFragment("kenyaemr", "patient/patientChart", [ patient: currentPatient ]) }
					<% if (activeVisit) { %>
					
					${ ui.includeFragment("kenyaemr", "visitCompletedForms", [ visit: activeVisit ]) }
					<% } %>
					
				</td>
			</tr>
		</table>
	<!--</div>-->

</div>