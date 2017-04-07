<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

%>

<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaemr", "report/halfYearlyReportForm") }
</div>

<div class="ke-page-content" id="viewHalfYearlyReport">

</div>

<script type="text/javascript">
	jQuery(function() {
		//jQuery('input[name="query"]').focus();
	});
</script>