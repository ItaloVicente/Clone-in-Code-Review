/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - Initial API and implementation
 *     Tomasz Zarna (Tasktop Technologies) - [429546] External Browser with parameters
 *******************************************************************************/
package org.eclipse.ui.tests.browser.internal;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for org.eclipse.ui.browser.tests");
		suite.addTestSuite(ExistenceTestCase.class);
		suite.addTestSuite(InternalBrowserViewTestCase.class);
		suite.addTestSuite(InternalBrowserEditorTestCase.class);

		suite.addTestSuite(DialogsTestCase.class);
		suite.addTestSuite(PreferencesTestCase.class);
		suite.addTestSuite(ToolbarBrowserTestCase.class);
		suite.addTestSuite(WebBrowserUtilTestCase.class);
		return suite;
	}
}
