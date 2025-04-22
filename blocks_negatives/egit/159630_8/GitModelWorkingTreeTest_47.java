/*******************************************************************************
 * Copyright (C) 2010, Dariusz Luksza <dariusz@luksza.org>
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

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.Activator;
import org.junit.Before;
import org.junit.Test;

public class GitModelWorkingFileTest extends GitModelTestCase {

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		GitModelWorkingFile left = createWorkingFile(getFile1Location());

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Test public void shouldReturnEqualForSameLocation() throws Exception {
		GitModelWorkingFile left = createWorkingFile(getFile1Location());
		GitModelWorkingFile right = createWorkingFile(getFile1Location());

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentLocation()
			throws Exception {
		GitModelWorkingFile left = createWorkingFile(getFile1Location());
		GitModelWorkingFile right = createWorkingFile(getFile2Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenComparingWorkingFileAndBlob()
			throws Exception {
		GitModelWorkingFile left = createWorkingFile(getFile1Location());
		GitModelBlob right = mock(GitModelBlob.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenComparingWorkingFileAndCacheFile()
			throws Exception {
		GitModelObject left = createWorkingFile(getFile1Location());
		GitModelObject right = mock(GitModelCacheFile.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Before
	public void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

	private GitModelWorkingFile createWorkingFile(IPath location)
			throws Exception {
		return new GitModelWorkingFile(createModelCommit(),
				lookupRepository(leftRepoFile), null, location);
	}

}
