
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;

public class BranchTrackingStatusTest extends RepositoryTestCase {
	private TestRepository<Repository> util;

	protected RevWalk rw;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		util = new TestRepository<>(db);
		StoredConfig config = util.getRepository().getConfig();
		config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REMOTE
		config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGE
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				"fetch"
	}

	@Test
	public void shouldWorkInNormalCase() throws Exception {
		RevCommit remoteTracking = util.branch("refs/remotes/origin/master")
				.commit().create();
		util.branch("master").commit().parent(remoteTracking).create();
		util.branch("master").commit().create();

		BranchTrackingStatus status = BranchTrackingStatus.of(
				util.getRepository()
		assertEquals(2
		assertEquals(0
		assertEquals("refs/remotes/origin/master"
				status.getRemoteTrackingBranch());
	}

	@Test
	public void shouldWorkWithoutMergeBase() throws Exception {
		util.branch("refs/remotes/origin/master").commit().create();
		util.branch("master").commit().create();

		BranchTrackingStatus status = BranchTrackingStatus.of(util.getRepository()
		assertEquals(1
		assertEquals(1
	}

	@Test
	public void shouldReturnNullWhenBranchDoesntExist() throws Exception {
		BranchTrackingStatus status = BranchTrackingStatus.of(
				util.getRepository()

		assertNull(status);
	}
}
