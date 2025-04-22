package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class GitConstructionTest extends RepositoryTestCase {
	private Repository bareRepo;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Initial commit").call();

		bareRepo = Git.cloneRepository().setBare(true)
				.setURI(db.getDirectory().toURI().toString()).call()
				.getRepository();
	}

	@Test
	public void testWrap() {
		Git git = Git.wrap(db);
		assertEquals(1

		git = Git.wrap(bareRepo);
		assertEquals(1
				.size());

		try {
			Git.wrap(null);
			fail("Expected exception has not been thrown");
		} catch (NullPointerException e) {
		}
	}

	@Test
	public void testOpen() throws IOException {
		Git git = Git.open(db.getDirectory());
		assertEquals(1

		git = Git.open(bareRepo.getDirectory());
		assertEquals(1
				.size());

		try {
			Git.open(db.getWorkTree());
			fail("Expected exception has not been thrown");
		} catch (RepositoryNotFoundException e) {
		}

		try {
			Git.open(db.getObjectsDirectory());
			fail("Expected exception has not been thrown");
		} catch (RepositoryNotFoundException e) {
		}

		try {
			Git.open(null);
			fail("Expected exception has not been thrown");
		} catch (IllegalArgumentException e) {
		}
	}
}
