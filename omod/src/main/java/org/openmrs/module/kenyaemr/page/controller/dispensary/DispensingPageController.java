package org.openmrs.module.kenyaemr.page.controller.dispensary;

import org.openmrs.Patient;
import org.openmrs.module.kenyaemr.EmrConstants;
import org.openmrs.module.kenyaemr.EmrWebConstants;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

@AppPage(EmrConstants.APP_DISPENSING)
public class DispensingPageController {
	public String controller(UiUtils ui, 
			PageModel model ) {
		
	
		Patient patient = (Patient) model.getAttribute(EmrWebConstants.MODEL_ATTR_CURRENT_PATIENT);
	
		if (patient != null) {
			return "redirect:" + ui.pageLink(EmrConstants.MODULE_ID, "dispensary/dispensing", SimpleObject.create("patientId", patient.getId()));
		} else {
			return null;
		}
	
	}
}

