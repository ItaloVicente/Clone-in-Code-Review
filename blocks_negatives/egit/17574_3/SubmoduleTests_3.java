/******************************************************************************
 *  Copyright (c) 2011 GitHub Inc.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *
 *  Contributors:
 *    Kevin Sawicki (GitHub Inc.) - initial API and implementation
 *****************************************************************************/
package org.eclipse.egit.ui.search;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Search unit test suite
 */
@RunWith(Suite.class)
@SuiteClasses({ CommitSearchDialogTest.class, //
		CommitSearchQueryTest.class, //
})
public class SearchTests {
}
