package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
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
		git.commit().setMessage("initial commit").call();
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		initialCommit = git.commit().setMessage("Initial commit").call();
		git.branchCreate().setForce(true).setName("master").call();
		RefUpdate rup = db.updateRef("refs/heads/master");
		rup.setNewObjectId(initialCommit.getId());
		rup.setForceUpdate(true);
		rup.update();

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		secondCommit = git.commit().setMessage("Second commit").call();
		git.branchCreate().setForce(true).setName("test").call();
	}

	public void testSimpleCheckout() {
		try {
			git.checkout().setName("test").call();
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
