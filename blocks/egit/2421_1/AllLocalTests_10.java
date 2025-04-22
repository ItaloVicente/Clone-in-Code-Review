package org.eclipse.egit.ui.internal.synchronize.model;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.junit.Assert.assertFalse;

import java.io.File;

import org.eclipse.egit.ui.Activator;
import org.junit.BeforeClass;
import org.junit.Test;

public class GitModelWorkingTreeTest extends GitModelTestCase {

	@Test public void shouldReturnEqualsForTheSameInstance() throws Exception {
		GitModelWorkingTree left = new GitModelWorkingTree(createModelCommit(),
				getCommit(leftRepoFile, HEAD));

		boolean actual = left.equals(left);

		assertFalse(!actual);
	}

	@Test public void shouldReturnNotEqualsForTheDifferentCommits()
			throws Exception {
		GitModelWorkingTree left = new GitModelWorkingTree(createModelCommit(),
				getCommit(leftRepoFile, HEAD));
		GitModelWorkingTree right = new GitModelWorkingTree(
				createModelCommit(),
				getCommit(leftRepoFile, HEAD + "~1"));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualsForTheDifferentParents()
			throws Exception {
		File localRightRepoFile = createProjectAndCommitToRepository(REPO2);
		GitModelRepository rightGsd = new GitModelRepository(
				getGSD(lookupRepository(localRightRepoFile)));
		GitModelWorkingTree left = new GitModelWorkingTree(createModelCommit(),
				getCommit(leftRepoFile, HEAD));
		GitModelWorkingTree right = new GitModelWorkingTree(rightGsd,
				getCommit(localRightRepoFile, HEAD));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnEqualsForTheSameCommits()
			throws Exception {
		GitModelWorkingTree left = new GitModelWorkingTree(createModelCommit(),
				getCommit(leftRepoFile, HEAD));
		GitModelWorkingTree right = new GitModelWorkingTree(
				createModelCommit(), getCommit(leftRepoFile, HEAD));

		boolean actual = left.equals(right);

		assertFalse(!actual);
	}

	@Test public void shouldReturnNotEqualsWhenComparingWorkingTreeAndCache()
			throws Exception {
		GitModelWorkingTree left = new GitModelWorkingTree(createModelCommit(),
				getCommit(leftRepoFile, HEAD));
		GitModelCache right = new GitModelCache(createModelCommit(),
				getCommit(leftRepoFile, HEAD));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@BeforeClass public static void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

}
