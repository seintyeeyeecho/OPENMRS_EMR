<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Quaterly Report" ])

%>

<form id="hfgjjbb">

	<label class="ke-field-label">Quarterly Selection:</label>
	<span class="ke-field-content">
		<select style='width: 155px;height: 30px;' type="text" id="quaterly" name="quaterly" >
<% listOfYear.each { year -> %>
<option value="${year}">${year}</option>
<% } %>
</select>
	</span>
	
	<input type="button" value="View" onclick="viewQuaterlyReport();"/>
	<input type="button" value="Export" onclick="quarterIndex();"/>
	<input type="button" value="Cancel" onclick="ke_cancel_quaterly();"/>
</form>


<script type="text/javascript">
var count=0;
var today = new Date();
var currentYear = today.getFullYear(); 

function quarterIndex(){
	var quaterly=jQuery('#quaterly').val();
	if(quaterly=="First Quarter"){
		
		jQuery.ajax({
		async : false,
				type : "GET",
				url : getContextPath_quaterly() + "/kenyaemr/reports/getQuaterlyReport.page",
				data : ({
					quaterly:"First Quarter"
				}),
				success : function(data) {
				jQuery("#exportQuaterlyReport").html(data);	
				}
         });
		count=0;
		exportQuaterlyReportToExcel('exportQuaterlyReport');
	//	confirm("First Quarter-"+currentYear);
		exportQuaterlyReportToExcel('exportQuaterlyReport');
	}
	else if(quaterly=="Second Quarter"){
			
			jQuery.ajax({
			async : false,
					type : "GET",
					url : getContextPath_quaterly() + "/kenyaemr/reports/getQuaterlyReport.page",
					data : ({
						quaterly:"Second Quarter"
					}),
					success : function(data) {
					jQuery("#exportQuaterlyReport").html(data);	
					}
	         });
			count=0;
			exportQuaterlyReportToExcel('exportQuaterlyReport');
	//		confirm("Second Quarter-"+currentYear);
			exportQuaterlyReportToExcel('exportQuaterlyReport');
		}
	else if(quaterly=="Third Quarter"){
			
			jQuery.ajax({
			async : false,
					type : "GET",
					url : getContextPath_quaterly() + "/kenyaemr/reports/getQuaterlyReport.page",
					data : ({
						quaterly:"Third Quarter"
					}),
					success : function(data) {
					jQuery("#exportQuaterlyReport").html(data);	
					}
	         });
			count=0;
			exportQuaterlyReportToExcel('exportQuaterlyReport');
	//		confirm("Third Quarter-"+currentYear);
			exportQuaterlyReportToExcel('exportQuaterlyReport');
		}		
	
	else {
		jQuery.ajax({
		async : false,
				type : "GET",
				url : getContextPath_quaterly() + "/kenyaemr/reports/getQuaterlyReport.page",
				data : ({
					quaterly:"Fourth Quarter"
				}),
				success : function(data) {
				jQuery("#exportQuaterlyReport").html(data);	
				}
         });
		count=0;
		exportQuaterlyReportToExcel('exportQuaterlyReport');
	//	confirm("Fourth Quarter-"+currentYear);
		exportQuaterlyReportToExcel('exportQuaterlyReport');
	}
}


//View Report
function viewQuaterlyReport() {
var quaterly=jQuery('#quaterly').val();
jQuery('#viewReportQuaterly').empty();

jQuery.ajax({
				type : "GET",
				url : getContextPath_quaterly() + "/kenyaemr/reports/getQuaterlyReport.page",
				data : ({
					quaterly:quaterly
				}),
				success : function(data) {
				jQuery("#viewReportQuaterly").html(data);	
				}
  });
}

var exportQuaterlyReportToExcel = (function() {
			
			
		var uri = 'data:application/vnd.ms-excel;base64,'
		, template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table >{table}</table></body></html>'
		, base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
		, format = function(s, c) { return s.replace(/{(\\w+)}/g, function(m, p) { return c[p]; }) }
		return function(table, name) {
		if (!table.nodeType) table = document.getElementById(table)
		var ctx = {worksheet: name || 'Cohort report', table: table.innerHTML}
		
		var link = document.createElement("a");
		link.href = uri + base64(format(template, ctx));

		link.style = 'visibility:hidden';
		link.download ='${ currDate } - quarterly report.xls';

		document.body.appendChild(link);
		
		if(count>0){
	  		link.click();
		}
		count++;
		
		}
	})();
	

	

// get context path in order to build controller url
	function getContextPath_quaterly() {
		pn = location.pathname;
		len = pn.indexOf("/", 1);
		cp = pn.substring(0, len);
		return cp;
	}

function ke_cancel_quaterly() {
			ui.navigate('kenyaemr', 'reports/reportsHome');
}
</script>

<div class="ke-page-content" id="exportQuaterlyReport" hidden="hidden">
</div>
