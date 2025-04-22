package org.eclipse.egit.ui.internal.synchronize.model;

import static org.eclipse.compare.structuremergeviewer.Differencer.LEFT;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.egit.ui.Activator;
import org.junit.BeforeClass;
import org.junit.Test;

public class GitModelCommitTest extends GitModelTestCase {

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentCommits()
			throws Exception {
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);
		GitModelCommit right = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD + "~1"), LEFT);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentParents()
			throws Exception {
		File localRightRepoFile = createProjectAndCommitToRepository(REPO2);

		GitModelRepository rightGsd = new GitModelRepository(
				getGSD(lookupRepository(localRightRepoFile)));
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);
		GitModelCommit right = new GitModelCommit(rightGsd,
				getCommit(localRightRepoFile, HEAD), LEFT);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnEqualForSameCommits()
			throws Exception {
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);
		GitModelCommit right = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualWhenCommitTreeAndCache()
			throws Exception {
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);
		GitModelCache right = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenCommitTreeAndTree()
			throws Exception {
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);
		GitModelTree right = new GitModelTree(left,
				getCommit(leftRepoFile, HEAD), null, null, null, null, null);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenCommitTreeAndBlob()
			throws Exception {
		GitModelCommit left = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);
		GitModelBlob right = new GitModelBlob(left,
				getCommit(leftRepoFile, HEAD), null, null, null, null,
				getFile1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@BeforeClass public static void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

}
