package org.openmrs.module.kenyaemr.calculation.library.hiv;

import java.util.Collection;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.ObsResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyaemr.Dictionary;

public class LastOutcomeLossCalculation extends AbstractPatientCalculation {


/**
 * Calculates the last Viral load  of patients
 */

	/**
	 * Evaluates the calculation
	 * @should calculate null for patients with no recorded CD4 count
	 * @should calculate last CD4 count for all patients with a recorded CD4 count
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		
	//	Concept died = Dictionary.getConcept(Dictionary.DIED);
		Concept loss = Dictionary.getConcept(Dictionary.LOST_MISSING_FOLLOW);
	//	Concept transfer = Dictionary.getConcept(Dictionary.TRANSFERRED_OUT);
		CalculationResultMap ret = new CalculationResultMap();
		CalculationResultMap c= Calculations.lastObs(Dictionary.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION), cohort, context);
		//List<Obs> ctxProphylaxisObsList = CalculationUtils.extractResultValues(c);
		for(int ptId: cohort){
			boolean result = false;
			ObsResult outcomeObs = (ObsResult)c.get(ptId);
		
			
			if (outcomeObs != null) {
				if(outcomeObs.getValue().getValueCoded().equals(loss))
				{	
					result = true ;
				 //  break;
				}
				
			}
			
			ret.put(ptId, new BooleanResult(result, this));
			
		}
		
		return ret;
    }
}
