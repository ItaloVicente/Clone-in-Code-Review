package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.lib.RepositoryTestCase;

public class BranchCommandTest extends RepositoryTestCase {

	private static final String TEST_BRANCH_NAME = "test";

	public void testCreateBranch() throws JGitInternalException
			ConcurrentRefUpdateException
			UnmergedPathException
		Git git = new Git(db);
		git.commit().setMessage("first commit!").call();
		git.branch().setName(TEST_BRANCH_NAME).call();

		try {
			assertNotNull(db.getRef(TEST_BRANCH_NAME));
		} catch (IOException e) {
			fail("failed during branch creation test");
		}
	}

	public void testInvalidBranchName() throws JGitInternalException
			ConcurrentRefUpdateException
			UnmergedPathException
		Git git = new Git(db);
		git.commit().setMessage("first commit!").call();

		try {
			git.branch().setName(TEST_BRANCH_NAME + " spaces are bad").call();
		} catch (JGitInternalException e) {
		}
	}

	public void testDeleteBranch() throws JGitInternalException
			ConcurrentRefUpdateException
			UnmergedPathException
		Git git = new Git(db);
		git.commit().setMessage("first commit!").call();
		git.branch().setName("test").call();

		try {
			assertNotNull(db.getRef("test"));
			git.branch().setName("test").setDelete(true).call();
			assertNull(db.getRef("test"));
		} catch (IOException e) {
			fail("failed during branch creation test");
		}
	}

	public void testMoveBranch() throws JGitInternalException
			ConcurrentRefUpdateException
			UnmergedPathException
		Git git = new Git(db);
		git.commit().setMessage("first commit!").call();
		git.branch().rename(null

		try {
			assertNull(db.getRef("test"));
			assertNotNull(db.getRef("test2"));
		} catch (IOException e) {
			fail("failed during branch creation test");
		}
	}

}
