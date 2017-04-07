<%
	def startFieldName = config.startFieldName ?: "param[startDate]";
	def endFieldName = config.endFieldName ?: "param[endDate]";
%>
<script type="text/javascript">
	jQuery(function() {
		var select = jQuery('#${ config.id }');

		select.change(function() {
			var period = jQuery(this).val().split('|');
			jQuery('#${ config.id }_start').val(period[0]);
			jQuery('#${ config.id }_end').val(period[1]);
		});

		select.change();
	});
</script>

<!--<select id="${ config.id }">
	<% months.each { month -> %>
	<option value="${ month.range }">${ month.label }</option>
	<% } %>
</select>-->
Start Date 
${ ui.includeFragment("kenyaui", "field/java.util.Date" ,[id:'startDate',  formFieldName:startFieldName, showTime: false])}
End Date 
${ ui.includeFragment("kenyaui", "field/java.util.Date" ,[id:'endDate',  formFieldName:endFieldName, showTime: false])}


<!--<input id="${ config.id }_start" type="hidden" name="${ startFieldName }"  />
<input id="${ config.id }_end" type="hidden" name="${ endFieldName }" />-->

<span id="${ config.id }-error" class="error" style="display: none"></span>

<% if (config.parentFormId) { %>
<script type="text/javascript">
	// TODO
</script>
<% } %>