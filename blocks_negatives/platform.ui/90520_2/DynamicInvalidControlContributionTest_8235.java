/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

/**
 * @since 3.5
 */
public class DynamicInvalidContributionTest extends DynamicTestCase {

	public DynamicInvalidContributionTest(String testName) {
		super(testName);
	}

	public void testInvalidMenuContribution() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		getBundle();
		fWorkbench.openWorkbenchWindow(window.getActivePage().getPerspective().getId(), null);
	}

	@Override
	protected String getExtensionId() {
		return "menu.invalid.menu.contribution";
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_MENUS;
	}

	@Override
	protected String getInstallLocation() {
		return "data/org.eclipse.newInvalidMenuContribution1";
	}

}
