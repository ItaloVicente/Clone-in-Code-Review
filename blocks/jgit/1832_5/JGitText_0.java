package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;

public class CheckoutCommandTest extends RepositoryTestCase {
	private Git git;

	RevCommit initialCommit;

	RevCommit secondCommit;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		initialCommit = git.commit().setMessage("Initial commit").call();

		git.branchCreate().setName("test").call();
		RefUpdate rup = db.updateRef(Constants.HEAD);
		rup.link("refs/heads/test");

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		secondCommit = git.commit().setMessage("Second commit").call();
	}

	public void testSimpleCheckout() {
		try {
			git.checkout().setName("test").call();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testCheckout() {
		try {
			git.checkout().setName("test").call();
			assertEquals("[Test.txt
					indexState(CONTENT));
			git.checkout().setName("master").call();
			assertEquals("[Test.txt
					indexState(CONTENT));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testCreateBranchOnCheckout() throws IOException {
		try {
			git.checkout().setCreateBranch(true).setName("test2").call();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(db.getRef("test2"));
	}

	public void testCheckoutToNonExistingBranch() throws JGitInternalException
			RefAlreadyExistsException
		try {
			git.checkout().setName("badbranch").call();
			fail("Should have failed");
		} catch (RefNotFoundException e) {
		}
	}

}
