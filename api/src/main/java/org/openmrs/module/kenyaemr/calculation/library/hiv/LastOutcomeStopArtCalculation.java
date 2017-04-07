package org.openmrs.module.kenyaemr.calculation.library.hiv;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.ObsResult;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.ArtMetadata;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

public class LastOutcomeStopArtCalculation extends AbstractPatientCalculation {


/**
 * Calculates the last Viral load  of patients
 */

	/**
	 *
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {
		
		CalculationResultMap lastEncounter = Calculations.lastEncounter(MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_DISCONTINUATION), cohort, context);
		CalculationResultMap lastEncounterArt = Calculations.lastEncounter(MetadataUtils.existing(EncounterType.class, ArtMetadata._EncounterType.STOP_ART), cohort, context);
	
		
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {boolean result = false;
			Encounter encounterInfo = EmrCalculationUtils.encounterResultForPatient(lastEncounter, ptId);
			Encounter encounterInfoResult = EmrCalculationUtils.encounterResultForPatient(lastEncounterArt, ptId);
			
			Date dateLastSeen = null;Date dateLastSeenArt = null;
			if(encounterInfo != null && encounterInfoResult!=null ){
				dateLastSeen = encounterInfo.getEncounterDatetime();
				dateLastSeenArt = encounterInfoResult.getEncounterDatetime();
			
				if(dateLastSeenArt.after(dateLastSeen))
				{ 
					result=true;
				}
			}
			ret.put(ptId, new BooleanResult(result, this));
		}
		return  ret;
		
	}	
}	
		
	
	
