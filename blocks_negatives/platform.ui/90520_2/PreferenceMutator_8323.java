/*******************************************************************************
 * Copyright (c) 2000, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.keys;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Tests for all areas of the key support for the platform.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	BindingInteractionsTest.class,
	BindingManagerTest.class,
	BindingPersistenceTest.class,
	Bug36537Test.class,
	Bug42024Test.class,
	Bug42035Test.class,
	Bug43168Test.class,
	Bug43321Test.class,
	Bug43538Test.class,
	Bug43597Test.class,
	Bug43610Test.class,
	Bug43800Test.class,
	KeysCsvTest.class,
	/* TODO disabled as it fails on the Mac.
	 * Ctrl+S doesn't save the editor, and posting MOD1+S also doesn't seem to work.
	 */
	Bug189167Test.class,
	KeysPreferenceModelTest.class
 })
public class KeysTestSuite {

}
