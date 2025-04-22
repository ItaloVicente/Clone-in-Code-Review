/*******************************************************************************
 * Copyright (c) 2008, 2015 Oakland Software Incorporated and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Oakland Software Incorporated - initial API and implementation
 *     IBM Corporation - fixed dead code warning
 *     Thibault Le Ouay <thibaultleouay@gmail.com> - Bug 457870
 *******************************************************************************/
package org.eclipse.ui.tests.navigator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.junit.Test;

public class ActivityTest extends NavigatorTestBase {

	public ActivityTest() {
		_navigatorInstanceId = TEST_VIEWER;
	}

	private static final boolean DEBUG = false;

	protected static final boolean USE_NEW_MENU = true;

	@Test
	public void testCategoryWizard() {

		IStructuredSelection sel = new StructuredSelection(_project);
		_viewer.setSelection(sel);


		IWorkbenchActivitySupport actSupport = PlatformUI.getWorkbench()
				.getActivitySupport();
		Set ids = new HashSet();
		ids = actSupport.getActivityManager().getEnabledActivityIds();

		if (DEBUG)
			System.out.println("enabled before: " + ids);

		assertFalse(verifyMenu(sel, "Test CNF Activity", USE_NEW_MENU));
		assertFalse(verifyMenu(sel, "org.eclipse.ui.tests.navigator.activityTest1", !USE_NEW_MENU));

		Set newIds = new HashSet();
		newIds.addAll(ids);
		newIds.add(TEST_ACTIVITY);
		actSupport.setEnabledActivityIds(newIds);

		ids = actSupport.getActivityManager().getEnabledActivityIds();
		if (DEBUG)
			System.out.println("enabled now: " + ids);

		assertTrue(verifyMenu(sel, "Test CNF Activity", USE_NEW_MENU));
		assertTrue(verifyMenu(sel, "org.eclipse.ui.tests.navigator.activityTest1", !USE_NEW_MENU));
	}


	@Test
	public void testProviderFilter() {

		IStructuredSelection sel = new StructuredSelection(_project);
		_viewer.setSelection(sel);


		IWorkbenchActivitySupport actSupport = PlatformUI.getWorkbench()
				.getActivitySupport();
		Set ids = new HashSet();
		ids = actSupport.getActivityManager().getEnabledActivityIds();

		if (DEBUG)
			System.out.println("enabled before: " + ids);

		assertFalse(verifyMenu(sel, "org.eclipse.ui.tests.navigator.activityProviderTest", !USE_NEW_MENU));

		Set newIds = new HashSet();
		newIds.addAll(ids);
		newIds.add(TEST_ACTIVITY_PROVIDER);
		actSupport.setEnabledActivityIds(newIds);

		ids = actSupport.getActivityManager().getEnabledActivityIds();
		if (DEBUG)
			System.out.println("enabled now: " + ids);

		assertTrue(verifyMenu(sel, "org.eclipse.ui.tests.navigator.activityProviderTest", !USE_NEW_MENU));

	}



}
