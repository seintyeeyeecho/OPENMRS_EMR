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

package org.openmrs.module.kenyaemr.reporting.library.shared.common;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.Program;
import org.openmrs.api.PatientSetService;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.cohort.definition.CalculationCohortDefinition;
import org.openmrs.module.kenyacore.report.cohort.definition.DateObsValueBetweenCohortDefinition;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.Metadata;
import org.openmrs.module.kenyaemr.calculation.library.DeceasedPatientsCalculation;
import org.openmrs.module.kenyaemr.calculation.library.InProgramCalculation;
import org.openmrs.module.kenyaemr.calculation.library.RecordedDeceasedCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.OnAlternateFirstLineArtCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.reporting.library.shared.hiv.HivCohortLibrary;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CodedObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.EncounterCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.GenderCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.ProgramEnrollmentCohortDefinition;
import org.openmrs.module.reporting.common.SetComparator;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * Library of common cohort definitions
 */
@Component
public class CommonCohortLibrary {
	private CommonCohortLibrary commonCohorts;
	@Autowired
	private HivCohortLibrary hivCohortLibrary;

	/**
	 * Patients who are female
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition females() {
		GenderCohortDefinition cd = new GenderCohortDefinition();
		cd.setName("females");
		cd.setFemaleIncluded(true);
		cd.setUnknownGenderIncluded(true);
		return cd;
	}

	// TODO
	public CohortDefinition scaleA() {
		Concept performance = Dictionary.getConcept(Dictionary.PERFORMANCE);
		Concept scale = Dictionary.getConcept(Dictionary.PERFSCALE_A);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();

		cd.setName("performance scale A");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		cd.addSearch("givenScale", ReportUtils.map(hasObs(performance, scale), "onOrBefore=${onOrBefore}"));

		cd.setCompositionString("givenScale");

		return cd;

	}

	public CohortDefinition scaleB() {
		Concept performance = Dictionary.getConcept(Dictionary.PERFORMANCE);
		Concept scaleb = Dictionary.getConcept(Dictionary.PERFSCALE_B);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();

		cd.setName("performance scale B");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		cd.addSearch("givenScale", ReportUtils.map(hasObs(performance, scaleb), "onOrBefore=${onOrBefore}"));

		cd.setCompositionString("givenScale");

		return cd;

	}

	public CohortDefinition scaleC() {
		Concept performance = Dictionary.getConcept(Dictionary.PERFORMANCE);
		Concept scaleb = Dictionary.getConcept(Dictionary.PERFSCALE_C);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();

		cd.setName("performance scale C");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		cd.addSearch("givenScale", ReportUtils.map(hasObs(performance, scaleb), "onOrBefore=${onOrBefore}"));

		cd.setCompositionString("givenScale");

		return cd;

	}

	public CohortDefinition risk1() {
		Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
		Concept risk1 = Dictionary.getConcept(Dictionary.RISK_FACTOR_MSM);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();

		cd.setName("Risk1");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		cd.addSearch("givenRisk", ReportUtils.map(hasObs(risk, risk1), "onOrBefore=${onOrBefore}"));

		cd.setCompositionString("givenRisk");

		return cd;

	}

	public CohortDefinition risk2() {
		Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
		Concept risk2 = Dictionary.getConcept(Dictionary.RISK_FACTOR_SW);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();

		cd.setName("Risk2");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		// cd.addSearch("givenRisk", ReportUtils.map(hasObs(risk, risk2),
		// "onOrBefore=${onOrBefore}"));
		cd.addSearch("givenRisk", ReportUtils.map(hasLastestObs(risk, risk2), "onOrBefore=${onOrBefore}"));
		cd.setCompositionString("givenRisk");

		return cd;

	}

	public CohortDefinition risk3() {
		Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
		Concept risk3 = Dictionary.getConcept(Dictionary.RISK_FACTOR_HETRO);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();

		cd.setName("Risk3");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		// cd.addSearch("givenRisk", ReportUtils.map(hasObs(risk, risk3),
		// "onOrBefore=${onOrBefore}"));
		cd.addSearch("givenRisk", ReportUtils.map(hasLastestObs(risk, risk3), "onOrBefore=${onOrBefore}"));
		cd.setCompositionString("givenRisk");

		return cd;

	}

	public CohortDefinition risk4() {
		Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
		Concept risk4 = Dictionary.getConcept(Dictionary.RISK_FACTOR_IDU);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();

		cd.setName("Risk4");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		// cd.addSearch("givenRisk", ReportUtils.map(hasObs(risk, risk4),
		// "onOrBefore=${onOrBefore}"));
		cd.addSearch("givenRisk", ReportUtils.map(hasLastestObs(risk, risk4), "onOrBefore=${onOrBefore}"));
		cd.setCompositionString("givenRisk");

		return cd;

	}

	public CohortDefinition risk5() {
		Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
		Concept risk5 = Dictionary.getConcept(Dictionary.RISK_FACTOR_BLOODTRANS);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();

		cd.setName("Risk5");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		// cd.addSearch("givenRisk", ReportUtils.map(hasObs(risk, risk5),
		// "onOrBefore=${onOrBefore}"));
		cd.addSearch("givenRisk", ReportUtils.map(hasLastestObs(risk, risk5), "onOrBefore=${onOrBefore}"));
		cd.setCompositionString("givenRisk");

		return cd;

	}

	public CohortDefinition risk6() {
		Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
		Concept risk6 = Dictionary.getConcept(Dictionary.RISK_FACTOR_MOTHERTOCHILD);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();

		cd.setName("Risk6");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		// cd.addSearch("givenRisk", ReportUtils.map(hasObs(risk, risk6),
		// "onOrBefore=${onOrBefore}"));
		cd.addSearch("givenRisk", ReportUtils.map(hasLastestObs(risk, risk6), "onOrBefore=${onOrBefore}"));
		cd.setCompositionString("givenRisk");

		return cd;

	}

	public CohortDefinition risk7() {
		Concept risk = Dictionary.getConcept(Dictionary.HIV_RISK_FACTOR);
		Concept risk7 = Dictionary.getConcept(Dictionary.RISK_FACTOR_UNKNOWN);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();

		cd.setName("Risk7");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));

		// cd.addSearch("givenRisk", ReportUtils.map(hasObs(risk, risk7),
		// "onOrBefore=${onOrBefore}"));
		cd.addSearch("givenRisk", ReportUtils.map(hasLastestObs(risk, risk7), "onOrBefore=${onOrBefore}"));

		cd.setCompositionString("givenRisk");

		return cd;

	}

	/**
	 * Patients who are male
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition males() {
		GenderCohortDefinition cd = new GenderCohortDefinition();
		cd.setName("male");
		cd.setUnknownGenderIncluded(true);
		cd.setMaleIncluded(true);
		return cd;
	}

	public CohortDefinition adult() {
		AgeCohortDefinition ageCohort = new AgeCohortDefinition();
		ageCohort.setName("Just a test");
		ageCohort.setUnknownAgeIncluded(false);
		ageCohort.setMinAge(15);
		return ageCohort;
	}

	/**
	 * Patients who at most maxAge years old on ${effectiveDate}
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition agedAtMost(int maxAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged at most " + maxAge);
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMaxAge(maxAge);
		return cd;
	}

	/**
	 * Patients who are at least minAge years old on ${effectiveDate}
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition agedAtLeast(int minAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged at least " + minAge);
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMinAge(minAge);
		return cd;
	}

	/**
	 * Patients who are female and at least 18 years old on ${effectiveDate}
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition femalesAgedAtLeast18() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("females aged at least 18");
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.addSearch("females", ReportUtils.map(females()));
		cd.addSearch("agedAtLeast18", ReportUtils.map(agedAtLeast(18), "effectiveDate=${effectiveDate}"));
		cd.setCompositionString("females AND agedAtLeast18");
		return cd;
	}

	/**
	 * Patients who have an encounter between ${onOrAfter} and ${onOrBefore}
	 * 
	 * @param types
	 *            the encounter types
	 * @return the cohort definition
	 */
	public CohortDefinition hasEncounter(EncounterType... types) {
		EncounterCohortDefinition cd = new EncounterCohortDefinition();
		cd.setName("has encounter between dates");
		cd.setTimeQualifier(TimeQualifier.ANY);
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		if (types.length > 0) {
			cd.setEncounterTypeList(Arrays.asList(types));
		}
		return cd;
	}

