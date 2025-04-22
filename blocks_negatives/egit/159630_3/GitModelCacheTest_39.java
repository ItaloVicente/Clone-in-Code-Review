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

import static org.eclipse.jgit.lib.ObjectId.fromString;
import static org.eclipse.jgit.lib.ObjectId.zeroId;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.synchronize.GitCommitsModelCache.Change;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GitModelCacheFileTest extends GitModelTestCase {

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		GitModelCacheFile left = createCacheFile(zeroId(), zeroId(),
				getFile1Location());

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Test public void shouldReturnEqualForSameObjectIdsAndLocation()
			throws Exception {
		GitModelCacheFile left = createCacheFile(zeroId(),
				fromString("390b6b146aa218a9c985e6ce9df2845eb575be48"),
				getFile1Location());
		GitModelCacheFile right = createCacheFile(zeroId(),
				fromString("390b6b146aa218a9c985e6ce9df2845eb575be48"),
				getFile1Location());

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentBaseIds()
			throws Exception {
		GitModelCacheFile left = createCacheFile(
				fromString("390b6b146aa218a9c985e6ce9df2845eb575be48"),
				fromString("390b6b146aa218a9c985e6ce9df2845eb0000000"),
				getFile1Location());
		GitModelCacheFile right = createCacheFile(zeroId(),
				fromString("390b6b146aa218a9c985e6ce9df2845eb575be48"),
				getFile1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Ignore
	@Test public void shouldReturnNotEqualForDifferentCacheIds()
			throws Exception {
		GitModelCacheFile left = createCacheFile(zeroId(),
				fromString("390b6b146aa218a9c985e6ce9df2845eb575be48"),
				getFile1Location());
		GitModelCacheFile right = createCacheFile(zeroId(),
				fromString("000000006aa218a9c985e6ce9df2845eb575be48"),
				getFile1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Ignore
	@Test public void shouldReturnNotEqualForDifferentLocations()
			throws Exception {
		GitModelCacheFile left = createCacheFile(zeroId(),
				fromString("000000006aa218a9c985e6ce9df2845eb575be48"),
				getFile1Location());
		GitModelCacheFile right = createCacheFile(zeroId(),
				fromString("000000006aa218a9c985e6ce9df2845eb575be48"),
				getFile2Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenComparingCacheFileAndBlob()
			throws Exception {
		GitModelCacheFile left = createCacheFile(zeroId(),
				fromString("000000006aa218a9c985e6ce9df2845eb575be48"),
				getFile1Location());
		GitModelBlob right = mock(GitModelBlob.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenComparingCacheFileAndWorkingFile()
			throws Exception {
		GitModelObject left = createCacheFile(zeroId(),
				fromString("000000006aa218a9c985e6ce9df2845eb575be48"),
				getFile1Location());
		GitModelObject right = mock(GitModelWorkingFile.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Before
	public void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

	private GitModelCacheFile createCacheFile(ObjectId repoId,
			ObjectId cacheId, IPath location) throws Exception {
		Change change = mock(Change.class);
		when(change.getObjectId()).thenReturn(
				AbbreviatedObjectId.fromObjectId(cacheId));
		when(change.getRemoteObjectId()).thenReturn(
				AbbreviatedObjectId.fromObjectId(repoId));

		return new GitModelCacheFile(createModelCommit(),
				lookupRepository(leftRepoFile), change, location);
	}

}
