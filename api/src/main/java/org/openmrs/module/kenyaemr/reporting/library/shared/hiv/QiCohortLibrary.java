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

package org.openmrs.module.kenyaemr.reporting.library.shared.hiv;

import org.openmrs.Concept;
import org.openmrs.Program;
import org.openmrs.api.PatientSetService;
import org.openmrs.api.PatientSetService.TimeModifier;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.cohort.definition.CalculationCohortDefinition;
import org.openmrs.module.kenyacore.report.cohort.definition.ObsInLastVisitCohortDefinition;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.library.hiv.InCareHasAtLeast2VisitsCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.PatientsWhoMeetCriteriaForNutritionalSupport;
import org.openmrs.module.kenyaemr.calculation.library.hiv.cqi.DiedInMonthOneOfReviewCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.cqi.HavingAtLeastOneVisitInEachQuoterCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.cqi.InCareInMonths4To6During6MonthsReviewPeriodCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.cqi.PatientsWithVLResultsAtLeastMonthAgoCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.cqi.PatientsWithVLResultsLessThanXValueCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.library.moh731.Moh731CohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.art.ArtCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.tb.TbCohortLibrary;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CodedObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.DateObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.SetComparator;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * Library of Quality Improvement cohorts for HIV care adult
 */
@Component
public class QiCohortLibrary {

	@Autowired
	private CommonCohortLibrary commonCohorts;

	@Autowired
	private ArtCohortLibrary artCohortLibrary;

	@Autowired
	private Moh731CohortLibrary moh731CohortLibrary;

	@Autowired
	private HivCohortLibrary hivCohortLibrary;

	@Autowired
	private PwpCohortLibrary pwpCohortLibrary;

	@Autowired
	private TbCohortLibrary tbCohortLibrary;


	/*public CohortDefinition onART() {
		SqlCohortDefinition onART = new SqlCohortDefinition("select distinct(patient_id) from orders where concept_id in (select concept_id from concept_set where concept_set =" + Dictionary.getConcept(Dictionary.ANTIRETROVIRAL_DRUGS).getConceptId() + ") and (discontinued_date is null or discontinued_date > :onDate) and start_date < :onDate and (auto_expire_date is null or auto_expire_date > :onDate) and voided = 0");
		onART.setName("onART");
		onART.addParameter(new Parameter("onDate", "On Date", Date.class));
		return onART;
	}*/

