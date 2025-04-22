package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Optional;

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
		RevCommit a = repo.commit().create();
		RevCommit b1 = repo.commit(a);
		RevCommit b2 = repo.commit(b1);
		RevCommit c1 = repo.commit(a);
		RevCommit c2 = repo.commit(c1);
		repo.update("refs/heads/checker"

		ReachabilityChecker checker = getChecker(repo);

		assertReachable("reachable from one tip"
				checker.areAllReachable(Arrays.asList(a)
		assertReachable("reachable from another tip"
				checker.areAllReachable(Arrays.asList(a)
		assertReachable("reachable from itself"
				checker.areAllReachable(Arrays.asList(a)
	}

	@Test
	public void reachable_merge() throws Exception {
		RevCommit a = repo.commit().create();
		RevCommit b1 = repo.commit(a);
		RevCommit b2 = repo.commit(b1);
		RevCommit c1 = repo.commit(a);
		RevCommit c2 = repo.commit(c1);
		RevCommit merge = repo.commit(c2
		repo.update("refs/heads/checker"

		ReachabilityChecker checker = getChecker(repo);

		assertReachable("reachable through one branch"
				checker.areAllReachable(Arrays.asList(b1)
						Arrays.asList(merge)));
		assertReachable("reachable through another branch"
				checker.areAllReachable(Arrays.asList(c1)
						Arrays.asList(merge)));
		assertReachable("reachable
				checker.areAllReachable(Arrays.asList(a)
						Arrays.asList(merge)));
	}

	@Test
	public void unreachable_isLaterCommit() throws Exception {
		RevCommit a = repo.commit().create();
		RevCommit b1 = repo.commit(a);
		RevCommit b2 = repo.commit(b1);
		repo.update("refs/heads/checker"

		ReachabilityChecker checker = getChecker(repo);

		assertUnreachable("unreachable from the future"
				checker.areAllReachable(Arrays.asList(b2)
	}

	@Test
	public void unreachable_differentBranch() throws Exception {
		RevCommit a = repo.commit().create();
		RevCommit b1 = repo.commit(a);
		RevCommit b2 = repo.commit(b1);
		RevCommit c1 = repo.commit(a);
		repo.update("refs/heads/checker"

		ReachabilityChecker checker = getChecker(repo);

		assertUnreachable("unreachable from different branch"
				checker.areAllReachable(Arrays.asList(c1)
	}

	@Test
	public void reachable_longChain() throws Exception {
		RevCommit root = repo.commit().create();
		RevCommit head = root;
		for (int i = 0; i < 10000; i++) {
			head = repo.commit(head);
		}
		repo.update("refs/heads/master"

		ReachabilityChecker checker = getChecker(repo);

		assertReachable("reachable with long chain in the middle"
				.areAllReachable(Arrays.asList(root)
	}

	private static void assertReachable(String msg
			Optional<RevCommit> result) {
		assertFalse(msg
	}

	private static void assertUnreachable(String msg
			Optional<RevCommit> result) {
		assertTrue(msg
	}

}
