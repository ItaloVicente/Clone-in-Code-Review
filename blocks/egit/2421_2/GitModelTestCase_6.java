package org.eclipse.egit.ui.internal.synchronize.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.egit.ui.Activator;
import org.eclipse.jgit.lib.Repository;
import org.junit.BeforeClass;
import org.junit.Test;

public class GitModelRepositoryTest extends GitModelTestCase {

	@Test public void shouldReturnNotEqual() throws Exception {
		Repository leftRepo = lookupRepository(leftRepoFile);
		Repository rightRepo = lookupRepository(rightRepoFile);
		GitModelRepository left = new GitModelRepository(getGSD(leftRepo));
		GitModelRepository right = new GitModelRepository(getGSD(rightRepo));

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnEqual() throws Exception {
		Repository leftRepo = lookupRepository(leftRepoFile);
		Repository rightRepo = lookupRepository(leftRepoFile);
		GitModelRepository left = new GitModelRepository(getGSD(leftRepo));
		GitModelRepository right = new GitModelRepository(getGSD(rightRepo));

		boolean actual = left.equals(right);

		assertTrue(actual);
	}

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		Repository leftRepo = lookupRepository(leftRepoFile);
		GitModelRepository left = new GitModelRepository(getGSD(leftRepo));

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@BeforeClass public static void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();
		rightRepoFile = createChildRepository(leftRepoFile);

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

}
