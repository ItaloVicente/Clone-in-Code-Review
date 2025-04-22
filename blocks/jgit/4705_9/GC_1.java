package org.eclipse.jgit.storage.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.PackIndex.MutableEntry;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GCTest extends LocalDiskRepositoryTestCase {
	private TestRepository<FileRepository> tr;
	private FileRepository repo;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repo = createWorkRepository();
		tr = new TestRepository<FileRepository>((repo));
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testPackAllObjectsInOnePack() throws Exception {
		tr.branch("refs/heads/master").commit().add("A"
				.create();
		assertEquals(4
		assertEquals(0
		GC.gc(null
		assertEquals(0
		assertEquals(4
		assertEquals(1
	}

	@Test
	public void testPackRepoWithNoRefs() throws Exception {
		tr.commit().add("A"
		assertEquals(4
		assertEquals(0
		GC.gc(null
		assertEquals(4
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testPack2Commits() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		bb.commit().add("A"

		assertEquals(8
		assertEquals(0
		GC.gc(null
		assertEquals(0
		assertEquals(8
		assertEquals(1
	}

	@Test
	public void testPackCommitsAndLooseOne() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevCommit first = bb.commit().add("A"
		bb.commit().add("A"
				.create();
		tr.update("refs/heads/master"

		assertEquals(8
		assertEquals(0
		GC.gc(null
		assertEquals(0
		assertEquals(8
		assertEquals(2
	}

	@Test
	public void testPackCommitsAndLooseOneNoReflog() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevCommit first = bb.commit().add("A"
		bb.commit().add("A"
		tr.update("refs/heads/master"

		assertEquals(8
		assertEquals(0

		FileUtils.delete(new File(repo.getDirectory()
				FileUtils.RETRY | FileUtils.SKIP_MISSING);
		FileUtils.delete(
				new File(repo.getDirectory()
				FileUtils.RETRY | FileUtils.SKIP_MISSING);
		GC.gc(null

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

		assertEquals(8
		assertEquals(0
		GC.gc(null
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

		assertEquals(8
		assertEquals(0

		FileUtils.delete(new File(repo.getDirectory()
				FileUtils.RETRY | FileUtils.SKIP_MISSING);
		FileUtils.delete(
				new File(repo.getDirectory()
				FileUtils.RETRY | FileUtils.SKIP_MISSING);
		GC.gc(null

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
		assertEquals(9
		assertEquals(0
		GC.gc(null
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
		assertEquals(9
		assertEquals(0
		GC.gc(null
		assertEquals(0
		assertEquals(8
		assertEquals(1
	}

	public Set<String> looseObjectIDs() {
		Set<String> ret = new HashSet<String>();
		for (File parent : repo.getObjectsDirectory().listFiles())
			if (parent.getName().matches("[0-9a-fA-F]{2}"))
				for (File obj : parent.listFiles())
					if (obj.getName().matches("[0-9a-fA-F]{38}"))
						ret.add(parent.getName() + obj.getName());
		return ret;
	}

	public Set<String> packedObjectIDs() throws IOException {
		Set<String> ret = new HashSet<String>();
		repo.scanForRepoChanges();
		Iterator<PackFile> pi = repo.getObjectDatabase().getPacks().iterator();
		while (pi.hasNext()) {
			PackFile pf = pi.next();
			Iterator<MutableEntry> ei = pf.iterator();
			while (ei.hasNext()) {
				MutableEntry enty = ei.next();
				ret.add(enty.name());
			}
			pf.close();
		}
		return ret;
	}
}
