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

package org.openmrs.module.kenyaemr.fragment.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.kenyacore.test.TestUtils;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link EmrUtilsFragmentController}
 */
public class EmrUtilsFragmentControllerTest extends BaseModuleWebContextSensitiveTest {

	private EmrUtilsFragmentController controller;

	@Autowired
	private KenyaUiUtils kenyaui;

	/**
	 * Setup each test
	 */
	@Before
	public void setup() throws Exception {
		controller = new EmrUtilsFragmentController();
	}

	/**
	 * @see EmrUtilsFragmentController#birthdateFromAge(Integer, java.util.Date, org.openmrs.module.kenyaui.KenyaUiUtils)
	 */
	@Test
	public void birthdateFromAge_shouldCalculateBirthdate() {
		/*
		Assert.assertThat(controller.birthdateFromAge(10, null, kenyaui), hasKey("birthdate"));
		Assert.assertThat(controller.birthdateFromAge(0, TestUtils.date(2000, 6, 1), kenyaui), hasEntry("birthdate", (Object) "2000-06-01"));
		Assert.assertThat(controller.birthdateFromAge(10, TestUtils.date(2000, 6, 1), kenyaui), hasEntry("birthdate", (Object) "1990-06-01"));
		*/
	}
}