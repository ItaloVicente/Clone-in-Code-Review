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

import org.eclipse.egit.ui.Activator;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;

public class GitModelRepositoryTest extends GitModelTestCase {

	@Test public void shouldReturnNotEqual() throws Exception {
		Repository leftRepo = lookupRepository(leftRepoFile);
		Repository rightRepo = lookupRepository(rightRepoFile);
		GitModelRepository left = new GitModelRepository(getGSD(leftRepo));
		GitModelRepository right = new GitModelRepository(getGSD(rightRepo));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnEqual() throws Exception {
		Repository leftRepo = lookupRepository(leftRepoFile);
		Repository rightRepo = lookupRepository(leftRepoFile);
		GitModelRepository left = new GitModelRepository(getGSD(leftRepo));
		GitModelRepository right = new GitModelRepository(getGSD(rightRepo));

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		Repository leftRepo = lookupRepository(leftRepoFile);
		GitModelRepository left = new GitModelRepository(getGSD(leftRepo));

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Before
	public void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();
		rightRepoFile = createChildRepository(leftRepoFile);

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

}
