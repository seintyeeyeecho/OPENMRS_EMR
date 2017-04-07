package org.openmrs.module.kenyaemr.calculation.library.hiv;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;

public class OnSubsituteFirstLineArtCalculation extends AbstractPatientCalculation {

	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection, java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 * @should return null for patients who have never started ARVs
	 * @should return null for patients who aren't currently on ARVs
	 * @should return whether patients have changed regimens
	 */
	@Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> arg1, PatientCalculationContext context) {

		
		Set<Integer> alive = Filters.alive(cohort, context);
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : alive) {
			boolean onOrigFirstLines = false;
			 KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
		 	   List<DrugOrderProcessed> drugorderprocess = kenyaEmrService.getAllfirstLine();
		 	  DrugOrderProcessed drugorder = new DrugOrderProcessed();
		 	   {
		 	  for(DrugOrderProcessed order:drugorderprocess)
		 	  {
		 		 if(order.getPatient().getAge()<=14) 
		 		 {
		 	 
		 	  if((ptId.equals(order.getPatient().getPatientId())&&(order.getRegimenChangeType().equals("Substitute")) &&(order.getTypeOfRegimen().equals("Fixed dose combinations (FDCs)"))))
		 	  { if(order.getDrugRegimen().equals(drugorder.getDrugRegimen()))
		 		  { 
		 			 onOrigFirstLines = false;
		 			
		 		  }
		 		  else
		 		  {  onOrigFirstLines = true;
		 			drugorder=order;
		 			ret.put(ptId, new BooleanResult(onOrigFirstLines, this, context));
		 		  }
		 		 if(order.getDiscontinuedDate()!=null)
			 	  { 
			 		 onOrigFirstLines=false; 
			 		ret.put(ptId, new BooleanResult(onOrigFirstLines, this, context));
			 	  }
		 		
		 	  }
		 	  
			  else if((ptId.equals(order.getPatient().getPatientId())&&(order.getRegimenChangeType().equals("Substitute")) &&(order.getTypeOfRegimen().equals("ARV drugs for child")))){
			 		 if(order.getDrugRegimen().equals(drugorder.getDrugRegimen()))
			 		  { 
			 			 onOrigFirstLines = false;
			 			
			 		  }
			 		  else
			 		  {  onOrigFirstLines = true;
			 			drugorder=order;
			 			ret.put(ptId, new BooleanResult(onOrigFirstLines, this, context));
			 		  }
			 		 if(order.getDiscontinuedDate()!=null)
				 	  { 
				 		 onOrigFirstLines=false; 
				 		ret.put(ptId, new BooleanResult(onOrigFirstLines, this, context));
				 	  }
			 		
			 	    
			 	  }
			 
		 		 }
		 		 else
		 		 {
		 			if((ptId.equals(order.getPatient().getPatientId()) &&(order.getRegimenChangeType().equals("Substitute")) &&(order.getTypeOfRegimen().equals("First line Anti-retoviral drugs"))))
			 		 {  
			 			onOrigFirstLines=true; 
			 			ret.put(ptId, new BooleanResult(onOrigFirstLines, this, context));
			 			 if(order.getDiscontinuedDate()!=null)
					 	  { 
					 		 onOrigFirstLines=false; 
					 		ret.put(ptId, new BooleanResult(onOrigFirstLines, this, context));
					 	  }
			 		 }
		 			else if((ptId.equals(order.getPatient().getPatientId())&&(order.getRegimenChangeType().equals("Substitute")) &&(order.getTypeOfRegimen().equals("Fixed dose combinations (FDCs)"))))
			 	  { if(order.getDrugRegimen().equals(drugorder.getDrugRegimen()))
			 		  { 
			 			 onOrigFirstLines = false;
			 			
			 		  }
			 		  else
			 		  {  onOrigFirstLines = true;
			 			drugorder=order;
			 			ret.put(ptId, new BooleanResult(onOrigFirstLines, this, context));
			 		  }
			 		 if(order.getDiscontinuedDate()!=null)
				 	  { 
				 		 onOrigFirstLines=false; 
				 		ret.put(ptId, new BooleanResult(onOrigFirstLines, this, context));
				 	  }
			 		
			 	  }
		 		 }
		 	  }
		}
			
		
    }return ret;
}
}