	/**
	 * Patients who have an obs between ${onOrAfter} and ${onOrBefore}
	 * 
	 * @param question
	 *            the question concept
	 * @param answers
	 *            the answers to include
	 * @return the cohort definition
	 */
	public CohortDefinition hasObs(Concept question, Concept... answers) {
		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.setName("has obs between dates");
		cd.setQuestion(question);

		cd.setOperator(SetComparator.IN);
		cd.setTimeModifier(PatientSetService.TimeModifier.ANY);
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		if (answers.length > 0) {
			cd.setValueList(Arrays.asList(answers));
		}
		return cd;
	}

	public CohortDefinition hasLastestObs(Concept question, Concept... answers) {
		/*
		 * CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		 * cd.setName("has obs between dates"); cd.setQuestion(question);
		 * 
		 * cd.setOperator(SetComparator.IN);
		 * cd.setTimeModifier(PatientSetService.TimeModifier.LAST);
		 * cd.addParameter(new Parameter("onOrBefore", "Before Date",
		 * Date.class)); cd.addParameter(new Parameter("onOrAfter",
		 * "After Date", Date.class)); if (answers.length > 0) {
		 * cd.setValueList(Arrays.asList(answers)); } return cd;
		 */
		return hasObs(question, answers);
	}

	/**
	 * Patients who transferred in between ${onOrAfter} and ${onOrBefore}
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition transferredIn() {
		Concept transferInDate = Dictionary.getConcept(Dictionary.TRANSFER_IN_DATE);

		DateObsValueBetweenCohortDefinition cd = new DateObsValueBetweenCohortDefinition();
		cd.setName("transferred in between dates");
		cd.setQuestion(transferInDate);
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		return cd;
	}

	/**
	 * Patients who transferred in between ${onOrAfter} and ${onOrBefore}
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition transferredOut() {
		Concept reasonForDiscontinue = Dictionary.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION);
		Concept transferredOut = Dictionary.getConcept(Dictionary.TRANSFERRED_OUT);

		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.setName("transferred out between dates");
		cd.setTimeModifier(PatientSetService.TimeModifier.ANY);
		cd.setQuestion(reasonForDiscontinue);
		cd.setOperator(SetComparator.IN);
		cd.setValueList(Collections.singletonList(transferredOut));
		return cd;
	}

	/**
	 * Patients who were enrolled on the given programs between
	 * ${enrolledOnOrAfter} and ${enrolledOnOrBefore}
	 * 
	 * @param programs
	 *            the programs
	 * @return the cohort definition
	 */
	public CohortDefinition enrolled(Program... programs) {
		ProgramEnrollmentCohortDefinition cd = new ProgramEnrollmentCohortDefinition();
		cd.setName("enrolled in program between dates");
		cd.addParameter(new Parameter("enrolledOnOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("enrolledOnOrBefore", "Before Date", Date.class));
		if (programs.length > 0) {
			cd.setPrograms(Arrays.asList(programs));

		}
		return cd;
	}

	/**
	 * Patients who were enrolled on the given programs (excluding transfers)
	 * between ${onOrAfter} and ${onOrBefore}
	 * 
	 * @param programs
	 *            the programs
	 * @return the cohort definition
	 */
	public CohortDefinition enrolledExcludingTransfers(Program... programs) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("enrolled excluding transfers in program between dates");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(programs), "enrolledOnOrAfter=${onOrAfter},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("transferIn", ReportUtils.map(transferredIn(), "onOrBefore=${onOrBefore}"));
		cd.addSearch("completeProgram", ReportUtils.map(compltedProgram(), "completedOnOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND NOT (transferIn OR completeProgram)");
		return cd;
	}

	public CohortDefinition receiveART(Program programs) {
		Concept tbPatients = Dictionary.getConcept(Dictionary.TB_PATIENT);
		Concept tbPatientsstatus = Dictionary.getConcept(Dictionary.TB_Rx);
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Patients HIV positive TB patients who have received ART");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addSearch("dead", ReportUtils.map(hivCohortLibrary.reasonOfoutcome(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("transferout", ReportUtils.map(hivCohortLibrary.reasonOfoutcometransfer(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("lostmissing", ReportUtils.map(hivCohortLibrary.reasonOfoutcomeMissing(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));

		cd.addSearch("givenDrugs", ReportUtils.map(hasObs(tbPatients, tbPatientsstatus), "onOrBefore=${onOrBefore}"));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(programs), "enrolledOnOrAfter=${onOrAfter-1m},enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("completeProgram", ReportUtils.map(compltedProgram(MetadataUtils.existing(Program.class, Metadata.Program.ART)), "completedOnOrBefore=${onOrBefore}"));
		cd.addSearch("completedProgram", ReportUtils.map(compltedProgram(), "completedOnOrBefore=${onOrBefore}"));

		cd.setCompositionString("givenDrugs AND enrolled AND NOT(completeProgram OR completedProgram) AND NOT (dead OR transferout OR lostmissing)");

		return cd;
	}

	/**
	 * Patients who were enrolled on the given programs (excluding transfers) on
	 * ${onOrBefore}
	 * 
	 * @param programs
	 *            the programs
	 * @return the cohort definition
	 */
	public CohortDefinition enrolledExcludingTransfersOnDate(Program... programs) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("enrolled excluding transfers in program on date in this facility");
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addSearch("enrolled", ReportUtils.map(enrolled(programs), "enrolledOnOrBefore=${onOrBefore}"));
		cd.addSearch("transferIn", ReportUtils.map(transferredIn(), "onOrBefore=${onOrBefore}"));
		cd.setCompositionString("enrolled AND NOT transferIn");
		return cd;

	}

	/**
	 * Patients who are pregnant on ${onDate}
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition pregnant() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new OnAlternateFirstLineArtCalculation());
		cd.setName("pregnant on date");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		return cd;
	}

	/**
	 * Patients who are in the specified program on ${onDate}
	 * 
	 * @param program
	 *            the program
	 * @return
	 */
	public CohortDefinition inProgram(Program program) {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new InProgramCalculation());
		cd.setName("in " + program.getName() + " on date");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		cd.addCalculationParameter("program", program);
		return cd;
	}

