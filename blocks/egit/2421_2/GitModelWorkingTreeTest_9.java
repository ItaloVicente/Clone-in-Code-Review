package org.eclipse.egit.ui.internal.synchronize.model;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.Activator;
import org.junit.BeforeClass;
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
		GitModelBlob right = new GitModelBlob(createModelCommit(), getCommit(
				leftRepoFile, HEAD), null, null, null, null, getFile1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenComparingWorkingFileAndCacheFile()
			throws Exception {
		GitModelWorkingFile left = createWorkingFile(getFile1Location());
		GitModelCacheFile right = new GitModelCacheFile(createModelCommit(),
				getCommit(leftRepoFile, HEAD), null, null, getFile1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@BeforeClass public static void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

	private GitModelWorkingFile createWorkingFile(IPath location)
			throws Exception {
		return new GitModelWorkingFile(createModelCommit(), getCommit(
				leftRepoFile, HEAD), null, location);
	}

}
