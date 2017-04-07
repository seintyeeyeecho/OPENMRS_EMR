<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Half Yearly Report" ])

%>

<form id="halfYearlyReport">

	<label class="ke-field-label">Half Yearly Selection:</label>
	<span class="ke-field-content">
		<select style='width: 155px;height: 30px;' type="text" id="halfYearly" name="halfYearly" >
<% listOfYear.each { year -> %>
<option value="${year}">${year}</option>
<% } %>
</select>
	</span>
	
	<input type="button" value="View" onclick="viewHalfYearlyReport();"/>
	<input type="button" value="Export" onclick="halfYearIndex();"/>
	<input type="button" value="Cancel" onclick="ke_cancel_halfyearly();"/>
</form>


<script type="text/javascript">
var count=0;
var today = new Date();
var currentYear = today.getFullYear(); 

function  halfYearIndex(){
	
	var halfYearly=jQuery('#halfYearly').val();
	if(halfYearly=="First Half"){
		jQuery.ajax({
		async : false,
				type : "GET",
				url : getContextPath_halfyearly() + "/kenyaemr/reports/getHalfYearlyReport.page",
				data : ({
					halfYearly:"First Half"
				}),
				success : function(data) {
				jQuery("#exportHalfYearlyReport").html(data);	
				}
         });
		count=0;
		exportHalfYearlyReportToExcel('exportHalfYearlyReport');
//	confirm("If entrie's are wrong or empty try again");
		exportHalfYearlyReportToExcel('exportHalfYearlyReport');
	}
	else {
		jQuery.ajax({
		async : false,
				type : "GET",
				url : getContextPath_halfyearly() + "/kenyaemr/reports/getHalfYearlyReport.page",
				data : ({
					halfYearly:"Second Half"
				}),
				success : function(data) {
				jQuery("#exportHalfYearlyReport").html(data);	
				}
         });
		count=0;
		exportHalfYearlyReportToExcel('exportHalfYearlyReport');
	//	confirm("If entrie's are wrong or empty try again");
		exportHalfYearlyReportToExcel('exportHalfYearlyReport');
	}
}




//View Report
function viewHalfYearlyReport() {

var halfYearly=jQuery('#halfYearly').val();

jQuery('#viewHalfYearlyReport').empty();

jQuery.ajax({
				type : "GET",
				url : getContextPath_halfyearly() + "/kenyaemr/reports/getHalfYearlyReport.page",
				data : ({
					halfYearly:halfYearly
				}),
				success : function(data) {
				jQuery("#viewHalfYearlyReport").html(data);	
				}
  });
}

//Excel export
var exportHalfYearlyReportToExcel = (function() {
         
		var uri = 'data:application/vnd.ms-excel;base64,'
		, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table >{table}</table></body></html>'
		, base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
		, format = function(s, c) { return s.replace(/{(\\w+)}/g, function(m, p) { return c[p]; }) }
		return function(table, name) {
		if (!table.nodeType) table = document.getElementById(table)
		var ctx = {worksheet: name || 'Cohort Report', table: table.innerHTML}
		
		var link = document.createElement("a");
		link.href = uri + base64(format(template, ctx));

		link.style = 'visibility:hidden';
		link.download ='${ currDate } - half yearly report.xls';

		document.body.appendChild(link);
		if(count>0){
			link.click();
		}
		count++;	
		}
	})();

// get context path in order to build controller url
	function getContextPath_halfyearly() {
		pn = location.pathname;
		len = pn.indexOf("/", 1);
		cp = pn.substring(0, len);
		return cp;
	}

function ke_cancel_halfyearly() {
			ui.navigate('kenyaemr', 'reports/reportsHome');
}
</script>

<div class="ke-page-content" id="exportHalfYearlyReport" hidden="hidden">

</div>