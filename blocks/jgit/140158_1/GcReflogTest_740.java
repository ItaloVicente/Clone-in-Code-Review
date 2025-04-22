
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collections;
import java.util.Date;

import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.junit.Test;

public class GcPruneNonReferencedTest extends GcTestCase {
	@Test
	public void nonReferencedNonExpiredObject_notPruned() throws Exception {
		RevBlob a = tr.blob("a");
		gc.setExpire(new Date(lastModified(a)));
		gc.prune(Collections.<ObjectId> emptySet());
		assertTrue(repo.getObjectDatabase().has(a));
	}

	@Test
	public void nonReferencedExpiredObject_pruned() throws Exception {
		RevBlob a = tr.blob("a");
		gc.setExpireAgeMillis(0);
		fsTick();
		gc.prune(Collections.<ObjectId> emptySet());
		assertFalse(repo.getObjectDatabase().has(a));
	}

	@Test
	public void nonReferencedExpiredObjectTree_pruned() throws Exception {
		RevBlob a = tr.blob("a");
		RevTree t = tr.tree(tr.file("a"
		gc.setExpireAgeMillis(0);
		fsTick();
		gc.prune(Collections.<ObjectId> emptySet());
		assertFalse(repo.getObjectDatabase().has(t));
		assertFalse(repo.getObjectDatabase().has(a));
	}

	@Test
	public void nonReferencedObjects_onlyExpiredPruned() throws Exception {
		RevBlob a = tr.blob("a");
		gc.setExpire(new Date(lastModified(a) + 1));

		fsTick();
		RevBlob b = tr.blob("b");

		gc.prune(Collections.<ObjectId> emptySet());
		assertFalse(repo.getObjectDatabase().has(a));
		assertTrue(repo.getObjectDatabase().has(b));
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
		fsTick();
		gc.gc();
		stats = gc.getStatistics();
		assertNoEmptyFanoutDirectories();
		assertEquals(0
		assertEquals(8
		assertEquals(2
	}

	private void assertNoEmptyFanoutDirectories() {
		File[] fanout = repo.getObjectsDirectory().listFiles();
		for (File f : fanout) {
			if (f.isDirectory()) {
				String[] entries = f.list();
				if (entries == null || entries.length == 0) {
					assertFalse(
							"Found empty fanout directory "
									+ f.getAbsolutePath() + " after pruning"
							f.getName().length() == 2);
				}
			}
		}
	}
}
