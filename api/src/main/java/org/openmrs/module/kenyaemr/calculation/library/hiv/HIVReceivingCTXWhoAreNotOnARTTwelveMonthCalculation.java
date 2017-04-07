package org.openmrs.module.kenyaemr.calculation.library.hiv;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.kenyaemr.calculation.BaseEmrCalculation;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

@SuppressWarnings("deprecation")
public class HIVReceivingCTXWhoAreNotOnARTTwelveMonthCalculation extends AbstractPatientCalculation {

	
	
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,PatientCalculationContext context) {

		Calendar calendar = Calendar.getInstance();
		
		//endDate value have to be set from UI
        Date endDate = new Date(); 
        
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, -360); //12 months
        Date startDate = calendar.getTime();
        
		
		// in HIV
		Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
		// in ART
		Program ARTProgram = MetadataUtils.existing(Program.class, Metadata.Program.ART);
		
		// get to a list
		Set<Integer> alive = Filters.alive(cohort, context);
		Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
		Set<Integer> inARTProgram = Filters.inProgram(ARTProgram, alive, context);
		
		Concept ctx = Dictionary.getConcept(Dictionary.SULFAMETHOXAZOLE_TRIMETHOPRIM);
		
		CalculationResultMap ret = new CalculationResultMap();
		
		for (Integer ptId : cohort) {
			
			boolean finalVal = false;
				
			if (inHivProgram.contains(ptId) && !(inARTProgram.contains(ptId))) {

				Set<Obs> o = Context.getObsService().getObservations(new Patient(ptId), Dictionary.getConcept(Dictionary.OI_TREATMENT_DRUG), false);

				if (!o.isEmpty()){
					for(Obs i : o){
						Date obsDate = i.getObsDatetime();
						if(i.getValueCoded().equals(ctx)&& obsDate.after(startDate) && obsDate.before(endDate)){
							finalVal = true;
						}
					}
				}
				
			}
			ret.put(ptId, new BooleanResult(finalVal, this));
		}
		return ret;
	}

}