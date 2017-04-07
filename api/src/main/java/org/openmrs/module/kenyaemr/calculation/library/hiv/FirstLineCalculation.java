package org.openmrs.module.kenyaemr.calculation.library.hiv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;

public class FirstLineCalculation extends AbstractPatientCalculation {

	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 * @should return null for patients who have never started ARVs
	 * @should return null for patients who aren't currently on ARVs
	 * @should return whether patients have changed regimens
	 */
	@Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> arg1, PatientCalculationContext context) {

		
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean onOrigFirstLine = false;
			 KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
		 	   List<DrugOrderProcessed> cd = kenyaEmrService.getAllfirstLine();
		 	   {
		 	  for(DrugOrderProcessed d:cd)
		 	  {
		 		 
		 	  if((ptId.equals(d.getPatient().getPatientId()) && (d.getDrugRegimen().equals("TDF/FTC/DTG"))|| (d.getDrugRegimen().equals("TDF/3TC/DTG"))|| (d.getDrugRegimen().equals("ABC/FTC/EFV"))|| (d.getDrugRegimen().equals("ABC/3TC/DTG"))|| (d.getDrugRegimen().equals("ABC/FTC/DTG"))|| (d.getDrugRegimen().equals("ABC/3TC/NVP"))|| (d.getDrugRegimen().equals("ABC/FTC/NVP"))|| (d.getDrugRegimen().equals("ABC/3TC/EFV"))))
		 		 {  
		 			onOrigFirstLine=true; 
		 		 }
		 		
		 	  }
		}
			
			ret.put(ptId, new BooleanResult(onOrigFirstLine, this, context));
		}
		return ret;
    }
}