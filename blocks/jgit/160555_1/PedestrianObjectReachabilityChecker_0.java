package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.CommitBuilder;
import org.junit.Before;
import org.junit.Test;

public class PedestrianObjectReachabilityTest
		extends LocalDiskRepositoryTestCase {

	PedestrianObjectReachabilityChecker getChecker(
			TestRepository<FileRepository> repository)
			throws Exception {
		return new PedestrianObjectReachabilityChecker(
				repository.getRevWalk().toObjectWalkWithSameObjects());
	}

	private TestRepository<FileRepository> repo;

	private AddressableRevCommit baseCommit;

	private AddressableRevCommit branchACommit;

	private AddressableRevCommit branchBCommit;

	private AddressableRevCommit mergeCommit;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		FileRepository db = createWorkRepository();
		repo = new TestRepository<>(db);
		prepareRepo();
	}

	@Test
	public void blob_in_base_reachable_from_branches() throws Exception {
		PedestrianObjectReachabilityChecker checker = getChecker(repo);

		RevObject baseBlob = baseCommit.blob;
		assertReachable("reachable from one branch"
				Arrays.asList(baseBlob)
		assertReachable("reachable from another branch"
				checker.areAllReachable(
				Arrays.asList(baseBlob)
	}

	@Test
	public void blob_reachable_from_owning_commit() throws Exception {
		PedestrianObjectReachabilityChecker checker = getChecker(repo);

		RevObject branchABlob = branchACommit.blob;
		assertReachable("reachable from itself"
				checker.areAllReachable(Arrays.asList(branchABlob)
						Stream.of(branchACommit.commit)));
	}

	@Test
	public void blob_in_branch_reachable_from_merge() throws Exception {
		PedestrianObjectReachabilityChecker checker = getChecker(repo);

		RevObject branchABlob = branchACommit.blob;
		assertReachable("reachable from merge"
				Arrays.asList(branchABlob)
	}

	@Test
	public void blob_unreachable_from_earlier_commit() throws Exception {
		PedestrianObjectReachabilityChecker checker = getChecker(repo);

		RevObject branchABlob = branchACommit.blob;
		assertUnreachable("unreachable from earlier commit"
				checker.areAllReachable(Arrays.asList(branchABlob)
						Stream.of(baseCommit.commit)));
	}

	@Test
	public void blob_unreachable_from_parallel_branch() throws Exception {
		PedestrianObjectReachabilityChecker checker = getChecker(repo);

		RevObject branchABlob = branchACommit.blob;
		assertUnreachable("reachable from another branch"
				checker.areAllReachable(Arrays.asList(branchABlob)
						Stream.of(branchBCommit.commit)));
	}

	private void prepareRepo()
			throws Exception {
		baseCommit = createCommit("base");
		branchACommit = createCommit("branchA"
		branchBCommit = createCommit("branchB"
		mergeCommit = createCommit("merge"

		repo.update("refs/heads/a"
		repo.update("refs/heads/b"
		repo.update("refs/heads/merge"
	}

	private AddressableRevCommit createCommit(String blobPath
			AddressableRevCommit... parents) throws Exception {
		RevBlob blob = repo.blob(blobPath + " content");
		CommitBuilder commitBuilder = repo.commit();
		for (int i = 0; i < parents.length; i++) {
			commitBuilder.parent(parents[i].commit);
		}
		commitBuilder.add(blobPath

		RevCommit commit = commitBuilder.create();
		return new AddressableRevCommit(commit
	}

	static class AddressableRevCommit {
		RevCommit commit;

		RevBlob blob;

		AddressableRevCommit(RevCommit commit
			this.commit = commit;
			this.blob = blob;
		}
	}

	private static void assertReachable(String msg
			Optional<RevObject> result) {
		assertFalse(msg
	}

	private static void assertUnreachable(String msg
			Optional<RevObject> result) {
		assertTrue(msg
	}

}
