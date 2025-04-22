package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GcObjectIndexTest extends GcTestCase {

	@Test
	public void gc_2commits_noSizeLimit_blobsInIndex() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevBlob blobA1 = tr.blob("7-bytes");
		RevBlob blobA2 = tr.blob("11-bytes xx");
		RevBlob blobB1 = tr.blob("B");
		RevBlob blobB2 = tr.blob("B2");
		bb.commit().add("A"
		bb.commit().add("A"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0
		configureGc(gc
		gc.gc().get();

		stats = gc.getStatistics();
		assertEquals(1
		assertEquals(4

		assertTrue(getOnlyPack(repo).hasObjSizeIndex());
		Pack pack = getOnlyPack(repo);
		assertEquals(7
		assertEquals(11
		assertEquals(1
		assertEquals(2
	}

	@Test
	public void gc_2commits_sizeLimit_biggerBlobsInIndex() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevBlob blobA1 = tr.blob("7-bytes");
		RevBlob blobA2 = tr.blob("11-bytes xx");
		RevBlob blobB1 = tr.blob("B");
		RevBlob blobB2 = tr.blob("B2");
		bb.commit().add("A"
		bb.commit().add("A"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0
		configureGc(gc
		gc.gc().get();

		stats = gc.getStatistics();
		assertEquals(1
		assertEquals(2

		assertTrue(getOnlyPack(repo).hasObjSizeIndex());
		Pack pack = getOnlyPack(repo);
		assertEquals(7
		assertEquals(11
		assertEquals(-1
		assertEquals(-1
	}

	@Test
	public void gc_2commits_disableSizeIdx_noIdx() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevBlob blobA1 = tr.blob("7-bytes");
		RevBlob blobA2 = tr.blob("11-bytes xx");
		RevBlob blobB1 = tr.blob("B");
		RevBlob blobB2 = tr.blob("B2");
		bb.commit().add("A"
		bb.commit().add("A"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0
		configureGc(gc
		gc.gc().get();


		stats = gc.getStatistics();
		assertEquals(1
		assertEquals(0
	}

	@Test
	public void gc_alreadyPacked_noChanges()
			throws Exception {
		tr.branch("refs/heads/master").commit().add("A"
				.create();
		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(0
		configureGc(gc
		gc.gc().get();

		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(1
		assertTrue(getOnlyPack(repo).hasObjSizeIndex());
		assertEquals(2

		gc.gc().get();
		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(1
		assertTrue(getOnlyPack(repo).hasObjSizeIndex());
		assertEquals(2
	}

	@Test
	public void gc_twoReachableCommits_oneUnreachable_twoPacks()
			throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		RevCommit first = bb.commit().add("A"
		bb.commit().add("A"
		tr.update("refs/heads/master"

		stats = gc.getStatistics();
		assertEquals(8
		assertEquals(0
		configureGc(gc
		gc.gc().get();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(8
		assertEquals(2
		assertEquals(4
	}

	@Test
	public void gc_preserved_objSizeIdxIsPreserved() throws Exception {
		Collection<Pack> oldPacks = preserveOldPacks();
		assertEquals(1
		PackFile preserved = oldPacks.iterator().next().getPackFile()
				.create(PackExt.OBJECT_SIZE_INDEX)
				.createPreservedForDirectory(
						repo.getObjectDatabase().getPreservedDirectory());
		assertTrue(preserved.exists());
	}

	@Test
	public void gc_preserved_prune_noPreserves() throws Exception {
		preserveOldPacks();
		configureGc(gc
		gc.gc().get();

		assertFalse(repo.getObjectDatabase().getPreservedDirectory().exists());
	}

	private Collection<Pack> preserveOldPacks() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().message("P").add("P"

		configureGc(gc
		gc.setExpireAgeMillis(0);
		gc.gc().get();
		Collection<Pack> oldPacks = tr.getRepository().getObjectDatabase()
				.getPacks();
		PackFile oldPackfile = oldPacks.iterator().next().getPackFile();
		assertTrue(oldPackfile.exists());

		fsTick();
		bb.commit().message("B").add("B"

		gc.setPackExpireAgeMillis(0);
		configureGc(gc
		gc.gc().get();

		File preservedPackFile = oldPackfile.createPreservedForDirectory(
				repo.getObjectDatabase().getPreservedDirectory());
		assertTrue(preservedPackFile.exists());
		return oldPacks;
	}

	@Ignore
	public void testPruneAndRestoreOldPacks() throws Exception {
		String tempRef = "refs/heads/soon-to-be-unreferenced";
		BranchBuilder bb = tr.branch(tempRef);
		bb.commit().add("A"

		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(0

		configureGc(gc
		gc.setExpireAgeMillis(0);
		gc.setPackExpireAgeMillis(0);
		gc.gc().get();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(4
		assertEquals(1

		RefUpdate update = tr.getRepository().getRefDatabase().newUpdate(tempRef
		update.setForceUpdate(true);
		RefUpdate.Result result = update.delete();
		assertEquals(RefUpdate.Result.FORCED

		fsTick();

		configureGc(gc
		gc.gc().get();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(0
		assertEquals(0

		update = tr.getRepository().getRefDatabase().newUpdate(tempRef
		update.setNewObjectId(objectId);
		update.setExpectedOldObjectId(null);
		result = update.update();
		assertEquals(RefUpdate.Result.NEW

		stats = gc.getStatistics();
		assertEquals(4
		assertEquals(1
	}

	private PackConfig configureGc(GC myGc
		PackConfig pconfig = new PackConfig(repo);
		pconfig.setMinBytesForObjSizeIndex(minSize);
		myGc.setPackConfig(pconfig);
		return pconfig;
	}

	private Pack getOnlyPack(FileRepository fileRepo)
			throws IOException {
		Collection<Pack> packs = fileRepo.getObjectDatabase().getPacks();
		if (packs.isEmpty() || packs.size() > 1) {
			throw new IOException("More than one pack");
		}

		return packs.iterator().next();
	}
}
