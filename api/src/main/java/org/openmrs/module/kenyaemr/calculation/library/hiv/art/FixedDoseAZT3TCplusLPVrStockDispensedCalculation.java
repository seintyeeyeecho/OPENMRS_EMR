package org.openmrs.module.kenyaemr.calculation.library.hiv.art;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;

public class FixedDoseAZT3TCplusLPVrStockDispensedCalculation extends AbstractPatientCalculation {

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
		 	   List<DrugOrderProcessed> drugorderprocess = kenyaEmrService.getAllfirstLine() ;
		 	 
		 	  for(DrugOrderProcessed orderprocess:drugorderprocess)
		 	  {   if(orderprocess.getPatient().getAge()<=14)
		 	  {
		 		 if((ptId.equals(orderprocess.getPatient().getPatientId()) &&(orderprocess.getDrugRegimen().equals("AZT/3TC+LPV/r")) && (orderprocess.getDoseRegimen().equals("60/30+100/25 mg"))))
		 			 
		 		
		 		  {  
		 			 if( orderprocess.getProcessedStatus()==true)
		 		      {
		 			
		 			 stock=orderprocess.getQuantityPostProcess();
		 			
			 			
			 			ret.put(ptId, new SimpleResult(stock, this, context));
		 		  }
		 			
		 		  }
		 		 
		 		 } 
		 	  else
		 	  {
		 		 if((ptId.equals(orderprocess.getPatient().getPatientId()) &&(orderprocess.getDrugRegimen().equals("AZT/3TC+LPV/r")) && (orderprocess.getDoseRegimen().equals("300/150+200/50 mg"))))
		 			 
				 		
		 		  {  
		 			 if( orderprocess.getProcessedStatus()==true)
		 		      {
		 			
		 			 stock=orderprocess.getQuantityPostProcess();
		 			
			 			
			 			ret.put(ptId, new SimpleResult(stock, this, context));
		 		  }
		 			
		 		  }  
		 	  }
		 	  }
		 	  
		 	  }
		 	  
		
			
			
	
		return ret;
    }
}

