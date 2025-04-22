
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.junit.Test;

public class BranchConfigTest {

	@Test
	public void getRemoteTrackingBranchShouldHandleNormalCase() {
				+ "[remote \"origin\"]\n"
				+ "[branch \"master\"]\n"
				+ "  remote = origin\n"
				+ "  merge = refs/heads/master\n");

		BranchConfig branchConfig = new BranchConfig(c
		assertEquals("refs/remotes/origin/master"
				branchConfig.getRemoteTrackingBranch());
	}

	@Test
	public void getRemoteTrackingBranchShouldHandleOtherMapping() {
				+ "[remote \"test\"]\n"
				+ "[branch \"master\"]\n"
				+ "  remote = test\n"
				+ "  merge = refs/foo/master\n" + "\n");

		BranchConfig branchConfig = new BranchConfig(c
		assertEquals("refs/remotes/origin/foo/master"
				branchConfig.getRemoteTrackingBranch());
	}

	@Test
	public void getRemoteTrackingBranchShouldReturnNullWithoutFetchSpec() {
				+ "[remote \"origin\"]\n"
				+ "  fetch = +refs/heads/onlyone:refs/remotes/origin/onlyone\n"
				+ "[branch \"master\"]\n"
				+ "  remote = origin\n"
				+ "  merge = refs/heads/master\n");
		BranchConfig branchConfig = new BranchConfig(c
		assertNull(branchConfig.getRemoteTrackingBranch());
	}

	@Test
	public void getRemoteTrackingBranchShouldReturnNullWithoutMergeBranch() {
				+ "[remote \"origin\"]\n"
				+ "  fetch = +refs/heads/onlyone:refs/remotes/origin/onlyone\n"
				+ "[branch \"master\"]\n"
				+ "  remote = origin\n");
		BranchConfig branchConfig = new BranchConfig(c
		assertNull(branchConfig.getRemoteTrackingBranch());
	}

	@Test
	public void getTrackingBranchShouldReturnMergeBranchForLocalBranch() {
				+ "[remote \"origin\"]\n"
				+ "[branch \"master\"]\n"
				+ "  remote = .\n"
				+ "  merge = refs/heads/master\n");

		BranchConfig branchConfig = new BranchConfig(c
		assertEquals("refs/heads/master"
				branchConfig.getTrackingBranch());
	}

	@Test
	public void getTrackingBranchShouldReturnNullWithoutMergeBranchForLocalBranch() {
				+ "[remote \"origin\"]\n"
				+ "  fetch = +refs/heads/onlyone:refs/remotes/origin/onlyone\n"
				+ "  remote = .\n");
		BranchConfig branchConfig = new BranchConfig(c
		assertNull(branchConfig.getTrackingBranch());
	}

	@Test
	public void getTrackingBranchShouldHandleNormalCaseForRemoteTrackingBranch() {
				+ "[remote \"origin\"]\n"
				+ "[branch \"master\"]\n"
				+ "  remote = origin\n"
				+ "  merge = refs/heads/master\n");

		BranchConfig branchConfig = new BranchConfig(c
		assertEquals("refs/remotes/origin/master"
				branchConfig.getTrackingBranch());
	}

	@Test
	public void isRebase() {
				+ "[branch \"undefined\"]\n"
				+ "[branch \"false\"]\n"
				+ "  rebase = false\n"
				+ "[branch \"true\"]\n"
				+ "  rebase = true\n");

		assertFalse(new BranchConfig(c
		assertFalse(new BranchConfig(c
		assertTrue(new BranchConfig(c
	}

	private static Config parse(String content) {
		final Config c = new Config(null);
		try {
			c.fromText(content);
		} catch (ConfigInvalidException e) {
			throw new RuntimeException(e);
		}
		return c;
	}
}
