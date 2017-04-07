<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

%>

<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaemr", "report/yearlyReportForm") }
</div>

<div class="ke-page-content" id="viewReport">

</div>

<script type="text/javascript">
	jQuery(function() {
		//jQuery('input[name="query"]').focus();
	});
</script>