	public CohortDefinition hadNutritionalAssessmentAtLastVisit() {
		Concept weight = Dictionary.getConcept(Dictionary.WEIGHT_KG);
		Concept height = Dictionary.getConcept(Dictionary.HEIGHT_CM);
		Concept muac = Dictionary.getConcept(Dictionary.MUAC);

		ObsInLastVisitCohortDefinition hadWeight = new ObsInLastVisitCohortDefinition();
		hadWeight.setName("patients with weight obs in last visit Child");
		hadWeight.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		hadWeight.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		hadWeight.setQuestion(weight);

		ObsInLastVisitCohortDefinition hadHeight = new ObsInLastVisitCohortDefinition();
		hadHeight.setName("patients with height obs in last visit Child");
		hadHeight.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		hadHeight.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		hadHeight.setQuestion(height);

		ObsInLastVisitCohortDefinition hadMuac = new ObsInLastVisitCohortDefinition();
		hadMuac.setName("patients with MUAC obs in last visit Child");
		hadMuac.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		hadMuac.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		hadMuac.setQuestion(muac);

		CompositionCohortDefinition cd =new CompositionCohortDefinition();
		hadMuac.setName("patients with nutritional assessment in last visit Child");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		cd.addSearch("hadWeight", ReportUtils.map(hadWeight, "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("hadHeight", ReportUtils.map(hadHeight, "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("hadMuac", ReportUtils.map(hadMuac, "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("(hadWeight AND hadHeight AND adult) OR hadMuac");
		return cd;
	}

	/**
	 *HIV infected patients NOT on ART and has hiv clinical visit
	 * @return CohortDefinition
	 */
	public CohortDefinition hivInfectedAndNotOnARTAndHasHivClinicalVisit() {
		CompositionCohortDefinition cd =new CompositionCohortDefinition();
		cd.setName("Not on ART with at least one HIV clinical Visit");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("hasVisits", ReportUtils.map(hivCohortLibrary.hasHivVisit(), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		cd.addSearch("atLeastOneEligibilityCriteria", ReportUtils.map(artCohortLibrary.EligibleForArtExclusive(), "onDate=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.addSearch("onART", ReportUtils.map(artCohortLibrary.onArt(), "onDate=${onOrBefore-6m}"));
		cd.setCompositionString("(hasVisits and atLeastOneEligibilityCriteria and adult) and NOT onART");
		return cd;
	}

	/**
	 * Patients in care and has at least 2 visits
	 */
	public CohortDefinition inCareHasAtLeast2Visits() {
		CalculationCohortDefinition hasAtLeast2VisitsWithin3Months = new CalculationCohortDefinition(new InCareHasAtLeast2VisitsCalculation());
		hasAtLeast2VisitsWithin3Months.setName("patients in care and have at least 2 visits 3 months a part");
		hasAtLeast2VisitsWithin3Months.addParameter(new Parameter("onDate", "On Date", Date.class));

		CalculationCohortDefinition cdHasAtLeast1VisitInEveryQuoter = new CalculationCohortDefinition(new HavingAtLeastOneVisitInEachQuoterCalculation());
		cdHasAtLeast1VisitInEveryQuoter.setName("patients who have at least one visit in every quoter");
		cdHasAtLeast1VisitInEveryQuoter.addParameter(new Parameter("onDate", "On Date", Date.class));

		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Adult and in care");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.addSearch("has2VisitsIn3Months", ReportUtils.map(hasAtLeast2VisitsWithin3Months, "onDate=${onOrBefore}"));
		cd.addSearch("atLeast1VisitInEachQuoter", ReportUtils.map(cdHasAtLeast1VisitInEveryQuoter, "onDate=${onOrBefore}"));
		cd.setCompositionString("adult AND (has2VisitsIn3Months OR atLeast1VisitInEachQuoter)");
		return  cd;
	}

	/**
	 *Patients with a=clinical visits
	 * @return CohortDefinition
	 */
	public CohortDefinition clinicalVisit() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("in care and has a visit during 6 months review period");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("inCare", ReportUtils.map(moh731CohortLibrary.currentlyInCare(), "onDate=${onOrBefore}"));
		cd.addSearch("hasVisit", ReportUtils.map(hivCohortLibrary.hasHivVisit(), "onOrAfter=${onOrAfter-6m},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.addSearch("enrolledIn4To6MonthOfReviewPeriod", ReportUtils.map(enrolledIn4To6MonthOfReviewPeriod(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("inCare AND hasVisit AND adult AND NOT enrolledIn4To6MonthOfReviewPeriod");
		return cd;
	}

	/**
	 * Patients who are newly enrolled in 4th to 6th month
	 * @return CohortDefinition
	 */
	 public CohortDefinition enrolledIn4To6MonthOfReviewPeriod() {

		 CalculationCohortDefinition enrolled4To6Months = new CalculationCohortDefinition(new InCareInMonths4To6During6MonthsReviewPeriodCalculation());
		 enrolled4To6Months.setName("Patients in care at the 4th and 6th month during review");
		 enrolled4To6Months.addParameter(new Parameter("onDate", "On Date", Date.class));

		 CalculationCohortDefinition died = new CalculationCohortDefinition(new DiedInMonthOneOfReviewCalculation());
		 died.setName("Patients who died in the first month");
		 died.addParameter(new Parameter("onDate", "On Date", Date.class));

		 Concept REASON_FOR_PROGRAM_DISCONTINUATION = Dictionary.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION);
		 Concept TRANSFERRED_OUT = Dictionary.getConcept(Dictionary.TRANSFERRED_OUT);

		 CompositionCohortDefinition comp = new CompositionCohortDefinition();
		 comp.setName("Enrolled on 4th to 6th months, transferred and died in month 1");
		 comp.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		 comp.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		 comp.addSearch("enrolled4To6Months", ReportUtils.map(enrolled4To6Months, "onDate=${onOrBefore}"));
		 comp.addSearch("transfer", ReportUtils.map(commonCohorts.hasObs(REASON_FOR_PROGRAM_DISCONTINUATION, TRANSFERRED_OUT), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		 comp.addSearch("diedInMonth1", ReportUtils.map(died, "onDate=${onOrBefore}"));
		 comp.setCompositionString("enrolled4To6Months OR transfer OR diedInMonth1");
		 return comp;
	 }

	/**
	 * Patients with at least on viral load results during last 12 months duration
	 * @return CohortDefinition
	 */
	public CohortDefinition viralLoadResultsDuringLast12Months(int months) {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new PatientsWithVLResultsAtLeastMonthAgoCalculation());
		cd.setName("Patients With VL results within 12 months");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		cd.addCalculationParameter("months", months);
		return  cd;
	}

	/**
	 * Patients on ART for at least 12 months by the end of the review period
	 * Patients have at least one Viral Load (VL) results during the last 12 months
	 * @return cohort definition
	 */
	public CohortDefinition onARTatLeast12MonthsAndHaveAtLeastVLResultsDuringTheLast12Months() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("on ART at least 12 months and have VL during the last 12 months");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("onARTForAtLeast12Months", ReportUtils.map(artCohortLibrary.onArt(), "onDate=${onOrBefore-12m}"));
		cd.addSearch("viralLoadResults", ReportUtils.map(viralLoadResultsDuringLast12Months(12), "onDate=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("onARTForAtLeast12Months AND viralLoadResults AND adult");
		return cd;
	}

	/**
	 * This aggregate other indicators to be used in others as intersections
	 */
	public CohortDefinition hivMonitoringViralLoadNumAndDen() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Patients with the viral load");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("onARTatLeast12MonthsAndHaveAtLeastVLResultsDuringTheLast12Months", ReportUtils.map(onARTatLeast12MonthsAndHaveAtLeastVLResultsDuringTheLast12Months(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("onARTatLeast12MonthsAndHaveAtLeastOneVisitDuringTheLast6MonthsReview", ReportUtils.map(onARTatLeast12MonthsAndHaveAtLeastOneVisitDuringTheLast6MonthsReview(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("onARTatLeast12MonthsAndHaveAtLeastVLResultsDuringTheLast12Months AND onARTatLeast12MonthsAndHaveAtLeastOneVisitDuringTheLast6MonthsReview");
		return cd;
	}

	/**
	 * Number of HIV infected patients on ART 12 months ago
	 * Have atleast one clinical visit during the six months review period
	 * @return CohortDefinition
	 */
	public CohortDefinition onARTatLeast12MonthsAndHaveAtLeastOneVisitDuringTheLast6MonthsReview() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("on ART and have at least one clinical visit during the last 12 months");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("onARTForAtLeast12Months", ReportUtils.map(artCohortLibrary.onArt(), "onDate=${onOrBefore-12m}"));
		cd.addSearch("hasVisit", ReportUtils.map(hivCohortLibrary.hasHivVisit(), "onOrAfter=${onOrAfter-6m},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("onARTForAtLeast12Months AND hasVisit AND adult");
		return cd;
	}

	/**
	 * Number of patients on ART for at least 12 months
	 * VL < 1000 copies
	 * @return CohortDefinition
	 */
	public CohortDefinition onARTatLeast12MonthsAndVlLess1000() {
		CompositionCohortDefinition compositionCohortDefinition = new CompositionCohortDefinition();

		//find the <1000 copies of recent obs
		CalculationCohortDefinition cdVlLess1000 = new CalculationCohortDefinition( new PatientsWithVLResultsLessThanXValueCalculation());
		cdVlLess1000.setName("VL Less than 1000 Copies");
		cdVlLess1000.addParameter(new Parameter("onDate", "On Date", Date.class));
		cdVlLess1000.addCalculationParameter("months", 12 );
		cdVlLess1000.addCalculationParameter("threshold", 1000.0);


		compositionCohortDefinition.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		compositionCohortDefinition.setName("Number of patients on ART for at least 12 months and VL < 1000 copies");
		compositionCohortDefinition.addSearch("onARTForAtLeast12MonthsAdultAndHasVl", ReportUtils.map(onARTatLeast12MonthsAndHaveAtLeastVLResultsDuringTheLast12Months(), "onOrBefore=${onOrBefore}"));
		compositionCohortDefinition.addSearch("vlLess1000", ReportUtils.map(cdVlLess1000, "onDate=${onOrBefore}"));
		//compositionCohortDefinition.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));

		compositionCohortDefinition.setCompositionString("onARTForAtLeast12MonthsAdultAndHasVl AND vlLess1000");

		return compositionCohortDefinition;
	}

	/**
	 * Number of HIV infected patients currently NOT on ant TB treatment
	 * Patients have at least one HIV clinical visit during the 6 months review period
	 */
	public CohortDefinition hivInfectedNotOnTbTreatmentHaveAtLeastOneHivClinicalVisitDuring6Months() {
		//those patients on tb treatment
		DateObsCohortDefinition onTbTreatment = new DateObsCohortDefinition();
		onTbTreatment.setName("On Tb Treatment");
		onTbTreatment.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		onTbTreatment.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		onTbTreatment.setQuestion(Dictionary.getConcept(Dictionary.TUBERCULOSIS_DRUG_TREATMENT_START_DATE));
		onTbTreatment.setTimeModifier(TimeModifier.LAST);

		CompositionCohortDefinition compositionCohortDefinition = new CompositionCohortDefinition();
		compositionCohortDefinition.setName("in HIV has clinic and NOT in TB");
		compositionCohortDefinition.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		compositionCohortDefinition.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		compositionCohortDefinition.addSearch("inHivProgram", ReportUtils.map(hivCohortLibrary.enrolled()));
		compositionCohortDefinition.addSearch("onTbTreatment", ReportUtils.map(onTbTreatment, "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		compositionCohortDefinition.addSearch("hasVisit", ReportUtils.map(hivCohortLibrary.hasHivVisit(), "onOrAfter=${onOrAfter-6m},onOrBefore=${onOrBefore}"));
		compositionCohortDefinition.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		compositionCohortDefinition.setCompositionString("(inHivProgram AND hasVisit AND adult) AND NOT onTbTreatment");
		return compositionCohortDefinition;
	}

	/**
	 * Number of patients with negative TB screens
	 * Patients who  have NOt had IPT
	 */
	public CohortDefinition patientWithNegativeTbScreenWhoHaveNotHadIPT() {
		Concept tbDiseaseStatus = Dictionary.getConcept(Dictionary.TUBERCULOSIS_DISEASE_STATUS);
		Concept noSignsOrSymptoms = Dictionary.getConcept(Dictionary.NO_SIGNS_OR_SYMPTOMS_OF_DISEASE);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Screened for TB negative and NOT on IPT");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("negativeTB", ReportUtils.map(commonCohorts.hasObs(tbDiseaseStatus, noSignsOrSymptoms), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		cd.addSearch("onIPT", ReportUtils.map(onINHProphylaxis(), "onOrAfter=${onOrBefore-24m},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("(negativeTB AND adult) AND NOT onIPT");
		return cd;
	}

	/**
	 * Patients who are on INH
	 * Isoniazid dispensed
	 * Isoniazid medication
	 * INH prophylaxis
	 * @return CohortDefinition
	 */
	public CohortDefinition onINHProphylaxis() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		CodedObsCohortDefinition inhDispensed = new CodedObsCohortDefinition();
		inhDispensed.setName("isoniazid dispensed");
		inhDispensed.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		inhDispensed.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		inhDispensed.setTimeModifier(PatientSetService.TimeModifier.LAST);
		inhDispensed.setQuestion(Dictionary.getConcept(Dictionary.ISONIAZID_DISPENSED));
		inhDispensed.setValueList(Arrays.asList(Dictionary.getConcept(Dictionary.YES)));
		inhDispensed.setOperator(SetComparator.IN);

		cd.setName("Formulation of INH");
		cd.addSearch("inhDispensed", ReportUtils.map(inhDispensed, "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("inhMedication", ReportUtils.map(commonCohorts.medicationDispensed(Dictionary.getConcept(Dictionary.ISONIAZID)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("inhProphylaxis", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.PATIENT_REPORTED_CURRENT_TUBERCULOSIS_PROPHYLAXIS), Dictionary.getConcept(Dictionary.ISONIAZID_PROPHYLAXIS)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("inhDispensed OR inhMedication OR inhProphylaxis");
		return cd;
	}

	/**
	 * Number of patients who have NOT had IPT within the last 2 years
	 * Patients who have negative tb screen at last clinic visit during the 6 months review period
	 * @return CohortDefinition
	 */
	public CohortDefinition patientsWhoHaveHadNoIptWithinLast2YearsTbNegativeDuring6MonthsReviewPeriod() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		Concept tbDiseaseStatus = Dictionary.getConcept(Dictionary.TUBERCULOSIS_DISEASE_STATUS);
		Concept noSignsOrSymptoms = Dictionary.getConcept(Dictionary.NO_SIGNS_OR_SYMPTOMS_OF_DISEASE);
		cd.setName("Patients with No IPT within 2 years and Tb Negative");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("negativeTB", ReportUtils.map(commonCohorts.hasObs(tbDiseaseStatus, noSignsOrSymptoms), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("onIPT", ReportUtils.map(onINHProphylaxis(), "onOrAfter=${onOrBefore-24m},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("(negativeTB AND adult) AND NOT onIPT");
		return  cd;
	}

	/**
	 * Patients who meet criteria for nutritional support
	 * Patients who received nutritional support
	 * @return CohortDefinition
	 */
	public CohortDefinition patientsWhoMeetNutritionalSupportAtLastClinicVisit() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Patients who meet nutritional assessment and received nutritional support");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("meetNutritionCriteria", ReportUtils.map(patientsWhoMeetCriteriaForNutritionalSupport(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("lastClinicVisit", ReportUtils.map(hadNutritionalAssessmentAtLastVisit(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("meetNutritionCriteria AND lastClinicVisit AND adult");
		return  cd;
	}

	/**
	 * Patients who meet criteria for nutritional support
	 * BMI < 18.5 in adult or
	 * MUAC < 23 cm
	 */
	public CohortDefinition patientsWhoMeetCriteriaForNutritionalSupport() {
		CalculationCohortDefinition cdMeetCriteria = new CalculationCohortDefinition(new PatientsWhoMeetCriteriaForNutritionalSupport());
		cdMeetCriteria.setName("Patients who meet criteria for nutritional support");
		cdMeetCriteria.addParameter(new Parameter("onDate", "onDate", Date.class));

		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Meet nutritional criteria and adult");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("meetCriteria", ReportUtils.map(cdMeetCriteria, "onDate=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("meetCriteria AND adult");
		return cd;
	}

	/**
	 * patients who are HIV positive
	 * Partners having at least hiv known positive known status
	 * @return CohortDefinition
	 */
	public CohortDefinition hivPositivePatientsWhosePartnersAreHivPositive() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Hiv positive with a partner who is positive");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("inHivProgram", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV)), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("spousePartner", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.FAMILY_MEMBER), Dictionary.getConcept(Dictionary.PARTNER_OR_SPOUSE)), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("hivPositivePartner", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.SIGN_SYMPTOM_PRESENT), Dictionary.getConcept(Dictionary.YES)), "onOrAfter=${onOrBefore-12m},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("inHivProgram AND hivPositivePartner AND spousePartner AND adult");
		return  cd;

	}

	/**
	 *Hiv infected patients with at least one clinic visit during the six months review period
	 * Have at least one partner
	 * @return CohortDefinition
	 */
	public CohortDefinition hivPositivePatientsWithAtLeastOnePartner() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Hiv positive with at least one partner");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("inHivProgram", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV)), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("anyPartner", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.FAMILY_MEMBER), Dictionary.getConcept(Dictionary.PARTNER_OR_SPOUSE)), "onOrBefore=${onOrBefore}"));
		cd.addSearch("hasVisits", ReportUtils.map(hivCohortLibrary.hasHivVisit(), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("inHivProgram AND anyPartner AND hasVisits AND adult");
		return  cd;
	}

	/**
	 * patients who are HIV positive
	 * children having at least hiv known positive known status
	 * @return CohortDefinition
	 */
	public CohortDefinition hivPositivePatientsWhoseChildrenAreHivPositive() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Hiv infected and children Hiv Positive");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("inHivProgram", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV)), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("hasChild", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.FAMILY_MEMBER), Dictionary.getConcept(Dictionary.CHILD)), "onOrBefore=${onOrBefore}"));
		cd.addSearch("hivPositiveChild", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.SIGN_SYMPTOM_PRESENT), Dictionary.getConcept(Dictionary.YES)), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("inHivProgram AND hivPositiveChild AND hasChild AND adult");
		return  cd;

	}

	/**
	 *Hiv infected patients with at least one clinic visit during the six months review period
	 * Have at least one child
	 * @return CohortDefinition
	 */
	public CohortDefinition hivPositivePatientsWithAtLeastOneChildOrMinor() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Hiv positive with at least one child");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("inHivProgram", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV)), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("anyChild", ReportUtils.map(commonCohorts.hasObs(Dictionary.getConcept(Dictionary.FAMILY_MEMBER), Dictionary.getConcept(Dictionary.CHILD)), "onOrBefore=${onOrBefore}"));
		cd.addSearch("hasVisits", ReportUtils.map(hivCohortLibrary.hasHivVisit(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("inHivProgram AND anyChild AND hasVisits AND adult");
		return  cd;
	}

	/**
	 * Patients of HIV-infected non pregnant women aged 15-49 years
	 * Patients on modern contraceptives during the review period
	 * @return CohortDefinition
	 */
	public CohortDefinition nonPregnantWomen15To49YearsOnModernContraceptives() {
		Concept notPregnant = Dictionary.getConcept(Dictionary.NO);
		Concept pregnancyStatus = Dictionary.getConcept(Dictionary.PREGNANCY_STATUS);

		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Non pregnant 15 to 49 years on modern contraceptives");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("inHivProgram", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV)), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("notPregnant", ReportUtils.map(commonCohorts.hasObs(pregnancyStatus, notPregnant), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		cd.addSearch("above15Years", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.addSearch("below49Years", ReportUtils.map(commonCohorts.agedAtMost(49), "effectiveDate=${onOrBefore}"));
		cd.addSearch("onModernContraceptives", ReportUtils.map(pwpCohortLibrary.modernContraceptivesProvided(), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("inHivProgram AND notPregnant AND above15Years AND below49Years AND onModernContraceptives");
		return cd;
	}

	/**
	 * Patients who are hiv positive 15 to 49 years
	 * have at least one hiv clinical visit during the 6 months review period
	 * @return CohortDefinition
	 */
	public CohortDefinition nonPregnantWomen15To49YearsWithAtLeastOneHivClinicalVisit() {
		Concept notPregnant = Dictionary.getConcept(Dictionary.NO);
		Concept pregnancyStatus = Dictionary.getConcept(Dictionary.PREGNANCY_STATUS);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Non pregnant 15 to 49 years with at least one hiv clinical visit");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("inHivProgram", ReportUtils.map(commonCohorts.enrolled(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV)), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("notPregnant", ReportUtils.map(commonCohorts.hasObs(pregnancyStatus, notPregnant), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		cd.addSearch("above15Years", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.addSearch("below49Years", ReportUtils.map(commonCohorts.agedAtMost(49), "effectiveDate=${onOrBefore}"));
		cd.addSearch("hasVisits", ReportUtils.map(hivCohortLibrary.hasHivVisit(), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("inHivProgram AND notPregnant AND above15Years AND below49Years AND hasVisits");
		return cd;
	}

	/**
	 * Has cd4 results only adult
	 * @return CohortDefinition
	 */
	public CohortDefinition hasCD4ResultsAdult() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("hasCd4", ReportUtils.map(hivCohortLibrary.hasCd4Result(), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("hasCd4 AND adult");
		return cd;
	}

	/**
	 * Has visits only adult
	 * @return CohortDefinition
	 */
	public CohortDefinition hasHivVisitAdult() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("hasVisits", ReportUtils.map(hivCohortLibrary.hasHivVisit(), "onOrAfter=${onOrBefore-6m},onOrBefore=${onOrBefore}"));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.setCompositionString("hasVisits AND adult");
		return cd;
	}

	/**
	 * Adult patients screened for tb using ICF form
	 * @return CohortDefinition
	 */
	public CohortDefinition screenedForTBUsingICF() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("adult", ReportUtils.map(commonCohorts.agedAtLeast(15), "effectiveDate=${onOrBefore}"));
		cd.addSearch("screenedForTb", ReportUtils.map(tbCohortLibrary.screenedForTbUsingICF(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.setCompositionString("adult AND screenedForTb");
		return cd;
	}

}