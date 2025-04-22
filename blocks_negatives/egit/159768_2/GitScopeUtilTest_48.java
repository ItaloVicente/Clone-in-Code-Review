/*******************************************************************************
 * Copyright (C) 2011, Dariusz Luksza <dariusz@luksza.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.egit.ui.internal.synchronize.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.egit.ui.Activator;
import org.junit.Before;
import org.junit.Test;

public class GitModelWorkingTreeTest extends GitModelTestCase {

	@Test public void shouldReturnEqualsForTheSameInstance() throws Exception {
		GitModelWorkingTree left = new GitModelWorkingTree(
				createModelRepository(), lookupRepository(leftRepoFile), null);

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualsWhenComparingWorkingTreeAndCache()
			throws Exception {
		GitModelWorkingTree left = new GitModelWorkingTree(
				createModelRepository(), lookupRepository(leftRepoFile), null);
		GitModelCache right = mock(GitModelCache.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Before
	public void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

}
