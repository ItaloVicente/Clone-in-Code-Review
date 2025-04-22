package org.eclipse.egit.ui.internal.synchronize.model;

import static org.eclipse.compare.structuremergeviewer.Differencer.LEFT;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.egit.ui.Activator;
import org.junit.BeforeClass;
import org.junit.Test;

public class GitModelCacheTest extends GitModelTestCase {

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		GitModelCache left = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD));

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentRepositories()
			throws Exception {
		File localRightRepoFile = createProjectAndCommitToRepository(REPO2);
		GitModelRepository rightGsd = new GitModelRepository(
				getGSD(lookupRepository(localRightRepoFile)));
		GitModelCache left = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD));
		GitModelCache right = new GitModelCache(rightGsd,
				getCommit(localRightRepoFile, HEAD));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentCommits()
			throws Exception {
		GitModelCache left = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD));
		GitModelCache right = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD + "~1"));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnEqualForSameCommits()
			throws Exception {
		GitModelCache left = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD));
		GitModelCache right = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD));

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualWhenComparingCacheAndWorkingTree()
			throws Exception {
		GitModelCache left = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD));
		GitModelCache right = new GitModelWorkingTree(createModelRepository(),
				getCommit(leftRepoFile, HEAD));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenCacheTreeAndCommit()
			throws Exception {
		GitModelCache left = new GitModelCache(createModelRepository(),
				getCommit(leftRepoFile, HEAD));
		GitModelCommit right = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@BeforeClass public static void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

}
