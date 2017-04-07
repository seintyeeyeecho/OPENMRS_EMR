package org.openmrs.module.kenyaemr.calculation.library.hiv.art;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;

public class D4T3TCEFVStockDispensedCalculation extends AbstractPatientCalculation {

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
			Integer stock=0;
			 KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
		 	   List<DrugOrderProcessed> drugorderprocess = kenyaEmrService.getAllfirstLine(); 
		 	
		 	   
		 	  for(DrugOrderProcessed orderprocess:drugorderprocess)
		 	  {  
		 	 
		 		 if((ptId.equals(orderprocess.getPatient().getPatientId()) &&(orderprocess.getDrugRegimen().equals("d4T/3TC/EFV")) && (orderprocess.getDoseRegimen().equals("30/150/600 mg"))))
		 			 
		 		 { 
		 			 if( orderprocess.getProcessedStatus()==true)
		 		      {
		 			
		 			 stock=orderprocess.getQuantityPostProcess();
			 			ret.put(ptId, new SimpleResult(stock, this, context));
		 		  }
		 		 }   
		 	  }
		 	  
		}
		return ret;
    }
}
