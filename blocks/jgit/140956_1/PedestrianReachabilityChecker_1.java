package org.eclipse.jgit.revwalk;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.junit.Before;
import org.junit.Test;

public abstract class ReachabilityCheckerTestCase
		extends LocalDiskRepositoryTestCase {

	protected abstract ReachabilityChecker getChecker(
			TestRepository<FileRepository> repository) throws Exception;

	TestRepository<FileRepository> repo;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		FileRepository db = createWorkRepository();
		repo = new TestRepository<>(db);
	}

	@Test
	public void reachable() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(b1);
		RevCommit c1 = commit(a);
		RevCommit c2 = commit(c1);
		updateRef("refs/heads/checker"

		ReachabilityChecker checker = getChecker(repo);

		assertThat("reachable from one tip"
				checker.isReachable(a
		assertThat("reachable from another tip"
				checker.isReachable(a
		assertThat("reachable from itself"
				checker.isReachable(a
	}

	@Test
	public void reachable_merge() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(b1);
		RevCommit c1 = commit(a);
		RevCommit c2 = commit(c1);
		RevCommit merge = commit(c2
		updateRef("refs/heads/checker"

		ReachabilityChecker checker = getChecker(repo);

		assertThat("reachable through one branch"
				checker.isReachable(b1
		assertThat("reachable through another branch"
				checker.isReachable(c1
		assertThat("reachable
				checker.isReachable(a
	}

	@Test
	public void unreachable_isLaterCommit() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(b1);
		updateRef("refs/heads/checker"

		ReachabilityChecker checker = getChecker(repo);

		assertFalse("unreachable from the future"
				checker.isReachable(b2
	}

	@Test
	public void unreachable_differentBranch() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(b1);
		RevCommit c1 = commit(a);
		updateRef("refs/heads/checker"

		ReachabilityChecker checker = getChecker(repo);

		assertFalse("unreachable from different branch"
				checker.isReachable(c1
	}

	@Test
	public void reachable_longChain() throws Exception {
		RevCommit root = commit();
		RevCommit head = root;
		for (int i = 0; i < 10000; i++) {
			head = repo.commit(head);
		}
		repo.update("refs/heads/master"

		ReachabilityChecker checker = getChecker(repo);

		assertTrue(checker.isReachable(root
	}

	private RevCommit commit(RevCommit... parents) throws Exception {
		return repo.commit(parents);
	}

	private void updateRef(String ref
		repo.update(ref
	}
}
