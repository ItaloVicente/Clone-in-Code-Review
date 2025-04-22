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

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;

import org.eclipse.egit.ui.Activator;
import org.junit.Before;
import org.junit.Test;

public class GitModelCommitTest extends GitModelTestCase {

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD),
				null);

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentCommits()
			throws Exception {
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD),
				null);
		GitModelCommit right = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD
						+ "~1"), null);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentParents()
			throws Exception {
		File localRightRepoFile = createProjectAndCommitToRepository(REPO2);

		GitModelRepository rightGsd = new GitModelRepository(
				getGSD(lookupRepository(localRightRepoFile)));
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD),
				null);
		GitModelCommit right = new GitModelCommit(rightGsd,
				lookupRepository(leftRepoFile), getCommit(localRightRepoFile,
						HEAD), null);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnEqualForSameCommits()
			throws Exception {
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD),
				null);
		GitModelCommit right = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD),
				null);

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualWhenCommitTreeAndCache()
			throws Exception {
		GitModelObject left = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD),
				null);
		GitModelObject right = mock(GitModelCache.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenCommitTreeAndTree()
			throws Exception {
		GitModelObject left = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD),
				null);
		GitModelObject right = mock(GitModelTree.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenCommitTreeAndBlob()
			throws Exception {
		GitModelObject left = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD),
				null);
		GitModelObject right = mock(GitModelBlob.class);

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
