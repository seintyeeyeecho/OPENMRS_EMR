package org.openmrs.module.kenyaemr.fragment.controller.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class QuaterlyReportFormFragmentController {
	public void controller(FragmentModel model, UiUtils ui) {
		List<String> listOfYear = new ArrayList<String>();
		listOfYear.add("First Quarter");
		listOfYear.add("Second Quarter");
		listOfYear.add("Third Quarter");
		listOfYear.add("Fourth Quarter");
		model.addAttribute("listOfYear", listOfYear);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		model.addAttribute("currDate", formatter.format(new Date()));
	}
	
}