	/**
	 * Patients who were dispensed the given medications between ${onOrAfter}
	 * and ${onOrBefore}
	 * 
	 * @param concepts
	 *            the drug concepts
	 * @return the cohort definition
	 */
	public CohortDefinition medicationDispensed(Concept... concepts) {
		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.setName("dispensed medication between");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.setTimeModifier(PatientSetService.TimeModifier.ANY);
		cd.setQuestion(Dictionary.getConcept(Dictionary.MEDICATION_ORDERS));
		cd.setValueList(Arrays.asList(concepts));
		cd.setOperator(SetComparator.IN);
		return cd;
	}

	/**
	 * Patients who completed program ${onOrAfter} and ${onOrBefore}
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition compltedProgram() {
		ProgramEnrollmentCohortDefinition cd = new ProgramEnrollmentCohortDefinition();
		cd.setName("Those patients who completed program on date");
		cd.addParameter(new Parameter("completedOnOrBefore", "Complete Date", Date.class));
		cd.setPrograms(Arrays.asList(MetadataUtils.existing(Program.class, HivMetadata._Program.HIV)));
		return cd;
	}

	public CohortDefinition compltedProgram(Program program) {
		ProgramEnrollmentCohortDefinition cd = new ProgramEnrollmentCohortDefinition();
		cd.setName("Those patients who completed program on date");
		cd.addParameter(new Parameter("completedOnOrBefore", "Complete Date", Date.class));
		cd.addParameter(new Parameter("completedOnOrAfter", "Complete Date", Date.class));
		cd.setPrograms(Arrays.asList(program));
		return cd;
	}

	/**
	 * Patients who are Deceased
	 * 
	 * @return the cohort definition
	 */
	public CohortDefinition deceasedPatients() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new DeceasedPatientsCalculation());
		cd.setName("deceases patients on date");
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		return cd;
	}

	// @Author Soe Min Htike
	//TODO
	public CohortDefinition artOutComePatient() {
		CompositionCohortDefinition definition = new CompositionCohortDefinition();
		definition.setName("Patient who has art out come");
		Concept lostToFollowUp = Dictionary.getConcept(Dictionary.LOST_MISSING_FOLLOW);
		Concept transferOut = Dictionary.getConcept(Dictionary.TRANSFERRED_OUT);
		Concept died = Dictionary.getConcept(Dictionary.DIED);
		Concept artOutcomeReason = Dictionary.getConcept(Dictionary.REASON_FOR_PROGRAM_DISCONTINUATION);
		//definition.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		definition.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		definition.addSearch("artOutCome", ReportUtils.map(hasObs(artOutcomeReason, lostToFollowUp, transferOut, died), "onOrBefore=${onOrBefore}"));
		definition.setCompositionString("artOutCome");
		return definition;
	}

	/**
	 * Patients who ahve been marked as dead in discontinuation forms but NOT
	 * YET deceased
	 * 
	 * @return cohort definition
	 */
	public CohortDefinition markedAsDeadButNotDeceased() {
		CalculationCohortDefinition cd = new CalculationCohortDefinition(new RecordedDeceasedCalculation());
		cd.setName("marked as dead patients on date");
		cd.addParameter(new Parameter("onDate", "On Date", Date.class));
		return cd;
	}
}