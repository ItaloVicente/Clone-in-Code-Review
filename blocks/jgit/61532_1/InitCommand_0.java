
package org.eclipse.jgit.lib;

import static org.junit.Assert.*;

import java.io.IOException;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.junit.Test;

public class RepositoryCreateTest extends LocalDiskRepositoryTestCase {

	@Test
	public void testCreateBareRepository() throws IOException {
		Repository repo = new RepositoryBuilder()
				.setGitDir(getTemporaryDirectory()).setBare().build();
		repo.create();
		addRepoToClose(repo);
		assertTrue(repo.isBare());
	}

	@Test
	public void testCreateNonBareRepository() throws IOException {
		Repository repo = new RepositoryBuilder()
				.setWorkTree(getTemporaryDirectory()).build();
		repo.create();
		addRepoToClose(repo);
		assertFalse(repo.isBare());
	}
}
