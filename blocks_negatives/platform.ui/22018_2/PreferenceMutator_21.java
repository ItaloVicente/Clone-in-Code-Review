/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.keybinding.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests for all areas of the key support for the platform.
 */
public class KeysTestSuite extends TestSuite {

    /**
     * Returns the suite. This is required to use the JUnit Launcher.
     */
    public static Test suite() {
        return new KeysTestSuite();
    }

    /**
     * Construct the test suite.
     */
    public KeysTestSuite() {
    	addTest(new TestSuite(BindingInteractionsTest.class));
    	addTest(new TestSuite(BindingManagerTest.class));
        addTest(new TestSuite(BindingPersistenceTest.class));
        addTest(new TestSuite(Bug42024Test.class));
        addTest(new TestSuite(Bug42035Test.class));
        addTest(new TestSuite(Bug43168Test.class));
        addTest(new TestSuite(Bug43321Test.class));
        addTest(new TestSuite(Bug43538Test.class));
        addTest(new TestSuite(Bug43597Test.class));
        addTest(new TestSuite(Bug43610Test.class));
        addTest(new TestSuite(Bug43800Test.class));
        addTest(new TestSuite(KeysCsvTest.class));
        /* TODO disabled as it is causing failures in the nightly builds.
         * focus related?
         */
        addTest(new TestSuite(Bug189167Test.class));
        addTest(new TestSuite(KeysPreferenceModelTest.class));
    }
}
