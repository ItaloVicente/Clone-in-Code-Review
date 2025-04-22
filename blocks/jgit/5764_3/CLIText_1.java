package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class CheckoutTest extends CLIRepositoryTestCase {
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		new Git(db).commit().setMessage("initial commit").call();
	}

	@Test
	public void testCheckoutSelf() throws Exception {
		assertEquals("Already on 'master'"
	}

	@Test
	public void testCheckoutBranch() throws Exception {
		new Git(db).branchCreate().setName("side").call();

		assertEquals("Switched to branch 'side'"
				execute("git checkout side")[0]);
	}

	@Test
	public void testCheckoutNewBranch() throws Exception {
		assertEquals("Switched to a new branch 'side'"
				execute("git checkout -b side")[0]);
	}

	@Test
	public void testCheckoutNonExistingBranch() throws Exception {
		assertEquals(
				"error: pathspec 'side' did not match any file(s) known to git."
				execute("git checkout side")[0]);
	}

	@Test
	public void testCheckoutNewBranchThatAlreadyExists() throws Exception {
		assertEquals(
"fatal: git checkout: branch master already exists"
				execute("git checkout -b master")[0]);
	}

}
