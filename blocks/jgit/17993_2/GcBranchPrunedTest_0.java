
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class GcBasicPackingTest extends GcTestCase {
	@Test
	public void repackEmptyRepo_noPackCreated() throws IOException {
		gc.repack();
		assertEquals(0
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

		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(4
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
		fsTick();
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
}
