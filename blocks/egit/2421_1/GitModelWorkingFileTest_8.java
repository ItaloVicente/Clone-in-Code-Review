package org.eclipse.egit.ui.internal.synchronize.model;

import static org.eclipse.compare.structuremergeviewer.Differencer.LEFT;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.Activator;
import org.junit.BeforeClass;
import org.junit.Test;

public class GitModelTreeTest extends GitModelTestCase {

	@Test public void shouldReturnEqualForSameInstance() throws Exception {
		GitModelTree left = createModelTree();

		boolean actual = left.equals(left);

		assertTrue(actual);
	}

	@Test public void shouldReturnEqualForSameBaseCommit() throws Exception {
		GitModelTree left = createModelTree(HEAD, getTreeLocation());
		GitModelTree right = createModelTree(HEAD, getTreeLocation());
		
		boolean actual = left.equals(right);
		
		assertTrue(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentBaseCommit()
			throws Exception {
		GitModelTree left = createModelTree(HEAD, getTreeLocation());
		GitModelTree right = createModelTree(HEAD + "~1", getTreeLocation());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForDifferentLocation()
			throws Exception {
		GitModelTree left = createModelTree(HEAD, getTreeLocation());
		GitModelTree right = createModelTree(HEAD, getTree1Location());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForTreeAndCommit()
			throws Exception {
		GitModelTree left = createModelTree(HEAD, getTreeLocation());
		GitModelCommit right = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForTreeAndBlob()
			throws Exception {
		GitModelTree left = createModelTree(HEAD, getTreeLocation());
		GitModelBlob right = new GitModelBlob(createModelCommit(), getCommit(
				leftRepoFile, HEAD), null, null, null, null, getTreeLocation());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@Test public void shouldReturnNotEqualForTreeAndCacheTree()
			throws Exception {
		GitModelTree left = createModelTree(HEAD, getTreeLocation());
		GitModelCacheTree right = new GitModelCacheTree(createModelCommit(),
				getCommit(leftRepoFile, HEAD), null, null, getTreeLocation(),
				null);

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

	@BeforeClass public static void setupEnvironment() throws Exception {
		leftRepoFile = createProjectAndCommitToRepository();

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(leftRepoFile);
	}

	private GitModelTree createModelTree() throws Exception {
		return createModelTree(HEAD, getTreeLocation());
	}

	private GitModelTree createModelTree(String revStr, IPath location)
			throws Exception {
		return new GitModelTree(createModelCommit(), getCommit(
				leftRepoFile, revStr), getCommit(leftRepoFile, revStr), null,
				null, null, location);
	}

}
