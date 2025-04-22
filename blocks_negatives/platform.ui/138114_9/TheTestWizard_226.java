/*******************************************************************************
 * Copyright (c) 2008, 2014 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.jface.tests.wizards;

import junit.framework.TestCase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class ButtonAlignmentTest extends TestCase {

	private TheTestWizard wizard;
	private TheTestWizardDialog dialog;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Display.getDefault();
	}

	@Override
	protected void tearDown() throws Exception {
		if (dialog != null && dialog.getShell() != null
				&& !dialog.getShell().isDisposed()) {
			dialog.close();
		}
		super.tearDown();
	}

	public ButtonAlignmentTest() {
		super("ButtonAlignmentTest");
	}

	public void testButtonAlignment() {
		wizard = new TheTestWizard();
		dialog = new TheTestWizardDialog(null, wizard);
		dialog.create();
		dialog.open();

		Composite parent = dialog.getFinishedButton().getParent();
		Control[] children = parent.getChildren();
		assertEquals(
				"There should be three children, a composite for back/next buttons, the finish button, and the cancel button", //$NON-NLS-1$
				3, children.length);

		assertTrue(children[0] instanceof Composite);
		Composite backNextParent = (Composite) children[0];

		Control[] backNextChildren = backNextParent.getChildren();
		assertEquals("Back button should be the first button", dialog //$NON-NLS-1$
				.getBackButton(), backNextChildren[0]);
		assertEquals("Next button should be the second button", dialog //$NON-NLS-1$
				.getNextButton(), backNextChildren[1]);

		int finishIndex = parent.getDisplay().getDismissalAlignment() == SWT.LEFT ? 1
				: 2;
		int cancelIndex = parent.getDisplay().getDismissalAlignment() == SWT.LEFT ? 2
				: 1;

		assertEquals(
				"Finish button's alignment is off", dialog.getFinishedButton(), children[finishIndex]); //$NON-NLS-1$
		assertEquals(
				"Cancel button's alignment is off", dialog.getCancelButton(), children[cancelIndex]); //$NON-NLS-1$
	}

	public void testButtonAlignmentWithoutBackNextButtons() {
		wizard = new TheTestWizard() {
			@Override
			public void addPages() {
				addPage(new TheTestWizardPage(page1Name));
			}
		};
		dialog = new TheTestWizardDialog(null, wizard);
		dialog.create();
		dialog.open();

		Composite parent = dialog.getFinishedButton().getParent();
		Control[] children = parent.getChildren();
		assertEquals(
				"There should be two children, the finish button, and the cancel button", //$NON-NLS-1$
				2, children.length);

		int finishIndex = parent.getDisplay().getDismissalAlignment() == SWT.LEFT ? 0
				: 1;
		int cancelIndex = parent.getDisplay().getDismissalAlignment() == SWT.LEFT ? 1
				: 0;

		assertEquals(
				"Finish button's alignment is off", dialog.getFinishedButton(), children[finishIndex]); //$NON-NLS-1$
		assertEquals(
				"Cancel button's alignment is off", dialog.getCancelButton(), children[cancelIndex]); //$NON-NLS-1$
	}

	public void testBug270174() {
		wizard = new TheTestWizard() {
			@Override
			public boolean canFinish() {
				return false;
			}
		};
		dialog = new TheTestWizardDialog(null, wizard);
		dialog.create();
		dialog.open();

		Composite parent = dialog.getFinishedButton().getParent();
		Control[] children = parent.getChildren();
		assertEquals(
				"There should be three children, a composite for back/next buttons, the finish button, and the cancel button", //$NON-NLS-1$
				3, children.length);

		assertTrue(children[0] instanceof Composite);
		Composite backNextParent = (Composite) children[0];

		Control[] backNextChildren = backNextParent.getChildren();
		assertEquals("Back button should be the first button", dialog //$NON-NLS-1$
				.getBackButton(), backNextChildren[0]);
		assertEquals("Next button should be the second button", dialog //$NON-NLS-1$
				.getNextButton(), backNextChildren[1]);

		int finishIndex = parent.getDisplay().getDismissalAlignment() == SWT.LEFT ? 1
				: 2;
		int cancelIndex = parent.getDisplay().getDismissalAlignment() == SWT.LEFT ? 2
				: 1;

		assertEquals(
				"Finish button's alignment is off", dialog.getFinishedButton(), children[finishIndex]); //$NON-NLS-1$
		assertEquals(
				"Cancel button's alignment is off", dialog.getCancelButton(), children[cancelIndex]); //$NON-NLS-1$
	}

}
