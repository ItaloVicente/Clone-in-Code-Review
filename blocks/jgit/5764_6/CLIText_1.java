package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Assert;
import org.junit.Test;

public class CheckoutTest extends CLIRepositoryTestCase {

	@Test
	public void testCheckoutSelf() throws Exception {
		new Git(db).commit().setMessage("initial commit").call();

		assertEquals("Already on 'master'"
	}

	@Test
	public void testCheckoutBranch() throws Exception {
		new Git(db).commit().setMessage("initial commit").call();
		new Git(db).branchCreate().setName("side").call();

		assertEquals("Switched to branch 'side'"
	}

	@Test
	public void testCheckoutNewBranch() throws Exception {
		new Git(db).commit().setMessage("initial commit").call();

		assertEquals("Switched to a new branch 'side'"
				execute("git checkout -b side"));
	}

	@Test
	public void testCheckoutNonExistingBranch() throws Exception {
		assertEquals(
				"error: pathspec 'side' did not match any file(s) known to git."
				execute("git checkout side"));
	}

	@Test
	public void testCheckoutNewBranchThatAlreadyExists() throws Exception {
		new Git(db).commit().setMessage("initial commit").call();

		assertEquals("A branch named 'master' already exists."
				execute("git checkout -b master"));
	}

	@Test
	public void testCheckoutNewBranchOnBranchToBeBorn() throws Exception {
		assertEquals("You are on a branch yet to be born"
				execute("git checkout -b side"));
	}

	static private void assertEquals(String expected
		Assert.assertEquals(actual[actual.length - 1].equals("") ? 2 : 1
		Assert.assertEquals(expected
	}
}
