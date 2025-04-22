/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.api;

import junit.framework.TestCase;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * Tests the PlatformUI class.
 */
public class PlatformUITest extends TestCase {

    public PlatformUITest(String testName) {
        super(testName);
    }

    public void testGetWorkbench() throws Throwable {
        IWorkbench wb = PlatformUI.getWorkbench();
        assertNotNull(wb);
    }

    public void testPLUGIN_ID() {
        assertNotNull(PlatformUI.PLUGIN_ID);
    }
}
