
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class GcBasicPackingTest extends GcTestCase {
	@DataPoints
	public static boolean[] aggressiveValues = { true

	@Theory
	public void repackEmptyRepo_noPackCreated(boolean aggressive)
			throws IOException {
		configureGc(gc
		gc.repack();
		assertEquals(0
	}

	@Theory
	public void testPackRepoWithNoRefs(boolean aggressive) throws Exception {
		tr.commit().add("A"
		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(0
		configureGc(gc
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(0
		assertEquals(0
		assertEquals(0
	}

	@Theory
	public void testPack2Commits(boolean aggressive) throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().add("A"
		bb.commit().add("A"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0
		configureGc(gc
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(8
		assertEquals(1
		assertEquals(2
	}

	@Theory
	public void testPackAllObjectsInOnePack(boolean aggressive)
			throws Exception {
		tr.branch("refs/heads/master").commit().add("A"
				.create();
		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(0
		configureGc(gc
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(4
		assertEquals(1
		assertEquals(1

		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(4
		assertEquals(1
		assertEquals(1
	}

	@Theory
	public void testPackCommitsAndLooseOne(boolean aggressive)
			throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevCommit first = bb.commit().add("A"
		bb.commit().add("A"
		tr.update("refs/heads/master"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0
		configureGc(gc
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(8
		assertEquals(2
		assertEquals(1
	}

	@Theory
	public void testNotPackTwice(boolean aggressive) throws Exception {
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
		configureGc(gc
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0

		List<PackFile> packs = new ArrayList<>(
				repo.getObjectDatabase().getPacks());
		assertEquals(11
	}

	@Test
	public void testDonePruneTooYoungPacks() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().message("M").add("M"

		String tempRef = "refs/heads/soon-to-be-unreferenced";
		BranchBuilder bb2 = tr.branch(tempRef);
		bb2.commit().message("M").add("M"

		gc.setExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(4
		assertEquals(1
		File oldPackfile = tr.getRepository().getObjectDatabase().getPacks()
				.iterator().next().getPackFile();

		fsTick();

		RefUpdate update = tr.getRepository().getRefDatabase().newUpdate(tempRef
		update.setForceUpdate(true);
		update.delete();

		bb.commit().message("B").add("B"

		gc.setExpire(new Date(oldPackfile.lastModified() - 1));
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(10
		assertEquals(2

		gc.setExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(10
		assertEquals(2

		gc.setPackExpireAgeMillis(0);

		gc.setExpireAgeMillis(-1);

		gc.gc();
		stats = gc.getStatistics();
		assertEquals(1
		assertEquals(6
		assertEquals(1
	}

	@Test
	public void testImmediatePruning() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().message("M").add("M"

		String tempRef = "refs/heads/soon-to-be-unreferenced";
		BranchBuilder bb2 = tr.branch(tempRef);
		bb2.commit().message("M").add("M"

		gc.setExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();

		fsTick();

		RefUpdate update = tr.getRepository().getRefDatabase().newUpdate(tempRef
		update.setForceUpdate(true);
		update.delete();

		bb.commit().message("B").add("B"

		FileBasedConfig config = repo.getConfig();
		config.setString(ConfigConstants.CONFIG_GC_SECTION
			ConfigConstants.CONFIG_KEY_PRUNEEXPIRE
		config.save();

		gc.setPackExpireAgeMillis(0);

		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(6
		assertEquals(1
	}

	@Test
	public void testPreserveAndPruneOldPacks() throws Exception {
		testPreserveOldPacks();
		configureGc(gc
		gc.gc();

		assertFalse(repo.getObjectDatabase().getPreservedDirectory().exists());
	}

	private void testPreserveOldPacks() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().message("P").add("P"

		gc.setExpireAgeMillis(0);
		gc.gc();
		File oldPackfile = tr.getRepository().getObjectDatabase().getPacks()
				.iterator().next().getPackFile();
		assertTrue(oldPackfile.exists());

		fsTick();
		bb.commit().message("B").add("B"

		gc.setPackExpireAgeMillis(0);
		configureGc(gc
		gc.gc();

		File oldPackDir = repo.getObjectDatabase().getPreservedDirectory();
		String oldPackFileName = oldPackfile.getName();
		String oldPackName = oldPackFileName.substring(0
		File preservePackFile = new File(oldPackDir
		assertTrue(preservePackFile.exists());
	}

	private PackConfig configureGc(GC myGc
		PackConfig pconfig = new PackConfig(repo);
		if (aggressive) {
			pconfig.setDeltaSearchWindowSize(250);
			pconfig.setMaxDeltaDepth(250);
			pconfig.setReuseObjects(false);
		} else
			pconfig = new PackConfig(repo);
		myGc.setPackConfig(pconfig);
		return pconfig;
	}
}
