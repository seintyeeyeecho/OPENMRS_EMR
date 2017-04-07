/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.fragment.controller.field;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;
import org.openmrs.module.kenyaemr.regimen.RegimenChange;
import org.openmrs.module.kenyaemr.regimen.RegimenChangeHistory;
import org.openmrs.module.kenyaemr.regimen.RegimenManager;
import org.openmrs.module.kenyaemr.regimen.RegimenOrder;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

/**
 *
 */
public class HivRegimenChangeFragmentController {

	public void controller(@FragmentParam("patient") Patient patient,
			@SpringBean RegimenManager regimenManager,FragmentModel model) {
		KenyaEmrService kenyaEmrService = (KenyaEmrService) Context
				.getService(KenyaEmrService.class);
		DrugOrderProcessed drugOrderProcessed = kenyaEmrService
				.getLastRegimenChangeType(patient);
		model.addAttribute("drugOrderProcessed", drugOrderProcessed);
		SimpleDateFormat formatterExt = new SimpleDateFormat("dd-MMM-yyyy");
		Date startDate=new Date();
		
		//seint commented for existing patients
		if (drugOrderProcessed!= null && drugOrderProcessed.getStartDate()!=null)
		{
		startDate=drugOrderProcessed.getStartDate();
		String date = formatterExt.format(startDate);
		model.addAttribute("date", date);
		}
		else
		{	
			String category="ARV";
			Concept masterSet = regimenManager.getMasterSetConcept(category);
			RegimenChangeHistory history = RegimenChangeHistory.forPatient(patient, masterSet);
			RegimenChange lastChange = history.getLastChange();
			RegimenOrder baseline = lastChange != null ? lastChange.getStarted() : null;
			if (baseline != null) {
			for(DrugOrder drugOrder:baseline.getDrugOrders()){
				startDate=drugOrder.getStartDate();
			}
			String date = formatterExt.format(startDate);
			model.addAttribute("date", date);
			}
			else{
				model.addAttribute("date", "");	
			}

		}
	}
}