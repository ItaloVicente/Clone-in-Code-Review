package org.eclipse.egit.ui.internal.synchronize.model;

import static org.eclipse.compare.structuremergeviewer.Differencer.LEFT;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.ObjectId.fromString;
import static org.eclipse.jgit.lib.ObjectId.zeroId;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.BeforeClass;
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

	@Test public void shouldReturnEqualForSameData() throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), zeroId(),
				getFile1Location());
		GitModelBlob right = createGitModelBlob(zeroId(), zeroId(),
				getFile1Location());

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Test public void shouldReturnEqualSameData1() throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), getFile1Location());
		GitModelBlob right = createGitModelBlob(zeroId(), getFile1Location());

		boolean actual = left.equals(right);

		assertTrue(actual);
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
		GitModelBlob left = createGitModelBlob();
		GitModelCommit right = new GitModelCommit(createModelRepository(), getCommit(
				leftRepoFile, HEAD), LEFT);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForBlobAndTree() throws Exception {
		GitModelBlob left = createGitModelBlob();
		GitModelTree right = new GitModelTree(createModelCommit(), getCommit(
				leftRepoFile, HEAD), null, null, null, null, getFile1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForBlobAndCacheFile()
			throws Exception {
		GitModelBlob left = createGitModelBlob();
		GitModelCacheFile right = new GitModelCacheFile(createModelCommit(),
				getCommit(leftRepoFile, HEAD), null, null, getFile1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForBlobAndWorkingFile()
			throws Exception {
		GitModelBlob left = createGitModelBlob();
		GitModelWorkingFile right = new GitModelWorkingFile(
				createModelCommit(), getCommit(leftRepoFile, HEAD), null,
				getFile1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@BeforeClass public static void setupEnvironment() throws Exception {
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
		return new GitModelBlob(createModelCommit(),
				getCommit(leftRepoFile, HEAD), null, null, baseId,
				remoteId, location);
	}

}
