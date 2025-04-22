package org.eclipse.jgit.internal.storage.dfs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class DfsReftableDatabaseTest {
	private TestRepository<InMemoryRepository> git;

	private InMemoryRepository repo;

	private DfsReftableDatabase refdb;

	@Before
	public void setUp() throws Exception {
		DfsRepositoryDescription desc = new DfsRepositoryDescription("test");
		git = new TestRepository<>(new InMemoryRepository(desc));
		repo = git.getRepository();

		RevCommit zero = git.commit().message("0").create();
		RevCommit one = git.commit().message("1").create();

		git.update("branchX"
		git.update("master"
		git.update("HEAD"

		refdb = (DfsReftableDatabase) repo.getRefDatabase();
	}

	@Test
	public void testHasRefs() throws Exception {
		Map<String
		expectations.put("HEAD"
		expectations.put("refs/heads/master"
		expectations.put("refs/heads/branchX"

		assertTrue(refdb.hasRefs(expectations));
	}

	@Test
	public void testHasRefs_subset() throws Exception {
		Map<String
		expectations.put("refs/heads/branchX"
		expectations.put("refs/heads/master"

		assertTrue(refdb.hasRefs(expectations));
	}

	@Test
	public void testHasRefs_moreRecent() throws Exception {
		Map<String
		expectations.put("refs/heads/branchX"
		expectations.put("refs/heads/master"

		assertTrue(refdb.hasRefs(expectations));
	}

	@Test
	public void testCannotServe_tooOld() throws Exception {
		Map<String
		expectations.put("refs/heads/master"
		expectations.put("refs/heads/branchX"

		assertFalse(refdb.hasRefs(expectations));
	}

	@Test
	public void testCannotServe_unknownBranch() throws Exception {
		Map<String
		expectations.put("refs/heads/master"
		expectations.put("refs/heads/unknown"

		assertFalse(refdb.hasRefs(expectations));
	}

	@Test
	public void testCannotServe_nonQualifiedName() throws Exception {
		Map<String
		expectations.put("master"

		assertFalse(refdb.hasRefs(expectations));
	}

}
