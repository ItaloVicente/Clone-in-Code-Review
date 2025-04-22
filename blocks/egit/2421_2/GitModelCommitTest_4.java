package org.eclipse.egit.ui.internal.synchronize.model;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.ObjectId.fromString;
import static org.eclipse.jgit.lib.ObjectId.zeroId;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.BeforeClass;
import org.junit.Test;

public class GitModelCacheTreeTest extends GitModelTestCase {

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		GitModelCacheTree left = crateCacheTree(zeroId(), getTreeLocation());

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Test public void shouldReturnEqualForSameData() throws Exception {
		GitModelCacheTree left = crateCacheTree(zeroId(), getTreeLocation());
		GitModelCacheTree right = crateCacheTree(zeroId(), getTreeLocation());

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualForDifferetnRepoId() throws Exception {
		GitModelCacheTree left = crateCacheTree(zeroId(), getTreeLocation());
		GitModelCacheTree right = crateCacheTree(
				fromString("4c879313cd1332e594b1ad20b1485bdff9533034"),
				getTreeLocation());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForDifferetnLocation()
			throws Exception {
		GitModelCacheTree left = crateCacheTree(zeroId(), getTreeLocation());
		GitModelCacheTree right = crateCacheTree(zeroId(), getTree1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualWhenComparingCacheTreeAndTree()
			throws Exception {
		GitModelCacheTree left = crateCacheTree(zeroId(), getTreeLocation());
		GitModelTree right = new GitModelTree(createModelCommit(), getCommit(
				leftRepoFile, HEAD), null, null, null, null, getTreeLocation());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@BeforeClass public static void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

	private GitModelCacheTree crateCacheTree(ObjectId repoId, IPath location)
			throws Exception {
		return new GitModelCacheTree(createModelCommit(), getCommit(
				leftRepoFile, HEAD), repoId, null, location, null);
	}
}
