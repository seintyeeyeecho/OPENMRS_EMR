<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

%>

${ ui.includeFragment("kenyaemr", "report/excelExport",[year:year]) }