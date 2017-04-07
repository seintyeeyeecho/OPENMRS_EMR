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

package org.openmrs.module.kenyaemr.fragment.controller.program.mchcs;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.wrapper.EncounterWrapper;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.kenyaemr.metadata.MchMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for child services care summary
 */
public class MchcsCarePanelFragmentController {

	public void controller(@FragmentParam("patient") Patient patient,
						   @FragmentParam("complete") Boolean complete,
						   FragmentModel model) {

		Map<String, Object> calculations = new HashMap<String, Object>();
		List<Obs> milestones = new ArrayList<Obs>();
		List<Obs> remarks = new ArrayList<Obs>();
		Obs heiOutcomes = null;
		Obs hivExposed = null;
		Obs hivStatus = null;

		PatientWrapper patientWrapper = new PatientWrapper(patient);

		EncounterType hei_completion_encounterType = MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCHCS_HEI_COMPLETION);
		Encounter lastMchcsHeiCompletion = patientWrapper.lastEncounter(hei_completion_encounterType);
		EncounterType mchcs_enrollment_encounterType = MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCHCS_ENROLLMENT);
		Encounter lastMchcsEnrollment = patientWrapper.lastEncounter(mchcs_enrollment_encounterType);
		EncounterType mchcs_consultation_encounterType = MetadataUtils.existing(EncounterType.class, MchMetadata._EncounterType.MCHCS_CONSULTATION);
		Encounter lastMchcsConsultation = patientWrapper.lastEncounter(mchcs_consultation_encounterType);

		if (lastMchcsHeiCompletion != null && lastMchcsEnrollment != null) {
			EncounterWrapper heiCompletionWrapper = new EncounterWrapper(lastMchcsHeiCompletion);
			EncounterWrapper mchcsEnrollmentWrapper = new EncounterWrapper(lastMchcsEnrollment);

			heiOutcomes = heiCompletionWrapper.firstObs(Dictionary.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION));
			hivExposed =  mchcsEnrollmentWrapper.firstObs(Dictionary.getConcept(Dictionary.CHILDS_CURRENT_HIV_STATUS));
			hivStatus =  heiCompletionWrapper.firstObs(Dictionary.getConcept(Dictionary.HIV_STATUS));
		}

		if ((hivExposed != null) && (hivExposed.getValueCoded() != Dictionary.getConcept(Dictionary.EXPOSURE_TO_HIV))) {
			calculations.put("heioutcomes", "Not HIV Exposed");
		}
		else if (heiOutcomes != null && hivExposed != null && hivStatus != null) {
			calculations.put("heioutcomes", heiOutcomes.getValueCoded());
			calculations.put("hivStatus",hivStatus.getValueCoded());
		}
		else {
			calculations.put("heioutcomes", "Still in HEI Care");
			calculations.put("hivStatus", "Not Specified");
		}

		if (lastMchcsConsultation != null) {
			EncounterWrapper mchcsConsultationWrapper = new EncounterWrapper(lastMchcsConsultation);

			milestones.addAll(mchcsConsultationWrapper.allObs(Dictionary.getConcept(Dictionary.DEVELOPMENTAL_MILESTONES)));

			if (milestones.size() > 0) {
				calculations.put("milestones", milestones);
			}
			else {
				calculations.put("milestones", "Not Specified");
			}

			remarks.addAll(mchcsConsultationWrapper.allObs(Dictionary.getConcept(Dictionary.REVIEW_OF_SYSTEMS_DEVELOPMENTAL)));

			if (remarks.size() > 0) {
				calculations.put("remarks", remarks);
			}
			else {
				calculations.put("remarks", "Not Specified");
			}
		}
		else {
			calculations.put("milestones", "Not Specified");
			calculations.put("remarks", "Not Specified");
		}

		model.addAttribute("calculations", calculations);
	}

}