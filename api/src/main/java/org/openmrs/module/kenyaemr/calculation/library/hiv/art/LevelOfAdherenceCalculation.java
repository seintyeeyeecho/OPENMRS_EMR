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

package org.openmrs.module.kenyaemr.calculation.library.hiv.art;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyacore.calculation.Calculations;

import java.util.Set;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.HivConstants;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LostToFollowUpCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Obs;

/**
 * Calculates whether patients who have level of adherence
 */
public class LevelOfAdherenceCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {

	private int minPercentage,maxPercentage;
	public LevelOfAdherenceCalculation(){
		
	}
	
	public LevelOfAdherenceCalculation(int minPercentage,int maxPercentage){
		this.minPercentage = minPercentage;
		this.maxPercentage =maxPercentage;
		
	}
	/**
	 * @see org.openmrs.module.kenyacore.calculation.PatientFlagCalculation#getFlagMessage()
	 */
	@Override
	public String getFlagMessage() {
		return "Adherence";
	}
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection,
	 *      java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,
	                                     PatientCalculationContext context) {
		Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);

		Set<Integer> alive = Filters.alive(cohort, context);
		Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);

		// Get the two CD4 obss for comparison
		
		CalculationResultMap dosesReceived = Calculations.lastObs(Dictionary.getConcept(Dictionary.NUMBER_OF_DOSES_RECEIVED_BEFORE_ENROLLMENT), inHivProgram, context);
		CalculationResultMap dosesMissed = Calculations.lastObs(Dictionary.getConcept(Dictionary.NUMBER_OF_MISSED_DOSES_IN_LAST_30_DAYS),inHivProgram, context);
		
		CalculationResultMap percentageCalculation = Calculations.lastObs(Dictionary.getConcept(Dictionary.ART_ADHERENCE),inHivProgram, context);
		
		//HISP added
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean resultFlag = false;

			// Is patient alive and in HIV program?
			if (inHivProgram.contains(ptId)) {
				Double lastCD4Count = EmrCalculationUtils.numericObsResultForPatient(dosesReceived, ptId);
				Double oldCD4Count = EmrCalculationUtils.numericObsResultForPatient(dosesMissed, ptId);
				Obs o = EmrCalculationUtils.obsResultForPatient(percentageCalculation, ptId);
				
				if(lastCD4Count!=null && oldCD4Count !=null){
					
					Double persentage = oldCD4Count/lastCD4Count*100;
				
					if(persentage > minPercentage && persentage <= maxPercentage) {
						resultFlag = true;
					}
				}
				
				else if(o!=null && o.getValueText()!=null){
					if(o.getValueText().equals(">95%") && minPercentage==0){
						resultFlag = true;
					}
					else if(o.getValueText().equals("80-95%") && minPercentage==5){
						resultFlag = true;
					}
					else if(o.getValueText().equals("<80%") && minPercentage==20){
						resultFlag = true;
					}
					else if(minPercentage==0 && maxPercentage==100){
						resultFlag = true;
					}
				}
				
			}
			ret.put(ptId, new BooleanResult(resultFlag, this, context));
		}
		return ret;
	}
}