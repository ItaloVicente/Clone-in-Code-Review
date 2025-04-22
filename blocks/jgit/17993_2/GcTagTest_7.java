
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collections;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class GcReflogTest extends GcTestCase {
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
		fsTick();
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

	@Test
	public void testPackRepoWithCorruptReflog() throws Exception {
		RefUpdate ru = repo.updateRef(Constants.HEAD);
		ru.link("refs/to/garbage");
		tr.branch("refs/heads/master").commit().add("A"
				.create();
		Git.wrap(repo).checkout().setName("refs/heads/master").call();
		gc.gc();
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
}
