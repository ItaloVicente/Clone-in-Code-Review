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

import static org.eclipse.compare.structuremergeviewer.Differencer.CHANGE;
import static org.eclipse.compare.structuremergeviewer.Differencer.RIGHT;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.Activator;
import org.junit.Before;
import org.junit.Test;

public class GitModelTreeTest extends GitModelTestCase {

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		GitModelTree left = createModelTree();

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Test public void shouldReturnEqualForSameBaseCommit() throws Exception {
		GitModelTree left = createModelTree(getTreeLocation());
		GitModelTree right = createModelTree(getTreeLocation());

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentLocation()
			throws Exception {
		GitModelTree left = createModelTree(getTreeLocation());
		GitModelTree right = createModelTree(getTree1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForTreeAndCommit()
			throws Exception {
		GitModelObject left = createModelTree(getTreeLocation());
		GitModelObject right = mock(GitModelCommit.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForTreeAndBlob()
			throws Exception {
		GitModelObject left = createModelTree(getTreeLocation());
		GitModelObject right = mock(GitModelBlob.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForTreeAndCacheTree()
			throws Exception {
		GitModelTree left = createModelTree(getTreeLocation());
		GitModelCacheTree right = mock(GitModelCacheTree.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Before
	public void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

	private GitModelTree createModelTree() throws Exception {
		return createModelTree(getTreeLocation());
	}

	private GitModelTree createModelTree(IPath location)
			throws Exception {
		return new GitModelTree(createModelCommit(), location, RIGHT | CHANGE);
	}

}
