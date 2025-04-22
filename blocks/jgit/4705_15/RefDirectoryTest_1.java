package org.eclipse.jgit.storage.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.GC.RepoStatistics;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GCTest extends LocalDiskRepositoryTestCase {
	private TestRepository<FileRepository> tr;

	private FileRepository repo;

	private GC gc;

	private RepoStatistics stats;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repo = createWorkRepository();
		tr = new TestRepository<FileRepository>((repo));
		gc = new GC(repo);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testPackAllObjectsInOnePack() throws Exception {
		tr.branch("refs/heads/master").commit().add("A"
				.create();
		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(0
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(4
		assertEquals(1
	}

	@Test
	public void testPackRepoWithNoRefs() throws Exception {
		tr.commit().add("A"
		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(0
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testPack2Commits() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		bb.commit().add("A"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(8
		assertEquals(1
	}

	@Test
	public void testPackCommitsAndLooseOne() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevCommit first = bb.commit().add("A"
		bb.commit().add("A"
		tr.update("refs/heads/master"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(8
		assertEquals(2
	}

	@Test
	public void testNotPackTwice() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevCommit first = bb.commit().message("M").add("M"
		bb.commit().message("B").add("B"
		bb.commit().message("A").add("A"
		RevCommit second = tr.commit().parent(first).message("R").add("R"
				.create();
		tr.update("refs/tags/t1"

		Collection<PackFile> oldPacks = tr.getRepository().getObjectDatabase()
				.getPacks();
		assertEquals(0
		stats = gc.getStatistics();
		assertEquals(11
		assertEquals(0

		gc.setExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0

		Iterator<PackFile> pIt = repo.getObjectDatabase().getPacks().iterator();
		long c = pIt.next().getObjectCount();
		if (c == 9)
			assertEquals(2
		else {
			assertEquals(2
			assertEquals(9
		}
	}

	@Test
	public void testPackCommitsAndLooseOneNoReflog() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevCommit first = bb.commit().add("A"
		bb.commit().add("A"
		tr.update("refs/heads/master"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0

		FileUtils.delete(new File(repo.getDirectory()
				FileUtils.RETRY | FileUtils.SKIP_MISSING);
		FileUtils.delete(
				new File(repo.getDirectory()
				FileUtils.RETRY | FileUtils.SKIP_MISSING);
		gc.gc();

		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(4
		assertEquals(1
	}

	@Test
	public void testPackCommitsAndLooseOneWithPruneNow() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevCommit first = bb.commit().add("A"
		bb.commit().add("A"
		tr.update("refs/heads/master"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0
		gc.setExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(8
		assertEquals(2
	}

	@Test
	public void testPackCommitsAndLooseOneWithPruneNowNoReflog()
			throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevCommit first = bb.commit().add("A"
		bb.commit().add("A"
		tr.update("refs/heads/master"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0

		FileUtils.delete(new File(repo.getDirectory()
				FileUtils.RETRY | FileUtils.SKIP_MISSING);
		FileUtils.delete(
				new File(repo.getDirectory()
				FileUtils.RETRY | FileUtils.SKIP_MISSING);
		gc.setExpireAgeMillis(0);
		gc.gc();

		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(4
		assertEquals(1
	}

	@Test
	public void testIndexSavesObjects() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		bb.commit().add("A"
		bb.commit().add("A"
		stats = gc.getStatistics();
		assertEquals(9
		assertEquals(0
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(1
		assertEquals(8
		assertEquals(1
	}

	@Test
	public void testIndexSavesObjectsWithPruneNow() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		bb.commit().add("A"
		bb.commit().add("A"
		stats = gc.getStatistics();
		assertEquals(9
		assertEquals(0
		gc.setExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(8
		assertEquals(1
	}

	@Test
	public void testPruneNone() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		bb.commit().add("A"
		new File(repo.getDirectory()
				.delete();
		stats = gc.getStatistics();
		assertEquals(8
		gc.setExpireAgeMillis(0);
		gc.prune(Collections.<ObjectId> emptySet());
		stats = gc.getStatistics();
		assertEquals(8
		tr.blob("x");
		stats = gc.getStatistics();
		assertEquals(9
		gc.prune(Collections.<ObjectId> emptySet());
		stats = gc.getStatistics();
		assertEquals(8
	}
}
