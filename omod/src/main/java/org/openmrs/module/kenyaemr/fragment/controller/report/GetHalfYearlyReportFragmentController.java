package org.openmrs.module.kenyaemr.fragment.controller.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class GetHalfYearlyReportFragmentController {
	public void controller(@RequestParam("halfYearly") String halfYearly,
			FragmentModel model, UiUtils ui) {
    KenyaEmrService kenyaEmrService = (KenyaEmrService) Context.getService(KenyaEmrService.class);
    Date date = new Date();
   	SimpleDateFormat df = new SimpleDateFormat("yyyy");
   	String year = df.format(date);
   	
	Program program=Context.getProgramWorkflowService().getProgramByUuid("96ec813f-aaf0-45b2-add6-e661d5bf79d6");
	
	if(halfYearly!=null && halfYearly.equals("First Half")){
		String janStartDate=year+"-"+"01"+"-"+"01";
		String janEndDate=year+"-"+"01"+"-"+"31";
		
		String febStartDate=year+"-"+"02"+"-"+"01";
		String febEndDate=year+"-"+"02"+"-"+"28";
		
		String marchStartDate=year+"-"+"03"+"-"+"01";
		String marchEndDate=year+"-"+"03"+"-"+"31";
		
		String aprilStartDate=year+"-"+"04"+"-"+"01";
		String aprilEndDate=year+"-"+"04"+"-"+"30";
		
		String mayStartDate=year+"-"+"05"+"-"+"01";
		String mayEndDate=year+"-"+"05"+"-"+"31";
		
		String juneStartDate=year+"-"+"06"+"-"+"01";
		String juneEndDate=year+"-"+"06"+"-"+"30";
		
		Set<Patient> patientProgramForJan=kenyaEmrService.getPatientProgram(program,janStartDate,janEndDate);
		Set<Patient> patientProgramForFeb=kenyaEmrService.getPatientProgram(program,febStartDate,febEndDate);
		Set<Patient> patientProgramForMarch=kenyaEmrService.getPatientProgram(program,marchStartDate,marchEndDate);
		Set<Patient> patientProgramForApril=kenyaEmrService.getPatientProgram(program,aprilStartDate,aprilEndDate);
		Set<Patient> patientProgramForMay=kenyaEmrService.getPatientProgram(program,mayStartDate,mayEndDate);
		Set<Patient> patientProgramForJune=kenyaEmrService.getPatientProgram(program,juneStartDate,juneEndDate);
		
		Set<Patient> patientTransferInForJan=kenyaEmrService.getNoOfPatientTransferredIn(janStartDate,janEndDate);
		Set<Patient> patientTransferInForFeb=kenyaEmrService.getNoOfPatientTransferredIn(febStartDate,febEndDate);
		Set<Patient> patientTransferInForMarch=kenyaEmrService.getNoOfPatientTransferredIn(marchStartDate,marchEndDate);
		Set<Patient> patientTransferInForApril=kenyaEmrService.getNoOfPatientTransferredIn(aprilStartDate,aprilEndDate);
		Set<Patient> patientTransferInForMay=kenyaEmrService.getNoOfPatientTransferredIn(mayStartDate,mayEndDate);
		Set<Patient> patientTransferInForJune=kenyaEmrService.getNoOfPatientTransferredIn(juneStartDate,juneEndDate);
		
		Set<Patient> patientTransferOutForJan=kenyaEmrService.getNoOfPatientTransferredOut(janStartDate,janEndDate);
		Set<Patient> patientTransferOutForFeb=kenyaEmrService.getNoOfPatientTransferredOut(febStartDate,febEndDate);
		Set<Patient> patientTransferOutForMarch=kenyaEmrService.getNoOfPatientTransferredOut(marchStartDate,marchEndDate);
		Set<Patient> patientTransferOutForApril=kenyaEmrService.getNoOfPatientTransferredOut(aprilStartDate,aprilEndDate);
		Set<Patient> patientTransferOutForMay=kenyaEmrService.getNoOfPatientTransferredOut(mayStartDate,mayEndDate);
		Set<Patient> patientTransferOutForJune=kenyaEmrService.getNoOfPatientTransferredOut(juneStartDate,juneEndDate);
		
		Set<Patient> totalCohortForJan=kenyaEmrService.getTotalNoOfCohort(janStartDate, janEndDate);
		Set<Patient> totalCohortForFeb=kenyaEmrService.getTotalNoOfCohort(febStartDate,febEndDate);
		Set<Patient> totalCohortForMarch=kenyaEmrService.getTotalNoOfCohort(marchStartDate,marchEndDate);
		Set<Patient> totalCohortForApril=kenyaEmrService.getTotalNoOfCohort(aprilStartDate,aprilEndDate);
		Set<Patient> totalCohortForMay=kenyaEmrService.getTotalNoOfCohort(mayStartDate,mayEndDate);
		Set<Patient> totalCohortForJune=kenyaEmrService.getTotalNoOfCohort(juneStartDate,juneEndDate);
		
		Set<Patient> maleCohortForJan=kenyaEmrService.getCohortBasedOnGender("M",janStartDate, janEndDate);
		Set<Patient> maleCohortForFeb=kenyaEmrService.getCohortBasedOnGender("M",febStartDate,febEndDate);
		Set<Patient> maleCohortForMarch=kenyaEmrService.getCohortBasedOnGender("M",marchStartDate,marchEndDate);
		Set<Patient> maleCohortForApril=kenyaEmrService.getCohortBasedOnGender("M",aprilStartDate,aprilEndDate);
		Set<Patient> maleCohortForMay=kenyaEmrService.getCohortBasedOnGender("M",mayStartDate,mayEndDate);
		Set<Patient> maleCohortForJune=kenyaEmrService.getCohortBasedOnGender("M",juneStartDate,juneEndDate);
		
		Set<Patient> femaleCohortForJan=kenyaEmrService.getCohortBasedOnGender("F",janStartDate, janEndDate);
		Set<Patient> femaleCohortForFeb=kenyaEmrService.getCohortBasedOnGender("F",febStartDate,febEndDate);
		Set<Patient> femaleCohortForMarch=kenyaEmrService.getCohortBasedOnGender("F",marchStartDate,marchEndDate);
		Set<Patient> femaleCohortForApril=kenyaEmrService.getCohortBasedOnGender("F",aprilStartDate,aprilEndDate);
		Set<Patient> femaleCohortForMay=kenyaEmrService.getCohortBasedOnGender("F",mayStartDate,mayEndDate);
		Set<Patient> femaleCohortForJune=kenyaEmrService.getCohortBasedOnGender("F",juneStartDate,juneEndDate);
		
		Integer age1=0;
		Integer age2=14;
		Set<Patient> cohortFor0_14AgeForJan=kenyaEmrService.getCohortBasedOnAge(age1,age2,janStartDate, janEndDate);
		Set<Patient> cohortFor0_14AgeForFeb=kenyaEmrService.getCohortBasedOnAge(age1,age2,febStartDate,febEndDate);
		Set<Patient> cohortFor0_14AgeForMarch=kenyaEmrService.getCohortBasedOnAge(age1,age2,marchStartDate,marchEndDate);
		Set<Patient> cohortFor0_14AgeForApril=kenyaEmrService.getCohortBasedOnAge(age1,age2,aprilStartDate,aprilEndDate);
		Set<Patient> cohortFor0_14AgeForMay=kenyaEmrService.getCohortBasedOnAge(age1,age2,mayStartDate,mayEndDate);
		Set<Patient> cohortFor0_14AgeForJune=kenyaEmrService.getCohortBasedOnAge(age1,age2,juneStartDate,juneEndDate);
		
		age1=15;
		age2=24;
		Set<Patient> cohortFor15_24AgeForJan=kenyaEmrService.getCohortBasedOnAge(age1,age2,janStartDate, janEndDate);
		Set<Patient> cohortFor15_24AgeForFeb=kenyaEmrService.getCohortBasedOnAge(age1,age2,febStartDate,febEndDate);
		Set<Patient> cohortFor15_24AgeForMarch=kenyaEmrService.getCohortBasedOnAge(age1,age2,marchStartDate,marchEndDate);
		Set<Patient> cohortFor15_24AgeForApril=kenyaEmrService.getCohortBasedOnAge(age1,age2,aprilStartDate,aprilEndDate);
		Set<Patient> cohortFor15_24AgeForMay=kenyaEmrService.getCohortBasedOnAge(age1,age2,mayStartDate,mayEndDate);
		Set<Patient> cohortFor15_24AgeForJune=kenyaEmrService.getCohortBasedOnAge(age1,age2,juneStartDate,juneEndDate);
		
		age1=25;
		age2=60;
		Set<Patient> cohortFor25_60AgeForJan=kenyaEmrService.getCohortBasedOnAge(age1,age2,janStartDate, janEndDate);
		Set<Patient> cohortFor25_60AgeForFeb=kenyaEmrService.getCohortBasedOnAge(age1,age2,febStartDate,febEndDate);
		Set<Patient> cohortFor25_60AgeForMarch=kenyaEmrService.getCohortBasedOnAge(age1,age2,marchStartDate,marchEndDate);
		Set<Patient> cohortFor25_60AgeForApril=kenyaEmrService.getCohortBasedOnAge(age1,age2,aprilStartDate,aprilEndDate);
		Set<Patient> cohortFor25_60AgeForMay=kenyaEmrService.getCohortBasedOnAge(age1,age2,mayStartDate,mayEndDate);
		Set<Patient> cohortFor25_60AgeForJune=kenyaEmrService.getCohortBasedOnAge(age1,age2,juneStartDate,juneEndDate);
		
		Set<Patient> noOfCohortAliveAndOnArtForJan=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,janStartDate,janEndDate);
		Set<Patient> noOfCohortAliveAndOnArtForFeb=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,febStartDate,febEndDate);
		Set<Patient> noOfCohortAliveAndOnArtForMarch=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,marchStartDate,marchEndDate);
		Set<Patient> noOfCohortAliveAndOnArtForApril=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,aprilStartDate,aprilEndDate);
		Set<Patient> noOfCohortAliveAndOnArtForMay=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,mayStartDate,mayEndDate);
		Set<Patient> noOfCohortAliveAndOnArtForJune=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,juneStartDate,juneEndDate);
		
		Set<Patient> noOfOriginalFirstLineRegimenForJan=kenyaEmrService.getOriginalFirstLineRegimen(program,janStartDate,janEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForFeb=kenyaEmrService.getOriginalFirstLineRegimen(program,febStartDate,febEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForMarch=kenyaEmrService.getOriginalFirstLineRegimen(program,marchStartDate,marchEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForApril=kenyaEmrService.getOriginalFirstLineRegimen(program,aprilStartDate,aprilEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForMay=kenyaEmrService.getOriginalFirstLineRegimen(program,mayStartDate,mayEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForJune=kenyaEmrService.getOriginalFirstLineRegimen(program,juneStartDate,juneEndDate);
		
		Set<Patient> noOfAlternateFirstLineRegimenForJan=kenyaEmrService.getAlternateFirstLineRegimen(program,janStartDate,janEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForFeb=kenyaEmrService.getAlternateFirstLineRegimen(program,febStartDate,febEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForMarch=kenyaEmrService.getAlternateFirstLineRegimen(program,marchStartDate,marchEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForApril=kenyaEmrService.getAlternateFirstLineRegimen(program,aprilStartDate,aprilEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForMay=kenyaEmrService.getAlternateFirstLineRegimen(program,mayStartDate,mayEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForJune=kenyaEmrService.getAlternateFirstLineRegimen(program,juneStartDate,juneEndDate);
		
		Set<Patient> noOfSecondLineRegimenForJan=kenyaEmrService.getSecondLineRegimen(program,janStartDate,janEndDate);
		Set<Patient> noOfSecondLineRegimenForFeb=kenyaEmrService.getSecondLineRegimen(program,febStartDate,febEndDate);
		Set<Patient> noOfSecondLineRegimenForMarch=kenyaEmrService.getSecondLineRegimen(program,marchStartDate,marchEndDate);
		Set<Patient> noOfSecondLineRegimenForApril=kenyaEmrService.getSecondLineRegimen(program,aprilStartDate,aprilEndDate);
		Set<Patient> noOfSecondLineRegimenForMay=kenyaEmrService.getSecondLineRegimen(program,mayStartDate,mayEndDate);
		Set<Patient> noOfSecondLineRegimenForJune=kenyaEmrService.getSecondLineRegimen(program,juneStartDate,juneEndDate);
		
		Set<Patient> noOfArtStoppedCohortForJan=kenyaEmrService.getNoOfArtStoppedCohort(program,janStartDate,janEndDate);
		Set<Patient> noOfArtStoppedCohortForFeb=kenyaEmrService.getNoOfArtStoppedCohort(program,febStartDate,febEndDate);
		Set<Patient> noOfArtStoppedCohortForMarch=kenyaEmrService.getNoOfArtStoppedCohort(program,marchStartDate,marchEndDate);
		Set<Patient> noOfArtStoppedCohortForApril=kenyaEmrService.getNoOfArtStoppedCohort(program,aprilStartDate,aprilEndDate);
		Set<Patient> noOfArtStoppedCohortForMay=kenyaEmrService.getNoOfArtStoppedCohort(program,mayStartDate,mayEndDate);
		Set<Patient> noOfArtStoppedCohortForJune=kenyaEmrService.getNoOfArtStoppedCohort(program,juneStartDate,juneEndDate);
		
		Set<Patient> noOfArtDiedCohortForJan=kenyaEmrService.getNoOfArtDiedCohort(program,janStartDate,janEndDate);
		Set<Patient> noOfArtDiedCohortForFeb=kenyaEmrService.getNoOfArtDiedCohort(program,febStartDate,febEndDate);
		Set<Patient> noOfArtDiedCohortForMarch=kenyaEmrService.getNoOfArtDiedCohort(program,marchStartDate,marchEndDate);
		Set<Patient> noOfArtDiedCohortForApril=kenyaEmrService.getNoOfArtDiedCohort(program,aprilStartDate,aprilEndDate);
		Set<Patient> noOfArtDiedCohortForMay=kenyaEmrService.getNoOfArtDiedCohort(program,mayStartDate,mayEndDate);
		Set<Patient> noOfArtDiedCohortForJune=kenyaEmrService.getNoOfArtDiedCohort(program,juneStartDate,juneEndDate);
		
		Set<Patient> noOfPatientLostToFollowUpForJan=kenyaEmrService.getNoOfPatientLostToFollowUp(janStartDate,janEndDate);
		Set<Patient> noOfPatientLostToFollowUpForFeb=kenyaEmrService.getNoOfPatientLostToFollowUp(febStartDate,febEndDate);
		Set<Patient> noOfPatientLostToFollowUpForMarch=kenyaEmrService.getNoOfPatientLostToFollowUp(marchStartDate,marchEndDate);
		Set<Patient> noOfPatientLostToFollowUpForApril=kenyaEmrService.getNoOfPatientLostToFollowUp(aprilStartDate,aprilEndDate);
		Set<Patient> noOfPatientLostToFollowUpForMay=kenyaEmrService.getNoOfPatientLostToFollowUp(mayStartDate,mayEndDate);
		Set<Patient> noOfPatientLostToFollowUpForJune=kenyaEmrService.getNoOfPatientLostToFollowUp(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientWithCD4ForJan=kenyaEmrService.getNoOfPatientWithCD4(janStartDate,janEndDate);
		List<Obs> noOfPatientWithCD4ForFeb=kenyaEmrService.getNoOfPatientWithCD4(febStartDate,febEndDate);
		List<Obs> noOfPatientWithCD4ForMarch=kenyaEmrService.getNoOfPatientWithCD4(marchStartDate,marchEndDate);
		List<Obs> noOfPatientWithCD4ForApril=kenyaEmrService.getNoOfPatientWithCD4(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientWithCD4ForMay=kenyaEmrService.getNoOfPatientWithCD4(mayStartDate,mayEndDate);
		List<Obs> noOfPatientWithCD4ForJune=kenyaEmrService.getNoOfPatientWithCD4(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientNormalActivityForJan=kenyaEmrService.getNoOfPatientNormalActivity(janStartDate,janEndDate);
		List<Obs> noOfPatientNormalActivityForFeb=kenyaEmrService.getNoOfPatientNormalActivity(febStartDate,febEndDate);
		List<Obs> noOfPatientNormalActivityForMarch=kenyaEmrService.getNoOfPatientNormalActivity(marchStartDate,marchEndDate);
		List<Obs> noOfPatientNormalActivityForApril=kenyaEmrService.getNoOfPatientNormalActivity(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientNormalActivityForMay=kenyaEmrService.getNoOfPatientNormalActivity(mayStartDate,mayEndDate);
		List<Obs> noOfPatientNormalActivityForJune=kenyaEmrService.getNoOfPatientNormalActivity(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientBedriddenLessThanFiftyForJan=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(janStartDate,janEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForFeb=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(febStartDate,febEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForMarch=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(marchStartDate,marchEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForApril=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForMay=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(mayStartDate,mayEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForJune=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(juneStartDate,juneEndDate);
		
		List<Obs> noOfPatientBedriddenMoreThanFiftyForJan=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(janStartDate,janEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForFeb=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(febStartDate,febEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForMarch=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(marchStartDate,marchEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForApril=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(aprilStartDate,aprilEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForMay=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(mayStartDate,mayEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForJune=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(juneStartDate,juneEndDate);
		
		Set<Patient> noOfPatientPickedUpArvForSixMonthForJan=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(janStartDate,janEndDate);
		Set<Patient> noOfPatientPickedUpArvForSixMonthForFeb=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(febStartDate,febEndDate);
		Set<Patient> noOfPatientPickedUpArvForSixMonthForMarch=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(marchStartDate,marchEndDate);
		Set<Patient> noOfPatientPickedUpArvForSixMonthForApril=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(aprilStartDate,aprilEndDate);
		Set<Patient> noOfPatientPickedUpArvForSixMonthForMay=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(mayStartDate,mayEndDate);
		Set<Patient> noOfPatientPickedUpArvForSixMonthForJune=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(juneStartDate,juneEndDate);
		
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForJan=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(janStartDate,janEndDate);
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForFeb=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(febStartDate,febEndDate);
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForMarch=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(marchStartDate,marchEndDate);
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForApril=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(aprilStartDate,aprilEndDate);
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForMay=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(mayStartDate,mayEndDate);
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForJune=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(juneStartDate,juneEndDate);
		
		model.addAttribute("patientProgramForJan",patientProgramForJan.size());
		model.addAttribute("patientProgramForFeb",patientProgramForFeb.size());
		model.addAttribute("patientProgramForMarch",patientProgramForMarch.size());
		model.addAttribute("patientProgramForApril",patientProgramForApril.size());
		model.addAttribute("patientProgramForMay",patientProgramForMay.size());
		model.addAttribute("patientProgramForJune",patientProgramForJune.size());
		
		model.addAttribute("patientTransferInForJan",patientTransferInForJan.size());
		model.addAttribute("patientTransferInForFeb",patientTransferInForFeb.size());
		model.addAttribute("patientTransferInForMarch",patientTransferInForMarch.size());
		model.addAttribute("patientTransferInForApril",patientTransferInForApril.size());
		model.addAttribute("patientTransferInForMay",patientTransferInForMay.size());
		model.addAttribute("patientTransferInForJune",patientTransferInForJune.size());
		
		model.addAttribute("patientTransferOutForJan",patientTransferOutForJan.size());
		model.addAttribute("patientTransferOutForFeb",patientTransferOutForFeb.size());
		model.addAttribute("patientTransferOutForMarch",patientTransferOutForMarch.size());
		model.addAttribute("patientTransferOutForApril",patientTransferOutForApril.size());
		model.addAttribute("patientTransferOutForMay",patientTransferOutForMay.size());
		model.addAttribute("patientTransferOutForJune",patientTransferOutForJune.size());
		
		
		
		model.addAttribute("patientProgramForJan",patientProgramForJan.size());
		model.addAttribute("patientProgramForFeb",patientProgramForFeb.size());
		model.addAttribute("patientProgramForMarch",patientProgramForMarch.size());
		model.addAttribute("patientProgramForApril",patientProgramForApril.size());
		model.addAttribute("patientProgramForMay",patientProgramForMay.size());
		model.addAttribute("patientProgramForJune",patientProgramForJune.size());
		
		
		model.addAttribute("patientTransferInForJan",patientTransferInForJan.size());
		model.addAttribute("patientTransferInForFeb",patientTransferInForFeb.size());
		model.addAttribute("patientTransferInForMarch",patientTransferInForMarch.size());
		model.addAttribute("patientTransferInForApril",patientTransferInForApril.size());
		model.addAttribute("patientTransferInForMay",patientTransferInForMay.size());
		model.addAttribute("patientTransferInForJune",patientTransferInForJune.size());
		
		model.addAttribute("patientTransferOutForJan",patientTransferOutForJan.size());
		model.addAttribute("patientTransferOutForFeb",patientTransferOutForFeb.size());
		model.addAttribute("patientTransferOutForMarch",patientTransferOutForMarch.size());
		model.addAttribute("patientTransferOutForApril",patientTransferOutForApril.size());
		model.addAttribute("patientTransferOutForMay",patientTransferOutForMay.size());
		model.addAttribute("patientTransferOutForJune",patientTransferOutForJune.size());
		
		model.addAttribute("totalCohortForJan",totalCohortForJan.size());
		model.addAttribute("totalCohortForFeb",totalCohortForFeb.size());
		model.addAttribute("totalCohortForMarch",totalCohortForMarch.size());
		model.addAttribute("totalCohortForApril",totalCohortForApril.size());
		model.addAttribute("totalCohortForMay",totalCohortForMay.size());
		model.addAttribute("totalCohortForJune",totalCohortForJune.size());
		
		model.addAttribute("maleCohortForJan",maleCohortForJan.size());
		model.addAttribute("maleCohortForFeb",maleCohortForFeb.size());
		model.addAttribute("maleCohortForMarch",maleCohortForMarch.size());
		model.addAttribute("maleCohortForApril",maleCohortForApril.size());
		model.addAttribute("maleCohortForMay",maleCohortForMay.size());
		model.addAttribute("maleCohortForJune",maleCohortForJune.size());
		
		model.addAttribute("femaleCohortForJan",femaleCohortForJan.size());
		model.addAttribute("femaleCohortForFeb",femaleCohortForFeb.size());
		model.addAttribute("femaleCohortForMarch",femaleCohortForMarch.size());
		model.addAttribute("femaleCohortForApril",femaleCohortForApril.size());
		model.addAttribute("femaleCohortForMay",femaleCohortForMay.size());
		model.addAttribute("femaleCohortForJune",femaleCohortForJune.size());
		
		model.addAttribute("cohortFor0_14AgeForJan",cohortFor0_14AgeForJan.size());
		model.addAttribute("cohortFor0_14AgeForFeb",cohortFor0_14AgeForFeb.size());
		model.addAttribute("cohortFor0_14AgeForMarch",cohortFor0_14AgeForMarch.size());
		model.addAttribute("cohortFor0_14AgeForApril",cohortFor0_14AgeForApril.size());
		model.addAttribute("cohortFor0_14AgeForMay",cohortFor0_14AgeForMay.size());
		model.addAttribute("cohortFor0_14AgeForJune",cohortFor0_14AgeForJune.size());
		
		model.addAttribute("cohortFor15_24AgeForJan",cohortFor15_24AgeForJan.size());
		model.addAttribute("cohortFor15_24AgeForFeb",cohortFor15_24AgeForFeb.size());
		model.addAttribute("cohortFor15_24AgeForMarch",cohortFor15_24AgeForMarch.size());
		model.addAttribute("cohortFor15_24AgeForApril",cohortFor15_24AgeForApril.size());
		model.addAttribute("cohortFor15_24AgeForMay",cohortFor15_24AgeForMay.size());
		model.addAttribute("cohortFor15_24AgeForJune",cohortFor15_24AgeForJune.size());
		
		model.addAttribute("cohortFor25_60AgeForJan",cohortFor25_60AgeForJan.size());
		model.addAttribute("cohortFor25_60AgeForFeb",cohortFor25_60AgeForFeb.size());
		model.addAttribute("cohortFor25_60AgeForMarch",cohortFor25_60AgeForMarch.size());
		model.addAttribute("cohortFor25_60AgeForApril",cohortFor25_60AgeForApril.size());
		model.addAttribute("cohortFor25_60AgeForMay",cohortFor25_60AgeForMay.size());
		model.addAttribute("cohortFor25_60AgeForJune",cohortFor25_60AgeForJune.size());
		
		model.addAttribute("noOfCohortAliveAndOnArtForJan",noOfCohortAliveAndOnArtForJan.size());
		model.addAttribute("noOfCohortAliveAndOnArtForFeb",noOfCohortAliveAndOnArtForFeb.size());
		model.addAttribute("noOfCohortAliveAndOnArtForMarch",noOfCohortAliveAndOnArtForMarch.size());
		model.addAttribute("noOfCohortAliveAndOnArtForApril",noOfCohortAliveAndOnArtForApril.size());
		model.addAttribute("noOfCohortAliveAndOnArtForMay",noOfCohortAliveAndOnArtForMay.size());
		model.addAttribute("noOfCohortAliveAndOnArtForJune",noOfCohortAliveAndOnArtForJune.size());
		
		model.addAttribute("noOfOriginalFirstLineRegimenForJan",noOfOriginalFirstLineRegimenForJan.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForFeb",noOfOriginalFirstLineRegimenForFeb.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForMarch",noOfOriginalFirstLineRegimenForMarch.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForApril",noOfOriginalFirstLineRegimenForApril.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForMay",noOfOriginalFirstLineRegimenForMay.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForJune",noOfOriginalFirstLineRegimenForJune.size());
		
		model.addAttribute("noOfAlternateFirstLineRegimenForJan",noOfAlternateFirstLineRegimenForJan.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForFeb",noOfAlternateFirstLineRegimenForFeb.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForMarch",noOfAlternateFirstLineRegimenForMarch.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForApril",noOfAlternateFirstLineRegimenForApril.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForMay",noOfAlternateFirstLineRegimenForMay.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForJune",noOfAlternateFirstLineRegimenForJune.size());
		
		model.addAttribute("noOfSecondLineRegimenForJan",noOfSecondLineRegimenForJan.size());
		model.addAttribute("noOfSecondLineRegimenForFeb",noOfSecondLineRegimenForFeb.size());
		model.addAttribute("noOfSecondLineRegimenForMarch",noOfSecondLineRegimenForMarch.size());
		model.addAttribute("noOfSecondLineRegimenForApril",noOfSecondLineRegimenForApril.size());
		model.addAttribute("noOfSecondLineRegimenForMay",noOfSecondLineRegimenForMay.size());
		model.addAttribute("noOfSecondLineRegimenForJune",noOfSecondLineRegimenForJune.size());
		
		model.addAttribute("noOfArtStoppedCohortForJan",noOfArtStoppedCohortForJan.size());
		model.addAttribute("noOfArtStoppedCohortForFeb",noOfArtStoppedCohortForFeb.size());
		model.addAttribute("noOfArtStoppedCohortForMarch",noOfArtStoppedCohortForMarch.size());
		model.addAttribute("noOfArtStoppedCohortForApril",noOfArtStoppedCohortForApril.size());
		model.addAttribute("noOfArtStoppedCohortForMay",noOfArtStoppedCohortForMay.size());
		model.addAttribute("noOfArtStoppedCohortForJune",noOfArtStoppedCohortForJune.size());
		
		model.addAttribute("noOfArtDiedCohortForJan",noOfArtDiedCohortForJan.size());
		model.addAttribute("noOfArtDiedCohortForFeb",noOfArtDiedCohortForFeb.size());
		model.addAttribute("noOfArtDiedCohortForMarch",noOfArtDiedCohortForMarch.size());
		model.addAttribute("noOfArtDiedCohortForApril",noOfArtDiedCohortForApril.size());
		model.addAttribute("noOfArtDiedCohortForMay",noOfArtDiedCohortForMay.size());
		model.addAttribute("noOfArtDiedCohortForJune",noOfArtDiedCohortForJune.size());
		
		model.addAttribute("noOfPatientLostToFollowUpForJan",noOfPatientLostToFollowUpForJan.size());
		model.addAttribute("noOfPatientLostToFollowUpForFeb",noOfPatientLostToFollowUpForFeb.size());
		model.addAttribute("noOfPatientLostToFollowUpForMarch",noOfPatientLostToFollowUpForMarch.size());
		model.addAttribute("noOfPatientLostToFollowUpForApril",noOfPatientLostToFollowUpForApril.size());
		model.addAttribute("noOfPatientLostToFollowUpForMay",noOfPatientLostToFollowUpForMay.size());
		model.addAttribute("noOfPatientLostToFollowUpForJune",noOfPatientLostToFollowUpForJune.size());
		
		model.addAttribute("noOfPatientWithCD4ForJan",noOfPatientWithCD4ForJan.size());
		model.addAttribute("noOfPatientWithCD4ForFeb",noOfPatientWithCD4ForFeb.size());
		model.addAttribute("noOfPatientWithCD4ForMarch",noOfPatientWithCD4ForMarch.size());
		model.addAttribute("noOfPatientWithCD4ForApril",noOfPatientWithCD4ForApril.size());
		model.addAttribute("noOfPatientWithCD4ForMay",noOfPatientWithCD4ForMay.size());
		model.addAttribute("noOfPatientWithCD4ForJune",noOfPatientWithCD4ForJune.size());
		
		model.addAttribute("noOfPatientNormalActivityForJan",noOfPatientNormalActivityForJan.size());
		model.addAttribute("noOfPatientNormalActivityForFeb",noOfPatientNormalActivityForFeb.size());
		model.addAttribute("noOfPatientNormalActivityForMarch",noOfPatientNormalActivityForMarch.size());
		model.addAttribute("noOfPatientNormalActivityForApril",noOfPatientNormalActivityForApril.size());
		model.addAttribute("noOfPatientNormalActivityForMay",noOfPatientNormalActivityForMay.size());
		model.addAttribute("noOfPatientNormalActivityForJune",noOfPatientNormalActivityForJune.size());
		
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForJan",noOfPatientBedriddenLessThanFiftyForJan.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForFeb",noOfPatientBedriddenLessThanFiftyForFeb.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForMarch",noOfPatientBedriddenLessThanFiftyForMarch.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForApril",noOfPatientBedriddenLessThanFiftyForApril.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForMay",noOfPatientBedriddenLessThanFiftyForMay.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForJune",noOfPatientBedriddenLessThanFiftyForJune.size());
		
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForJan",noOfPatientBedriddenMoreThanFiftyForJan.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForFeb",noOfPatientBedriddenMoreThanFiftyForFeb.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForMarch",noOfPatientBedriddenMoreThanFiftyForMarch.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForApril",noOfPatientBedriddenMoreThanFiftyForApril.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForMay",noOfPatientBedriddenMoreThanFiftyForMay.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForJune",noOfPatientBedriddenMoreThanFiftyForJune.size());
		
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForJan",noOfPatientPickedUpArvForSixMonthForJan.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForFeb",noOfPatientPickedUpArvForSixMonthForFeb.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForMarch",noOfPatientPickedUpArvForSixMonthForMarch.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForApril",noOfPatientPickedUpArvForSixMonthForApril.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForMay",noOfPatientPickedUpArvForSixMonthForMay.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForJune",noOfPatientPickedUpArvForSixMonthForJune.size());
		
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForJan",noOfPatientPickedUpArvForTwelveMonthForJan.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForFeb",noOfPatientPickedUpArvForTwelveMonthForFeb.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForMarch",noOfPatientPickedUpArvForTwelveMonthForMarch.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForApril",noOfPatientPickedUpArvForTwelveMonthForApril.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForMay",noOfPatientPickedUpArvForTwelveMonthForMay.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForJune",noOfPatientPickedUpArvForTwelveMonthForJune.size());	
	}
	
	if(halfYearly!=null && halfYearly.equals("Second Half")){
		
		String julyStartDate=year+"-"+"07"+"-"+"01";
		String julyEndDate=year+"-"+"07"+"-"+"31";
		
		String augustStartDate=year+"-"+"08"+"-"+"01";
		String augustEndDate=year+"-"+"08"+"-"+"31";
		
		String septemberStartDate=year+"-"+"09"+"-"+"01";
		String septemberEndDate=year+"-"+"09"+"-"+"30";
		
		String octoberStartDate=year+"-"+"10"+"-"+"01";
		String octoberEndDate=year+"-"+"10"+"-"+"31";
		
		String novemberStartDate=year+"-"+"11"+"-"+"01";
		String novemberEndDate=year+"-"+"11"+"-"+"30";
		
		String decemberStartDate=year+"-"+"12"+"-"+"01";
		String decemberEndDate=year+"-"+"12"+"-"+"31";
		
		Set<Patient> patientProgramForJuly=kenyaEmrService.getPatientProgram(program,julyStartDate,julyEndDate);
		Set<Patient> patientProgramForAugust=kenyaEmrService.getPatientProgram(program,augustStartDate,augustEndDate);
		Set<Patient> patientProgramForSeptember=kenyaEmrService.getPatientProgram(program,septemberStartDate,septemberEndDate);
		Set<Patient> patientProgramForOctober=kenyaEmrService.getPatientProgram(program,octoberStartDate,octoberEndDate);
		Set<Patient> patientProgramForNovember=kenyaEmrService.getPatientProgram(program,novemberStartDate,novemberEndDate);
		Set<Patient> patientProgramForDecember=kenyaEmrService.getPatientProgram(program,decemberStartDate,decemberEndDate);
		
		Set<Patient> patientTransferInForJuly=kenyaEmrService.getNoOfPatientTransferredIn(julyStartDate,julyEndDate);
		Set<Patient> patientTransferInForAugust=kenyaEmrService.getNoOfPatientTransferredIn(augustStartDate,augustEndDate);
		Set<Patient> patientTransferInForSeptember=kenyaEmrService.getNoOfPatientTransferredIn(septemberStartDate,septemberEndDate);
		Set<Patient> patientTransferInForOctober=kenyaEmrService.getNoOfPatientTransferredIn(octoberStartDate,octoberEndDate);
		Set<Patient> patientTransferInForNovember=kenyaEmrService.getNoOfPatientTransferredIn(novemberStartDate,novemberEndDate);
		Set<Patient> patientTransferInForDecember=kenyaEmrService.getNoOfPatientTransferredIn(decemberStartDate,decemberEndDate);
		
		Set<Patient> patientTransferOutForJuly=kenyaEmrService.getNoOfPatientTransferredOut(julyStartDate,julyEndDate);
		Set<Patient> patientTransferOutForAugust=kenyaEmrService.getNoOfPatientTransferredOut(augustStartDate,augustEndDate);
		Set<Patient> patientTransferOutForSeptember=kenyaEmrService.getNoOfPatientTransferredOut(septemberStartDate,septemberEndDate);
		Set<Patient> patientTransferOutForOctober=kenyaEmrService.getNoOfPatientTransferredOut(octoberStartDate,octoberEndDate);
		Set<Patient> patientTransferOutForNovember=kenyaEmrService.getNoOfPatientTransferredOut(novemberStartDate,novemberEndDate);
		Set<Patient> patientTransferOutForDecember=kenyaEmrService.getNoOfPatientTransferredOut(decemberStartDate,decemberEndDate);
		
		Set<Patient> totalCohortForJuly=kenyaEmrService.getTotalNoOfCohort(julyStartDate,julyEndDate);
		Set<Patient> totalCohortForAugust=kenyaEmrService.getTotalNoOfCohort(augustStartDate,augustEndDate);
		Set<Patient> totalCohortForSeptember=kenyaEmrService.getTotalNoOfCohort(septemberStartDate,septemberEndDate);
		Set<Patient> totalCohortForOctober=kenyaEmrService.getTotalNoOfCohort(octoberStartDate,octoberEndDate);
		Set<Patient> totalCohortForNovember=kenyaEmrService.getTotalNoOfCohort(novemberStartDate,novemberEndDate);
		Set<Patient> totalCohortForDecember=kenyaEmrService.getTotalNoOfCohort(decemberStartDate,decemberEndDate);
		
		Set<Patient> maleCohortForJuly=kenyaEmrService.getCohortBasedOnGender("M",julyStartDate,julyEndDate);
		Set<Patient> maleCohortForAugust=kenyaEmrService.getCohortBasedOnGender("M",augustStartDate,augustEndDate);
		Set<Patient> maleCohortForSeptember=kenyaEmrService.getCohortBasedOnGender("M",septemberStartDate,septemberEndDate);
		Set<Patient> maleCohortForOctober=kenyaEmrService.getCohortBasedOnGender("M",octoberStartDate,octoberEndDate);
		Set<Patient> maleCohortForNovember=kenyaEmrService.getCohortBasedOnGender("M",novemberStartDate,novemberEndDate);
		Set<Patient> maleCohortForDecember=kenyaEmrService.getCohortBasedOnGender("M",decemberStartDate,decemberEndDate);
		
		Set<Patient> femaleCohortForJuly=kenyaEmrService.getCohortBasedOnGender("F",julyStartDate,julyEndDate);
		Set<Patient> femaleCohortForAugust=kenyaEmrService.getCohortBasedOnGender("F",augustStartDate,augustEndDate);
		Set<Patient> femaleCohortForSeptember=kenyaEmrService.getCohortBasedOnGender("F",septemberStartDate,septemberEndDate);
		Set<Patient> femaleCohortForOctober=kenyaEmrService.getCohortBasedOnGender("F",octoberStartDate,octoberEndDate);
		Set<Patient> femaleCohortForNovember=kenyaEmrService.getCohortBasedOnGender("F",novemberStartDate,novemberEndDate);
		Set<Patient> femaleCohortForDecember=kenyaEmrService.getCohortBasedOnGender("F",decemberStartDate,decemberEndDate);
		
		Integer age1=0;
		Integer age2=14;
		Set<Patient> cohortFor0_14AgeForJuly=kenyaEmrService.getCohortBasedOnAge(age1,age2,julyStartDate,julyEndDate);
		Set<Patient> cohortFor0_14AgeForAugust=kenyaEmrService.getCohortBasedOnAge(age1,age2,augustStartDate,augustEndDate);
		Set<Patient> cohortFor0_14AgeForSeptember=kenyaEmrService.getCohortBasedOnAge(age1,age2,septemberStartDate,septemberEndDate);
		Set<Patient> cohortFor0_14AgeForOctober=kenyaEmrService.getCohortBasedOnAge(age1,age2,octoberStartDate,octoberEndDate);
		Set<Patient> cohortFor0_14AgeForNovember=kenyaEmrService.getCohortBasedOnAge(age1,age2,novemberStartDate,novemberEndDate);
		Set<Patient> cohortFor0_14AgeForDecember=kenyaEmrService.getCohortBasedOnAge(age1,age2,decemberStartDate,decemberEndDate);
		
		age1=15;
		age2=24;
		Set<Patient> cohortFor15_24AgeForJuly=kenyaEmrService.getCohortBasedOnAge(age1,age2,julyStartDate,julyEndDate);
		Set<Patient> cohortFor15_24AgeForAugust=kenyaEmrService.getCohortBasedOnAge(age1,age2,augustStartDate,augustEndDate);
		Set<Patient> cohortFor15_24AgeForSeptember=kenyaEmrService.getCohortBasedOnAge(age1,age2,septemberStartDate,septemberEndDate);
		Set<Patient> cohortFor15_24AgeForOctober=kenyaEmrService.getCohortBasedOnAge(age1,age2,octoberStartDate,octoberEndDate);
		Set<Patient> cohortFor15_24AgeForNovember=kenyaEmrService.getCohortBasedOnAge(age1,age2,novemberStartDate,novemberEndDate);
		Set<Patient> cohortFor15_24AgeForDecember=kenyaEmrService.getCohortBasedOnAge(age1,age2,decemberStartDate,decemberEndDate);
		
		age1=25;
		age2=60;
		Set<Patient> cohortFor25_60AgeForJuly=kenyaEmrService.getCohortBasedOnAge(age1,age2,julyStartDate,julyEndDate);
		Set<Patient> cohortFor25_60AgeForAugust=kenyaEmrService.getCohortBasedOnAge(age1,age2,augustStartDate,augustEndDate);
		Set<Patient> cohortFor25_60AgeForSeptember=kenyaEmrService.getCohortBasedOnAge(age1,age2,septemberStartDate,septemberEndDate);
		Set<Patient> cohortFor25_60AgeForOctober=kenyaEmrService.getCohortBasedOnAge(age1,age2,octoberStartDate,octoberEndDate);
		Set<Patient> cohortFor25_60AgeForNovember=kenyaEmrService.getCohortBasedOnAge(age1,age2,novemberStartDate,novemberEndDate);
		Set<Patient> cohortFor25_60AgeForDecember=kenyaEmrService.getCohortBasedOnAge(age1,age2,decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfCohortAliveAndOnArtForJuly=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,julyStartDate,julyEndDate);
		Set<Patient> noOfCohortAliveAndOnArtForAugust=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,augustStartDate,augustEndDate);
		Set<Patient> noOfCohortAliveAndOnArtForSeptember=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,septemberStartDate,septemberEndDate);
		Set<Patient> noOfCohortAliveAndOnArtForOctober=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,octoberStartDate,octoberEndDate);
		Set<Patient> noOfCohortAliveAndOnArtForNovember=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,novemberStartDate,novemberEndDate);
		Set<Patient> noOfCohortAliveAndOnArtForDecember=kenyaEmrService.getNoOfCohortAliveAndOnArt(program,decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfOriginalFirstLineRegimenForJuly=kenyaEmrService.getOriginalFirstLineRegimen(program,julyStartDate,julyEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForAugust=kenyaEmrService.getOriginalFirstLineRegimen(program,augustStartDate,augustEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForSeptember=kenyaEmrService.getOriginalFirstLineRegimen(program,septemberStartDate,septemberEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForOctober=kenyaEmrService.getOriginalFirstLineRegimen(program,octoberStartDate,octoberEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForNovember=kenyaEmrService.getOriginalFirstLineRegimen(program,novemberStartDate,novemberEndDate);
		Set<Patient> noOfOriginalFirstLineRegimenForDecember=kenyaEmrService.getOriginalFirstLineRegimen(program,decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfAlternateFirstLineRegimenForJuly=kenyaEmrService.getAlternateFirstLineRegimen(program,julyStartDate,julyEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForAugust=kenyaEmrService.getAlternateFirstLineRegimen(program,augustStartDate,augustEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForSeptember=kenyaEmrService.getAlternateFirstLineRegimen(program,septemberStartDate,septemberEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForOctober=kenyaEmrService.getAlternateFirstLineRegimen(program,octoberStartDate,octoberEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForNovember=kenyaEmrService.getAlternateFirstLineRegimen(program,novemberStartDate,novemberEndDate);
		Set<Patient> noOfAlternateFirstLineRegimenForDecember=kenyaEmrService.getAlternateFirstLineRegimen(program,decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfSecondLineRegimenForJuly=kenyaEmrService.getSecondLineRegimen(program,julyStartDate,julyEndDate);
		Set<Patient> noOfSecondLineRegimenForAugust=kenyaEmrService.getSecondLineRegimen(program,augustStartDate,augustEndDate);
		Set<Patient> noOfSecondLineRegimenForSeptember=kenyaEmrService.getSecondLineRegimen(program,septemberStartDate,septemberEndDate);
		Set<Patient> noOfSecondLineRegimenForOctober=kenyaEmrService.getSecondLineRegimen(program,octoberStartDate,octoberEndDate);
		Set<Patient> noOfSecondLineRegimenForNovember=kenyaEmrService.getSecondLineRegimen(program,novemberStartDate,novemberEndDate);
		Set<Patient> noOfSecondLineRegimenForDecember=kenyaEmrService.getSecondLineRegimen(program,decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfArtStoppedCohortForJuly=kenyaEmrService.getNoOfArtStoppedCohort(program,julyStartDate,julyEndDate);
		Set<Patient> noOfArtStoppedCohortForAugust=kenyaEmrService.getNoOfArtStoppedCohort(program,augustStartDate,augustEndDate);
		Set<Patient> noOfArtStoppedCohortForSeptember=kenyaEmrService.getNoOfArtStoppedCohort(program,septemberStartDate,septemberEndDate);
		Set<Patient> noOfArtStoppedCohortForOctober=kenyaEmrService.getNoOfArtStoppedCohort(program,octoberStartDate,octoberEndDate);
		Set<Patient> noOfArtStoppedCohortForNovember=kenyaEmrService.getNoOfArtStoppedCohort(program,novemberStartDate,novemberEndDate);
		Set<Patient> noOfArtStoppedCohortForForDecember=kenyaEmrService.getNoOfArtStoppedCohort(program,decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfArtDiedCohortForJuly=kenyaEmrService.getNoOfArtDiedCohort(program,julyStartDate,julyEndDate);
		Set<Patient> noOfArtDiedCohortForAugust=kenyaEmrService.getNoOfArtDiedCohort(program,augustStartDate,augustEndDate);
		Set<Patient> noOfArtDiedCohortForSeptember=kenyaEmrService.getNoOfArtDiedCohort(program,septemberStartDate,septemberEndDate);
		Set<Patient> noOfArtDiedCohortForOctober=kenyaEmrService.getNoOfArtDiedCohort(program,octoberStartDate,octoberEndDate);
		Set<Patient> noOfArtDiedCohortForNovember=kenyaEmrService.getNoOfArtDiedCohort(program,novemberStartDate,novemberEndDate);
		Set<Patient> noOfArtDiedCohortForDecember=kenyaEmrService.getNoOfArtDiedCohort(program,decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfPatientLostToFollowUpForJuly=kenyaEmrService.getNoOfPatientLostToFollowUp(julyStartDate,julyEndDate);
		Set<Patient> noOfPatientLostToFollowUpForAugust=kenyaEmrService.getNoOfPatientLostToFollowUp(augustStartDate,augustEndDate);
		Set<Patient> noOfPatientLostToFollowUpForSeptember=kenyaEmrService.getNoOfPatientLostToFollowUp(septemberStartDate,septemberEndDate);
		Set<Patient> noOfPatientLostToFollowUpForOctober=kenyaEmrService.getNoOfPatientLostToFollowUp(octoberStartDate,octoberEndDate);
		Set<Patient> noOfPatientLostToFollowUpForNovember=kenyaEmrService.getNoOfPatientLostToFollowUp(novemberStartDate,novemberEndDate);
		Set<Patient> noOfPatientLostToFollowUpForDecember=kenyaEmrService.getNoOfPatientLostToFollowUp(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientWithCD4ForJuly=kenyaEmrService.getNoOfPatientWithCD4(julyStartDate,julyEndDate);
		List<Obs> noOfPatientWithCD4ForAugust=kenyaEmrService.getNoOfPatientWithCD4(augustStartDate,augustEndDate);
		List<Obs> noOfPatientWithCD4ForSeptember=kenyaEmrService.getNoOfPatientWithCD4(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientWithCD4ForOctober=kenyaEmrService.getNoOfPatientWithCD4(octoberStartDate,octoberEndDate);
		List<Obs> noOfPatientWithCD4ForNovember=kenyaEmrService.getNoOfPatientWithCD4(novemberStartDate,novemberEndDate);
		List<Obs> noOfPatientWithCD4ForDecember=kenyaEmrService.getNoOfPatientWithCD4(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientNormalActivityForJuly=kenyaEmrService.getNoOfPatientNormalActivity(julyStartDate,julyEndDate);
		List<Obs> noOfPatientNormalActivityForAugust=kenyaEmrService.getNoOfPatientNormalActivity(augustStartDate,augustEndDate);
		List<Obs> noOfPatientNormalActivityForSeptember=kenyaEmrService.getNoOfPatientNormalActivity(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientNormalActivityForOctober=kenyaEmrService.getNoOfPatientNormalActivity(octoberStartDate,octoberEndDate);
		List<Obs> noOfPatientNormalActivityForNovember=kenyaEmrService.getNoOfPatientNormalActivity(novemberStartDate,novemberEndDate);
		List<Obs> noOfPatientNormalActivityForDecember=kenyaEmrService.getNoOfPatientNormalActivity(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientBedriddenLessThanFiftyForJuly=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(julyStartDate,julyEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForAugust=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(augustStartDate,augustEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForSeptember=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForOctober=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(octoberStartDate,octoberEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForNovember=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(novemberStartDate,novemberEndDate);
		List<Obs> noOfPatientBedriddenLessThanFiftyForDecember=kenyaEmrService.getNoOfPatientBedriddenLessThanFifty(decemberStartDate,decemberEndDate);
		
		List<Obs> noOfPatientBedriddenMoreThanFiftyForJuly=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(julyStartDate,julyEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForAugust=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(augustStartDate,augustEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForSeptember=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(septemberStartDate,septemberEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForOctober=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(octoberStartDate,octoberEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForNovember=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(novemberStartDate,novemberEndDate);
		List<Obs> noOfPatientBedriddenMoreThanFiftyForDecember=kenyaEmrService.getNoOfPatientBedriddenMoreThanFifty(decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfPatientPickedUpArvForSixMonthForJuly=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(julyStartDate,julyEndDate);
		Set<Patient> noOfPatientPickedUpArvForSixMonthForAugust=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(augustStartDate,augustEndDate);
		Set<Patient> noOfPatientPickedUpArvForSixMonthForSeptember=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(septemberStartDate,septemberEndDate);
		Set<Patient> noOfPatientPickedUpArvForSixMonthForOctober=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(octoberStartDate,octoberEndDate);
		Set<Patient> noOfPatientPickedUpArvForSixMonthForNovember=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(novemberStartDate,novemberEndDate);
		Set<Patient> noOfPatientPickedUpArvForSixMonthForDecember=kenyaEmrService.getNoOfPatientPickedUpArvForSixMonth(decemberStartDate,decemberEndDate);
		
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForJuly=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(julyStartDate,julyEndDate);
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForAugust=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(augustStartDate,augustEndDate);
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForSeptember=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(septemberStartDate,septemberEndDate);
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForOctober=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(octoberStartDate,octoberEndDate);
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForNovember=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(novemberStartDate,novemberEndDate);
		Set<Patient> noOfPatientPickedUpArvForTwelveMonthForDecember=kenyaEmrService.getNoOfPatientPickedUpArvForTwelveMonth(decemberStartDate,decemberEndDate);
		
		model.addAttribute("patientProgramForJuly",patientProgramForJuly.size());
		model.addAttribute("patientProgramForAugust",patientProgramForAugust.size());
		model.addAttribute("patientProgramForSeptember",patientProgramForSeptember.size());
		model.addAttribute("patientProgramForOctober",patientProgramForOctober.size());
		model.addAttribute("patientProgramForNovember",patientProgramForNovember.size());
		model.addAttribute("patientProgramForDecember",patientProgramForDecember.size());
	
		model.addAttribute("patientTransferInForJuly",patientTransferInForJuly.size());
		model.addAttribute("patientTransferInForAugust",patientTransferInForAugust.size());
		model.addAttribute("patientTransferInForSeptember",patientTransferInForSeptember.size());
		model.addAttribute("patientTransferInForOctober",patientTransferInForOctober.size());
		model.addAttribute("patientTransferInForNovember",patientTransferInForNovember.size());
		model.addAttribute("patientTransferInForDecember",patientTransferInForDecember.size());
		
		model.addAttribute("patientTransferOutForJuly",patientTransferOutForJuly.size());
		model.addAttribute("patientTransferOutForAugust",patientTransferOutForAugust.size());
		model.addAttribute("patientTransferOutForSeptember",patientTransferOutForSeptember.size());
		model.addAttribute("patientTransferOutForOctober",patientTransferOutForOctober.size());
		model.addAttribute("patientTransferOutForNovember",patientTransferOutForNovember.size());
		model.addAttribute("patientTransferOutForDecember",patientTransferOutForDecember.size());
		
		model.addAttribute("patientProgramForJuly",patientProgramForJuly.size());
		model.addAttribute("patientProgramForAugust",patientProgramForAugust.size());
		model.addAttribute("patientProgramForSeptember",patientProgramForSeptember.size());
		model.addAttribute("patientProgramForOctober",patientProgramForOctober.size());
		model.addAttribute("patientProgramForNovember",patientProgramForNovember.size());
		model.addAttribute("patientProgramForDecember",patientProgramForDecember.size());
		
		model.addAttribute("patientTransferInForJuly",patientTransferInForJuly.size());
		model.addAttribute("patientTransferInForAugust",patientTransferInForAugust.size());
		model.addAttribute("patientTransferInForSeptember",patientTransferInForSeptember.size());
		model.addAttribute("patientTransferInForOctober",patientTransferInForOctober.size());
		model.addAttribute("patientTransferInForNovember",patientTransferInForNovember.size());
		model.addAttribute("patientTransferInForDecember",patientTransferInForDecember.size());
		
		model.addAttribute("patientTransferOutForJuly",patientTransferOutForJuly.size());
		model.addAttribute("patientTransferOutForAugust",patientTransferOutForAugust.size());
		model.addAttribute("patientTransferOutForSeptember",patientTransferOutForSeptember.size());
		model.addAttribute("patientTransferOutForOctober",patientTransferOutForOctober.size());
		model.addAttribute("patientTransferOutForNovember",patientTransferOutForNovember.size());
		model.addAttribute("patientTransferOutForDecember",patientTransferOutForDecember.size());
		
		model.addAttribute("totalCohortForJuly",totalCohortForJuly.size());
		model.addAttribute("totalCohortForAugust",totalCohortForAugust.size());
		model.addAttribute("totalCohortForSeptember",totalCohortForSeptember.size());
		model.addAttribute("totalCohortForOctober",totalCohortForOctober.size());
		model.addAttribute("totalCohortForNovember",totalCohortForNovember.size());
		model.addAttribute("totalCohortForDecember",totalCohortForDecember.size());
		
		model.addAttribute("maleCohortForJuly",maleCohortForJuly.size());
		model.addAttribute("maleCohortForAugust",maleCohortForAugust.size());
		model.addAttribute("maleCohortForSeptember",maleCohortForSeptember.size());
		model.addAttribute("maleCohortForOctober",maleCohortForOctober.size());
		model.addAttribute("maleCohortForNovember",maleCohortForNovember.size());
		model.addAttribute("maleCohortForDecember",maleCohortForDecember.size());
		
		model.addAttribute("femaleCohortForJuly",femaleCohortForJuly.size());
		model.addAttribute("femaleCohortForAugust",femaleCohortForAugust.size());
		model.addAttribute("femaleCohortForSeptember",femaleCohortForSeptember.size());
		model.addAttribute("femaleCohortForOctober",femaleCohortForOctober.size());
		model.addAttribute("femaleCohortForNovember",femaleCohortForNovember.size());
		model.addAttribute("femaleCohortForDecember",femaleCohortForDecember.size());
		
		model.addAttribute("cohortFor0_14AgeForJuly",cohortFor0_14AgeForJuly.size());
		model.addAttribute("cohortFor0_14AgeForAugust",cohortFor0_14AgeForAugust.size());
		model.addAttribute("cohortFor0_14AgeForSeptember",cohortFor0_14AgeForSeptember.size());
		model.addAttribute("cohortFor0_14AgeForOctober",cohortFor0_14AgeForOctober.size());
		model.addAttribute("cohortFor0_14AgeForNovember",cohortFor0_14AgeForNovember.size());
		model.addAttribute("cohortFor0_14AgeForDecember",cohortFor0_14AgeForDecember.size());
		
		model.addAttribute("cohortFor15_24AgeForJuly",cohortFor15_24AgeForJuly.size());
		model.addAttribute("cohortFor15_24AgeForAugust",cohortFor15_24AgeForAugust.size());
		model.addAttribute("cohortFor15_24AgeForSeptember",cohortFor15_24AgeForSeptember.size());
		model.addAttribute("cohortFor15_24AgeForOctober",cohortFor15_24AgeForOctober.size());
		model.addAttribute("cohortFor15_24AgeForNovember",cohortFor15_24AgeForNovember.size());
		model.addAttribute("cohortFor15_24AgeForDecember",cohortFor15_24AgeForDecember.size());
		
		model.addAttribute("cohortFor25_60AgeForJuly",cohortFor25_60AgeForJuly.size());
		model.addAttribute("cohortFor25_60AgeForAugust",cohortFor25_60AgeForAugust.size());
		model.addAttribute("cohortFor25_60AgeForSeptember",cohortFor25_60AgeForSeptember.size());
		model.addAttribute("cohortFor25_60AgeForOctober",cohortFor25_60AgeForOctober.size());
		model.addAttribute("cohortFor25_60AgeForNovember",cohortFor25_60AgeForNovember.size());
		model.addAttribute("cohortFor25_60AgeForDecember",cohortFor25_60AgeForDecember.size());
		
		model.addAttribute("noOfCohortAliveAndOnArtForJuly",noOfCohortAliveAndOnArtForJuly.size());
		model.addAttribute("noOfCohortAliveAndOnArtForAugust",noOfCohortAliveAndOnArtForAugust.size());
		model.addAttribute("noOfCohortAliveAndOnArtForSeptember",noOfCohortAliveAndOnArtForSeptember.size());
		model.addAttribute("noOfCohortAliveAndOnArtForOctober",noOfCohortAliveAndOnArtForOctober.size());
		model.addAttribute("noOfCohortAliveAndOnArtNovember",noOfCohortAliveAndOnArtForNovember.size());
		model.addAttribute("noOfCohortAliveAndOnArtForDecember",noOfCohortAliveAndOnArtForDecember.size());
		
		model.addAttribute("noOfOriginalFirstLineRegimenForJuly",noOfOriginalFirstLineRegimenForJuly.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForAugust",noOfOriginalFirstLineRegimenForAugust.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForSeptember",noOfOriginalFirstLineRegimenForSeptember.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForOctober",noOfOriginalFirstLineRegimenForOctober.size());
		model.addAttribute("noOfOriginalFirstLineRegimenNovember",noOfOriginalFirstLineRegimenForNovember.size());
		model.addAttribute("noOfOriginalFirstLineRegimenForDecember",noOfOriginalFirstLineRegimenForDecember.size());
		
		model.addAttribute("noOfAlternateFirstLineRegimenForJuly",noOfAlternateFirstLineRegimenForJuly.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForAugust",noOfAlternateFirstLineRegimenForAugust.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForSeptember",noOfAlternateFirstLineRegimenForSeptember.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForOctober",noOfAlternateFirstLineRegimenForOctober.size());
		model.addAttribute("noOfAlternateFirstLineRegimenNovember",noOfAlternateFirstLineRegimenForNovember.size());
		model.addAttribute("noOfAlternateFirstLineRegimenForDecember",noOfAlternateFirstLineRegimenForDecember.size());
		
		model.addAttribute("noOfSecondLineRegimenForJuly",noOfSecondLineRegimenForJuly.size());
		model.addAttribute("noOfSecondLineRegimenForAugust",noOfSecondLineRegimenForAugust.size());
		model.addAttribute("noOfSecondLineRegimenForSeptember",noOfSecondLineRegimenForSeptember.size());
		model.addAttribute("noOfSecondLineRegimenForOctober",noOfSecondLineRegimenForOctober.size());
		model.addAttribute("noOfSecondLineRegimenNovember",noOfSecondLineRegimenForNovember.size());
		model.addAttribute("noOfSecondLineRegimenForDecember",noOfSecondLineRegimenForDecember.size());
		
		model.addAttribute("noOfArtStoppedCohortForJuly",noOfArtStoppedCohortForJuly.size());
		model.addAttribute("noOfArtStoppedCohortForAugust",noOfArtStoppedCohortForAugust.size());
		model.addAttribute("noOfArtStoppedCohortForSeptember",noOfArtStoppedCohortForSeptember.size());
		model.addAttribute("noOfArtStoppedCohortForOctober",noOfArtStoppedCohortForOctober.size());
		model.addAttribute("noOfArtStoppedCohortNovember",noOfArtStoppedCohortForNovember.size());
		model.addAttribute("noOfArtStoppedCohortForDecember",noOfArtStoppedCohortForNovember.size());
		
		model.addAttribute("noOfArtDiedCohortForJuly",noOfArtDiedCohortForJuly.size());
		model.addAttribute("noOfArtDiedCohortForAugust",noOfArtDiedCohortForAugust.size());
		model.addAttribute("noOfArtDiedCohortForSeptember",noOfArtDiedCohortForSeptember.size());
		model.addAttribute("noOfArtDiedCohortForOctober",noOfArtDiedCohortForOctober.size());
		model.addAttribute("noOfArtDiedCohortNovember",noOfArtDiedCohortForNovember.size());
		model.addAttribute("noOfArtDiedCohortForDecember",noOfArtDiedCohortForDecember.size());
		
		model.addAttribute("noOfPatientLostToFollowUpForJuly",noOfPatientLostToFollowUpForJuly.size());
		model.addAttribute("noOfPatientLostToFollowUpForAugust",noOfPatientLostToFollowUpForAugust.size());
		model.addAttribute("noOfPatientLostToFollowUpForSeptember",noOfPatientLostToFollowUpForSeptember.size());
		model.addAttribute("noOfPatientLostToFollowUpForOctober",noOfPatientLostToFollowUpForOctober.size());
		model.addAttribute("noOfPatientLostToFollowUpNovember",noOfPatientLostToFollowUpForNovember.size());
		model.addAttribute("noOfPatientLostToFollowUpForDecember",noOfPatientLostToFollowUpForDecember.size());
		
		model.addAttribute("noOfPatientWithCD4ForJuly",noOfPatientWithCD4ForJuly.size());
		model.addAttribute("noOfPatientWithCD4ForAugust",noOfPatientWithCD4ForAugust.size());
		model.addAttribute("noOfPatientWithCD4ForSeptember",noOfPatientWithCD4ForSeptember.size());
		model.addAttribute("noOfPatientWithCD4ForOctober",noOfPatientWithCD4ForOctober.size());
		model.addAttribute("noOfPatientWithCD4November",noOfPatientWithCD4ForNovember.size());
		model.addAttribute("noOfPatientWithCD4ForDecember",noOfPatientWithCD4ForDecember.size());
		
		model.addAttribute("noOfPatientNormalActivityForJuly",noOfPatientNormalActivityForJuly.size());
		model.addAttribute("noOfPatientNormalActivityForAugust",noOfPatientNormalActivityForAugust.size());
		model.addAttribute("noOfPatientNormalActivityForSeptember",noOfPatientNormalActivityForSeptember.size());
		model.addAttribute("noOfPatientNormalActivityForOctober",noOfPatientNormalActivityForOctober.size());
		model.addAttribute("noOfPatientNormalActivityNovember",noOfPatientNormalActivityForNovember.size());
		model.addAttribute("noOfPatientNormalActivityForDecember",noOfPatientNormalActivityForDecember.size());
		
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForJuly",noOfPatientBedriddenLessThanFiftyForJuly.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForAugust",noOfPatientBedriddenLessThanFiftyForAugust.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForSeptember",noOfPatientBedriddenLessThanFiftyForSeptember.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForOctober",noOfPatientBedriddenLessThanFiftyForOctober.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyNovember",noOfPatientBedriddenLessThanFiftyForNovember.size());
		model.addAttribute("noOfPatientBedriddenLessThanFiftyForDecember",noOfPatientBedriddenLessThanFiftyForDecember.size());
		
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForJuly",noOfPatientBedriddenMoreThanFiftyForJuly.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForAugust",noOfPatientBedriddenMoreThanFiftyForAugust.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForSeptember",noOfPatientBedriddenMoreThanFiftyForSeptember.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForOctober",noOfPatientBedriddenMoreThanFiftyForOctober.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyNovember",noOfPatientBedriddenMoreThanFiftyForNovember.size());
		model.addAttribute("noOfPatientBedriddenMoreThanFiftyForDecember",noOfPatientBedriddenMoreThanFiftyForDecember.size());
		
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForJuly",noOfPatientPickedUpArvForSixMonthForJuly.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForAugust",noOfPatientPickedUpArvForSixMonthForAugust.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForSeptember",noOfPatientPickedUpArvForSixMonthForSeptember.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForOctober",noOfPatientPickedUpArvForSixMonthForOctober.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthNovember",noOfPatientPickedUpArvForSixMonthForNovember.size());
		model.addAttribute("noOfPatientPickedUpArvForSixMonthForDecember",noOfPatientPickedUpArvForSixMonthForDecember.size());
		
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForJuly",noOfPatientPickedUpArvForTwelveMonthForJuly.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForAugust",noOfPatientPickedUpArvForTwelveMonthForAugust.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForSeptember",noOfPatientPickedUpArvForTwelveMonthForSeptember.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForOctober",noOfPatientPickedUpArvForTwelveMonthForOctober.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthNovember",noOfPatientPickedUpArvForTwelveMonthForNovember.size());
		model.addAttribute("noOfPatientPickedUpArvForTwelveMonthForDecember",noOfPatientPickedUpArvForTwelveMonthForDecember.size());
	 }
		
		model.addAttribute("year",year);
		model.addAttribute("halfYearly",halfYearly);
   }
}
