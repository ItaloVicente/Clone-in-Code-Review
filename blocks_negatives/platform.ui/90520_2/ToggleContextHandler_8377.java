/******************************************************************************
 * Copyright (c) 2008 Versant Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Remy Chi Jian Suen (Versant Corporation) - initial API and implementation
 ******************************************************************************/
package org.eclipse.ui.tests.menus;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.ShowViewMenu;
import org.eclipse.ui.tests.harness.util.UITestCase;

/**
 * @since 3.5
 */
public class ShowViewMenuTest extends UITestCase {

	private IWorkbenchWindow workbenchWindow;

	public ShowViewMenuTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		workbenchWindow = openTestWindow();
	}

	/***********************************
	 * Tests for Bug 56368 starts here *
	 ***********************************/

	public void testMenuOnlyHasShowViewAction() {
		Menu swtMenu = new Menu(workbenchWindow.getShell());
		ShowViewMenu showViewMenu = new ShowViewMenu(workbenchWindow, "id"); //$NON-NLS-1$
		showViewMenu.fill(swtMenu, 0);

		assertEquals("Only the 'Other...' action should be available", 1, swtMenu.getItemCount());
	}

	public void testFastViewMenuVariantOnlyHasShowViewAction() {
		Menu swtMenu = new Menu(workbenchWindow.getShell());
		ShowViewMenu showViewMenu = new ShowViewMenu(workbenchWindow,
				"id", true); //$NON-NLS-1$
		showViewMenu.fill(swtMenu, 0);

		assertEquals("Only the 'Other...' action should be available", 1, swtMenu.getItemCount());
	}

	/*********************************
	 * Tests for Bug 56368 ends here *
	 *********************************/

}
