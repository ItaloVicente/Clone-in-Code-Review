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
import static org.eclipse.jgit.lib.ObjectId.fromString;
import static org.eclipse.jgit.lib.ObjectId.zeroId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.synchronize.GitCommitsModelCache.Change;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GitModelBlobTest extends GitModelTestCase {

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		GitModelBlob left = createGitModelBlob();

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentLocation()
			throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), getFile1Location());
		GitModelBlob right = createGitModelBlob(zeroId(), getFile2Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Ignore
	@Test public void shouldReturnEqualForSameData() throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), zeroId(),
				getFile1Location());
		GitModelBlob right = createGitModelBlob(zeroId(), zeroId(),
				getFile1Location());

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Ignore
	@Test public void shouldReturnEqualSameData1() throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), getFile1Location());
		GitModelBlob right = createGitModelBlob(zeroId(), getFile1Location());

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@SuppressWarnings("boxing")
	@Test
	public void shouldBeSymmetric() throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), getFile1Location());
		GitModelBlob right = createGitModelBlob(zeroId(), getFile1Location());

		boolean actual1 = left.equals(right);
		boolean actual2 = right.equals(left);

		assertEquals(actual1, actual2);
	}

	@Test
	public void shouldBeSymmetric1() throws Exception {
		GitModelObject left = createGitModelBlob(zeroId(), getFile1Location());
		GitModelObject right = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD),
				null);

		boolean actual1 = left.equals(right);
		boolean actual2 = right.equals(left);

		assertTrue(!actual1);
		assertTrue(!actual2);
	}

	@Test public void shouldReturnNotEqualForDifferentFiles()
			throws Exception {
		GitModelBlob left = createGitModelBlob();
		GitModelBlob right = createGitModelBlob(zeroId(), getFile2Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentBaseObjectId()
			throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), getFile1Location());
		GitModelBlob right = createGitModelBlob(
				fromString("4c879313cd1332e594b1ad20b1485bdff9533034"),
				getFile1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentBaseObjectId2()
			throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), zeroId(),
				getFile1Location());
		GitModelBlob right = createGitModelBlob(
				fromString("4c879313cd1332e594b1ad20b1485bdff9533034"),
				null, getFile1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentBaseObjectId3()
			throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), ObjectId.zeroId(),
				getFile1Location());
		GitModelBlob right = createGitModelBlob(
				fromString("4c879313cd1332e594b1ad20b1485bdff9533034"),
				fromString("4c879313cd1332e0000000000000000111122233"),
				getFile2Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForBlobAndCommit() throws Exception {
		GitModelObject left = createGitModelBlob();
		GitModelObject right = new GitModelCommit(createModelRepository(),
				lookupRepository(leftRepoFile), getCommit(leftRepoFile, HEAD),
				null);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForBlobAndTree() throws Exception {
		GitModelObject left = createGitModelBlob();
		GitModelObject right = mock(GitModelTree.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForBlobAndCacheFile()
			throws Exception {
		GitModelBlob left = createGitModelBlob();
		GitModelCacheFile right = mock(GitModelCacheFile.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForBlobAndWorkingFile()
			throws Exception {
		GitModelBlob left = createGitModelBlob();
		GitModelWorkingFile right = mock(GitModelWorkingFile.class);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldActAsResourceProvider()
			throws Exception {
		GitModelBlob left = createGitModelBlob();

		IFile file = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFile(new Path("folder/test.txt"));
		IPath leftLocation = left.getResource().getLocation();
		assertEquals(file.getLocation(), leftLocation);
	}

	@Before
	public void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

	private GitModelBlob createGitModelBlob() throws Exception {
		return createGitModelBlob(null, getFile1Location());
	}

	private GitModelBlob createGitModelBlob(ObjectId baseId, IPath location)
			throws IOException, Exception {
		return createGitModelBlob(baseId, null, location);
	}

	private GitModelBlob createGitModelBlob(ObjectId baseId, ObjectId remoteId,
			IPath location) throws Exception {
		Change change = mock(Change.class);
		if (baseId != null)
			when(change.getObjectId()).thenReturn(
					AbbreviatedObjectId.fromObjectId(baseId));
		if (remoteId != null)
			when(change.getRemoteObjectId()).thenReturn(
					AbbreviatedObjectId.fromObjectId(remoteId));

		return new GitModelBlob(createModelCommit(),
				lookupRepository(leftRepoFile), change, location);
	}

}
