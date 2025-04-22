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
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.PackIndex.MutableEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GCTest extends LocalDiskRepositoryTestCase {
	private TestRepository<FileRepository> tr;

	private GC gc;

	private FileRepository repo;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repo = createBareRepository();
		tr = new TestRepository<FileRepository>((repo));
		gc = new GC(repo);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testPackAllObjectsInOnePack() throws Exception {
		RevCommit create = tr.commit().add("A"
		RefUpdate ru = repo.updateRef("refs/heads/master");
		ru.setNewObjectId(create.getId());
		ru.forceUpdate();
		assertEquals(4
		assertEquals(0
		gc.gc();
		assertEquals(0
		assertEquals(4
		assertEquals(1
	}

	@Test
	public void testPackRepoWithNoRefs() throws Exception {
		tr.commit().add("A"
		assertEquals(4
		assertEquals(0
		gc.gc();
		assertEquals(4
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testPackCommits() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		RevCommit second = bb.commit().add("A"
		RefUpdate ru = repo.updateRef("refs/heads/master");
		ru.setNewObjectId(second.getId());
		ru.update();

		assertEquals(8
		assertEquals(0
		gc.gc();
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
		RefUpdate ru = repo.updateRef("refs/heads/master");
		ru.setNewObjectId(first.getId());
		ru.forceUpdate();

		assertEquals(8
		assertEquals(0
		gc.gc();
		assertEquals(4
		assertEquals(4
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
		}
		return ret;
	}
}
