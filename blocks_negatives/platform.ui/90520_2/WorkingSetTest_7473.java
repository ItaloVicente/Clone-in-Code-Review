/*******************************************************************************
 * Copyright (c) 2009, 2015 Oakland Software Incorporated and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Oakland Software Incorporated - initial API and implementation
 *     Thibault Le Ouay <thibaultleouay@gmail.com> - Bug 457870
 *******************************************************************************/
package org.eclipse.ui.tests.navigator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.help.WorkbenchHelpSystem;
import org.eclipse.ui.tests.harness.util.EditorTestHelper;
import org.eclipse.ui.tests.navigator.extension.TestContentProvider;
import org.junit.Test;

public class ViewerTest extends NavigatorTestBase {

	public ViewerTest() {
		_navigatorInstanceId = TEST_VIEWER_INHERITED;
	}

	@Test
	public void testInheritedViewer() throws Exception {

		IStructuredSelection sel;
		sel = new StructuredSelection(
				((IContainer) _p2.members()[1]).members()[0]);
		_viewer.setSelection(sel);

		verifyMenu(sel, "Resource Mapping");
	}

	@Test
	public void testHelpId() throws Exception {
		String context = (String) _viewer.getControl().getData(WorkbenchHelpSystem.HELP_KEY);
		assertEquals(TEST_VIEWER_HELP_CONTEXT, context);
	}

	@Test
	public void testDispose() throws Exception {
		refreshViewer();
		_viewer.expandAll();

		TestContentProvider._dieOnSetInput = true;
		EditorTestHelper.showView(_navigatorInstanceId, false);

		assertFalse(TestContentProvider._diedOnSetInput);
	}

}
