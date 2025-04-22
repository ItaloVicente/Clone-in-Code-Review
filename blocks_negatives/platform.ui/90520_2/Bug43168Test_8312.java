/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.tests.keys;

import org.eclipse.ui.tests.harness.util.UITestCase;

/**
 * Tests Bug 42627
 *
 * @since 3.0
 */
public class Bug42627Test extends UITestCase {


    /**
     * A dummy implementation of an <code>Action</code>.
     *
     * @since 3.0
     */
    /**
     * Constructor for Bug42627Test.
     *
     * @param name
     *           The name of the test
     */
    public Bug42627Test(String name) {
        super(name);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
    }

    /**
     * Tests that actions with no defined command ID are logged.
     *
     * @throws CoreException
     *            If something fails when trying to open a new project.
     */
    public void testLogUndefined() /*throws CoreException*/{
    }
}
