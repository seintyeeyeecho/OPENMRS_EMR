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
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Program;
import org.openmrs.api.ObsService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.Calculation;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.CalculationUtils;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyacore.calculation.PatientFlagCalculation;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastCd4CountCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastCd4PercentageCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastWhoStageCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LostToFollowUpIncludingTransferOutCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.Age;

/**
 * Calculates whether patients are eligible for ART
 */
public class EligibleForArtCalculation extends AbstractPatientCalculation implements PatientFlagCalculation {

	/**
	 * @see org.openmrs.module.kenyacore.calculation.PatientFlagCalculation#getFlagMessage()
	 */
	@Override
	public String getFlagMessage() {
		return "Eligible for ART";
	}

	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection,
	 *      java.util.Map,
	 *      org.openmrs.calculation.patient.PatientCalculationContext)
	 * @should calculate eligibility
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues, PatientCalculationContext context) {

		Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);

		Set<Integer> alive = Filters.alive(cohort, context);
		Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
		Concept tca = Dictionary.getConcept(Dictionary.RETURN_VISIT_DATE);

		// need to exclude those on ART already
		//Set<Integer> onArt = CalculationUtils.patientsThatPass(calculate(new OnArtCalculation(), cohort, context));
		Set<Integer> onArt = CalculationUtils.patientsThatPass(calculate(new CurrentArtRegimenCalculation(), cohort, context));
		Set<Integer> ltfu = CalculationUtils.patientsThatPass(calculate(new LostToFollowUpIncludingTransferOutCalculation(), cohort, context));

		// only exclude LTF within the reporting period
		CalculationResultMap nextAppointmentDate = Calculations.lastObsOnOrBefore(tca, context.getNow(), ltfu, context);
		Set<Integer> ltfuWithinPeriod = nextAppointmentDate.keySet();

		CalculationResultMap ages = Calculations.ages(cohort, context);

		CalculationResultMap lastWhoStage = calculate(new LastWhoStageCalculation(), cohort, context);
		// CalculationResultMap lastMsm = calculate(new MSMCalculation(),
		// cohort, context);
		CalculationResultMap lastCd4 = calculate(new LastCd4CountCalculation(), cohort, context);
		CalculationResultMap lastCd4Percent = calculate(new LastCd4PercentageCalculation(), cohort, context);

		CalculationResultMap ret = new CalculationResultMap();
		for (Integer patientId : cohort) {
			boolean eligible = false;
			if (onArt.contains(patientId) /*&& !inHivProgram.contains(patientId)*/){
				ret.put(patientId, new BooleanResult(eligible, this));
				continue;
			}
			int ageInMonths = ((Age) ages.get(patientId).getValue()).getFullMonths();
			Double cd4 = EmrCalculationUtils.numericObsResultForPatient(lastCd4, patientId);
			Double cd4Percent = EmrCalculationUtils.numericObsResultForPatient(lastCd4Percent, patientId);
			Integer whoStage = EmrUtils.whoStage(EmrCalculationUtils.codedObsResultForPatient(lastWhoStage, patientId));
			eligible = isEligible(ageInMonths, cd4, cd4Percent, whoStage);
			Patient patient = Context.getPatientService().getPatient(patientId);
			eligible = !eligible ? !isMsmOrSexWorker(patient) ? !isPwid(patient) ? !isFamilyHistoryRelationWith(patient) ? false : true : true : true : true;

			if (ltfuWithinPeriod.contains(patientId)) {
				eligible = false;
			}
			ret.put(patientId, new BooleanResult(eligible, this));
		}
		return ret;
	}

	private boolean isMsmOrSexWorker(Patient patient) {
		Concept riskFactor = Dictionary.getConcept(Dictionary.RISK_FACTOR_OF_HIV);
		Concept msm = Dictionary.getConcept(Dictionary.MSM);
		Concept sexWorker = Dictionary.getConcept(Dictionary.SEX_WORKER);
		ObsService obsService = Context.getObsService();
		List<Obs> allRiskFactor = obsService.getObservationsByPersonAndConcept(patient, riskFactor);
		for (Obs obs : allRiskFactor) {
			if (obs.getValueCoded().getConceptId() == msm.getConceptId()) {
				return true;
			}
			if (obs.getValueCoded().getConceptId() == sexWorker.getConceptId()) {
				return true;
			}
		}
		return false;
	}

	private boolean isPwid(Patient patient) {
		Concept pwid = Dictionary.getConcept(Dictionary.PWID);
		List<Obs> pwidObs = Context.getObsService().getObservationsByPersonAndConcept(patient, pwid);
		for (Obs obs : pwidObs) {
			if (obs.getValueCoded().getUuid().equals(Dictionary.YES)) {
				return true;
			}
		}
		return false;
	}

	private boolean isFamilyHistoryRelationWith(Patient patient) {
		Concept hivInfected = Dictionary.getConcept(Dictionary.HIV_INFECTED);
		List<Obs> relationshipObs = Context.getObsService().getObservationsByPersonAndConcept(patient, hivInfected);
		/*for (Obs obs : relationshipObs) {
			Obs spouse = getAnsweredObs(obs.getGroupMembers(), 163120); // Spouse
																		// Status
			Obs hivStatus = getAnsweredObs(obs.getGroupMembers(), 1169);
			if ((spouse != null && spouse.getValueCoded().getId() == 163121 || spouse.getValueCoded().getId() == 163122 || spouse.getValueCoded().getId() == 162123) && hivStatus.getId() == 664) {
				return true;
			}
		}*/
		boolean spouseStatus = false;
		Concept spouse = Context.getConceptService().getConcept(163120);
		List<Obs> spouseObs = Context.getObsService().getObservationsByPersonAndConcept(patient, spouse);
		for(Obs obs : spouseObs){
			if(obs.getValueCoded().getId()==163121 || obs.getValueCoded().getId()==163122 || obs.getValueCoded().getId() ==163123)
				spouseStatus = true;
		}
		boolean relationStatus = false;
		for(Obs obs : relationshipObs){
			if(obs.getValueCoded().getId()==664){
				relationStatus = true;
			}
		}
		
		return spouseStatus && relationStatus;
	}

	private Obs getAnsweredObs(Set<Obs> allObs, int conceptId) {
		for (Obs innerObs : allObs) {
			if (innerObs.getConcept().getId() == conceptId) {
				return innerObs;
			}
		}
		return null;
	}

	/**
	 * Checks eligibility based on age, CD4 and WHO stage
	 * 
	 * @param ageInMonths
	 *            the patient age in months
	 * @param cd4
	 *            the last CD4 count
	 * @param cd4Percent
	 *            the last CD4 percentage
	 * @param whoStage
	 *            the last WHO stage
	 * @return true if patient is eligible
	 */
	protected boolean isEligible(int ageInMonths, Double cd4, Double cd4Percent, Integer whoStage) {
		if (ageInMonths < 24) {
			return true;
		} else if (ageInMonths < 60) { // 24-59 months
			if (whoStage != null && (whoStage == 3 || whoStage == 4)) {
				return true;
			}
			if (cd4Percent != null && cd4Percent < 25) {
				return true;
			}
			if (cd4 != null && cd4 < 1000) {
				return true;
			}
		} else if (ageInMonths < 155) { // 5-12 years
			if (whoStage != null && (whoStage == 3 || whoStage == 4)) {
				return true;
			}
			if (cd4Percent != null && cd4Percent < 20) {
				return true;
			}
			if (cd4 != null && cd4 < 500) {
				return true;
			}
		} else { // 13+ years
			if (whoStage != null && (whoStage == 3 || whoStage == 4)) {
				return true;
			}
			if (cd4 != null && cd4 < 350) {
				return true;
			}
		}
		return false;
	}
}