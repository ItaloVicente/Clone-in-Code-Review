package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.GC;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.GC_REST;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.INSERT;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.UNREACHABLE_GARBAGE;
import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;
import static org.eclipse.jgit.internal.storage.pack.PackExt.REFTABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource;
import org.eclipse.jgit.internal.storage.dfs.DfsRefDatabase;
import org.eclipse.jgit.internal.storage.reftable.RefCursor;
import org.eclipse.jgit.internal.storage.reftable.ReftableConfig;
import org.eclipse.jgit.internal.storage.reftable.ReftableReader;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter;
import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.util.SystemReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DfsGarbageCollectorTest {
	private TestRepository<InMemoryRepository> git;
	private InMemoryRepository repo;
	private DfsObjDatabase odb;
	private MockSystemReader mockSystemReader;

	@Before
	public void setUp() throws IOException {
		DfsRepositoryDescription desc = new DfsRepositoryDescription("test");
		git = new TestRepository<>(new InMemoryRepository(desc));
		repo = git.getRepository();
		odb = repo.getObjectDatabase();
		mockSystemReader = new MockSystemReader();
		SystemReader.setInstance(mockSystemReader);
	}

	@After
	public void tearDown() {
		SystemReader.setInstance(null);
	}

	@Test
	public void testCollectionWithNoGarbage() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		assertTrue("commit0 reachable"
		assertTrue("commit1 reachable"

		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			assertEquals(INSERT
		}

		gcNoTtl();

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"
	}

	@Test
	public void testRacyNoReusePrefersSmaller() throws Exception {
		StringBuilder msg = new StringBuilder();
		for (int i = 0; i < 100; i++) {
			msg.append(i).append(": i am a teapot\n");
		}
		RevBlob a = git.blob(msg.toString());
		RevCommit c0 = git.commit()
				.add("tea"
				.message("0")
				.create();

		msg.append("short and stout\n");
		RevBlob b = git.blob(msg.toString());
		RevCommit c1 = git.commit().parent(c0).tick(1)
				.add("tea"
				.message("1")
				.create();
		git.update("master"

		PackConfig cfg = new PackConfig();
		cfg.setReuseObjects(false);
		cfg.setReuseDeltas(false);
		cfg.setDeltaCompress(false);
		cfg.setThreads(1);
		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		gc.setPackConfig(cfg);
		run(gc);

		assertEquals(1
		DfsPackDescription large = odb.getPacks()[0].getPackDescription();
		assertSame(PackSource.GC

		cfg.setDeltaCompress(true);
		gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		gc.setPackConfig(cfg);
		run(gc);

		assertEquals(1
		DfsPackDescription small = odb.getPacks()[0].getPackDescription();
		assertSame(PackSource.GC
		assertTrue(
				"delta compression pack is smaller"
				small.getFileSize(PACK) < large.getFileSize(PACK));
		assertTrue(
				"large pack is older"
				large.getLastModified() < small.getLastModified());

		odb.commitPack(Collections.singleton(large)
		odb.clearCache();
		assertEquals(2

		gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		run(gc);

		assertEquals(1
		DfsPackDescription rebuilt = odb.getPacks()[0].getPackDescription();
		assertEquals(small.getFileSize(PACK)
	}

	@Test
	public void testCollectionWithGarbage() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		assertTrue("commit0 reachable"
		assertFalse("commit1 garbage"
		gcNoTtl();

		assertEquals(2
		DfsPackFile gc = null;
		DfsPackFile garbage = null;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				gc = pack;
			} else if (d.getPackSource() == UNREACHABLE_GARBAGE) {
				garbage = pack;
			} else {
				fail("unexpected " + d.getPackSource());
			}
		}

		assertNotNull("created GC pack"
		assertTrue(isObjectInPack(commit0

		assertNotNull("created UNREACHABLE_GARBAGE pack"
		assertTrue(isObjectInPack(commit1
	}

	@Test
	public void testCollectionWithGarbageAndGarbagePacksPurged()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		gcWithTtl();
		assertEquals(2
		boolean gcPackFound = false;
		boolean garbagePackFound = false;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				gcPackFound = true;
				assertTrue("has commit0"
				assertFalse("no commit1"
			} else if (d.getPackSource() == UNREACHABLE_GARBAGE) {
				garbagePackFound = true;
				assertFalse("no commit0"
				assertTrue("has commit1"
			} else {
				fail("unexpected " + d.getPackSource());
			}
		}
		assertTrue("gc pack found"
		assertTrue("garbage pack found"

		gcWithTtl();
		DfsPackFile[] packs = odb.getPacks();
		assertEquals(1

		assertEquals(GC
		assertTrue("has commit0"
		assertFalse("no commit1"
	}

	@Test
	public void testCollectionWithGarbageAndRereferencingGarbage()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		gcWithTtl();
		assertEquals(2
		boolean gcPackFound = false;
		boolean garbagePackFound = false;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				gcPackFound = true;
				assertTrue("has commit0"
				assertFalse("no commit1"
			} else if (d.getPackSource() == UNREACHABLE_GARBAGE) {
				garbagePackFound = true;
				assertFalse("no commit0"
				assertTrue("has commit1"
			} else {
				fail("unexpected " + d.getPackSource());
			}
		}
		assertTrue("gc pack found"
		assertTrue("garbage pack found"

		git.update("master"

		gcWithTtl();
		DfsPackFile[] packs = odb.getPacks();
		assertEquals(1

		assertEquals(GC
		assertTrue("has commit0"
		assertTrue("has commit1"
	}

	@Test
	public void testCollectionWithPureGarbageAndGarbagePacksPurged()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();

		gcWithTtl();
		DfsPackFile[] packs = odb.getPacks();
		assertEquals(1

		assertEquals(UNREACHABLE_GARBAGE
		assertTrue("has commit0"
		assertTrue("has commit1"

		gcWithTtl();
		assertEquals(0
	}

	@Test
	public void testCollectionWithPureGarbageAndRereferencingGarbage()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();

		gcWithTtl();
		DfsPackFile[] packs = odb.getPacks();
		assertEquals(1

		DfsPackDescription pack = packs[0].getPackDescription();
		assertEquals(UNREACHABLE_GARBAGE
		assertTrue("has commit0"
		assertTrue("has commit1"

		git.update("master"

		gcWithTtl();
		packs = odb.getPacks();
		assertEquals(1

		pack = packs[0].getPackDescription();
		assertEquals(GC
		assertTrue("has commit0"
		assertFalse("no commit1"
	}

	@Test
	public void testCollectionWithGarbageCoalescence() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		for (int i = 0; i < 3; i++) {
			commit1 = commit().message("g" + i).parent(commit1).create();

			gcNoTtl();
			assertEquals(1
		}
	}

	@Test
	public void testCollectionWithGarbageNoCoalescence() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		for (int i = 0; i < 3; i++) {
			commit1 = commit().message("g" + i).parent(commit1).create();

			DfsGarbageCollector gc = new DfsGarbageCollector(repo);
			gc.setCoalesceGarbageLimit(0);
			gc.setGarbageTtl(0
			run(gc);
			assertEquals(1 + i
		}
	}

	@Test
	public void testCollectionWithGarbageCoalescenceWithShortTtl()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		for (int i = 0; i < 100; i++) {
			mockSystemReader.tick(60);
			commit1 = commit().message("g" + i).parent(commit1).create();

			DfsGarbageCollector gc = new DfsGarbageCollector(repo);
			gc.setGarbageTtl(1
			run(gc);

			int count = countPacks(UNREACHABLE_GARBAGE);
			assertTrue("Garbage pack count should not exceed 4
					+ count
		}
	}

	@Test
	public void testCollectionWithGarbageCoalescenceWithLongTtl()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		for (int i = 0; i < 100; i++) {
			mockSystemReader.tick(3600);
			commit1 = commit().message("g" + i).parent(commit1).create();

			DfsGarbageCollector gc = new DfsGarbageCollector(repo);
			gc.setGarbageTtl(2
			run(gc);

			int count = countPacks(UNREACHABLE_GARBAGE);
			assertTrue("Garbage pack count should not exceed 3
					+ count
		}
	}

	@Test
	public void testEstimateGcPackSizeInNewRepo() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		long inputPacksSize = 32;
		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			assertEquals(INSERT
			inputPacksSize += pack.getPackDescription().getFileSize(PACK) - 32;
		}

		gcNoTtl();

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(GC
		assertEquals(inputPacksSize
				pack.getPackDescription().getEstimatedPackSize());
	}

	@Test
	public void testEstimateGcPackSizeWithAnExistingGcPack() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		gcNoTtl();

		RevCommit commit2 = commit().message("2").parent(commit1).create();
		git.update("master"

		assertEquals(2
		boolean gcPackFound = false;
		boolean insertPackFound = false;
		long inputPacksSize = 32;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				gcPackFound = true;
			} else if (d.getPackSource() == INSERT) {
				insertPackFound = true;
			} else {
				fail("unexpected " + d.getPackSource());
			}
			inputPacksSize += d.getFileSize(PACK) - 32;
		}
		assertTrue(gcPackFound);
		assertTrue(insertPackFound);

		gcNoTtl();

		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(GC
		assertEquals(inputPacksSize
				pack.getPackDescription().getEstimatedPackSize());
	}

	@Test
	public void testEstimateGcRestPackSizeInNewRepo() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("refs/notes/note1"

		long inputPacksSize = 32;
		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			assertEquals(INSERT
			inputPacksSize += pack.getPackDescription().getFileSize(PACK) - 32;
		}

		gcNoTtl();

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(GC_REST
		assertEquals(inputPacksSize
				pack.getPackDescription().getEstimatedPackSize());
	}

	@Test
	public void testEstimateGcRestPackSizeWithAnExistingGcPack()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("refs/notes/note1"

		gcNoTtl();

		RevCommit commit2 = commit().message("2").parent(commit1).create();
		git.update("refs/notes/note2"

		assertEquals(2
		boolean gcRestPackFound = false;
		boolean insertPackFound = false;
		long inputPacksSize = 32;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC_REST) {
				gcRestPackFound = true;
			} else if (d.getPackSource() == INSERT) {
				insertPackFound = true;
			} else {
				fail("unexpected " + d.getPackSource());
			}
			inputPacksSize += d.getFileSize(PACK) - 32;
		}
		assertTrue(gcRestPackFound);
		assertTrue(insertPackFound);

		gcNoTtl();

		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(GC_REST
		assertEquals(inputPacksSize
				pack.getPackDescription().getEstimatedPackSize());
	}

	@Test
	public void testEstimateGcPackSizesWithGcAndGcRestPacks() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		git.update("head"
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("refs/notes/note1"

		gcNoTtl();

		RevCommit commit2 = commit().message("2").parent(commit1).create();
		git.update("refs/notes/note2"

		assertEquals(3
		boolean gcPackFound = false;
		boolean gcRestPackFound = false;
		boolean insertPackFound = false;
		long gcPackSize = 0;
		long gcRestPackSize = 0;
		long insertPackSize = 0;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				gcPackFound = true;
				gcPackSize = d.getFileSize(PACK);
			} else if (d.getPackSource() == GC_REST) {
				gcRestPackFound = true;
				gcRestPackSize = d.getFileSize(PACK);
			} else if (d.getPackSource() == INSERT) {
				insertPackFound = true;
				insertPackSize = d.getFileSize(PACK);
			} else {
				fail("unexpected " + d.getPackSource());
			}
		}
		assertTrue(gcPackFound);
		assertTrue(gcRestPackFound);
		assertTrue(insertPackFound);

		gcNoTtl();

		assertEquals(2
		gcPackFound = false;
		gcRestPackFound = false;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				gcPackFound = true;
				assertEquals(gcPackSize + insertPackSize - 32
						pack.getPackDescription().getEstimatedPackSize());
			} else if (d.getPackSource() == GC_REST) {
				gcRestPackFound = true;
				assertEquals(gcRestPackSize + insertPackSize - 32
						pack.getPackDescription().getEstimatedPackSize());
			} else {
				fail("unexpected " + d.getPackSource());
			}
		}
		assertTrue(gcPackFound);
		assertTrue(gcRestPackFound);
	}

	@Test
	public void testEstimateUnreachableGarbagePackSize() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		assertTrue("commit0 reachable"
		assertFalse("commit1 garbage"

		long packSize0 = 0;
		long packSize1 = 0;
		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			assertEquals(INSERT
			if (isObjectInPack(commit0
				packSize0 = d.getFileSize(PACK);
			} else if (isObjectInPack(commit1
				packSize1 = d.getFileSize(PACK);
			} else {
				fail("expected object not found in the pack");
			}
		}

		gcNoTtl();

		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				assertEquals(packSize0 + packSize1 - 32
						d.getEstimatedPackSize());
			} else if (d.getPackSource() == UNREACHABLE_GARBAGE) {
				assertEquals(packSize1
			} else {
				fail("unexpected " + d.getPackSource());
			}
		}
	}

	@Test
	public void testSinglePackForAllRefs() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		git.update("head"
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("refs/notes/note1"

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		gc.getPackConfig().setSinglePack(true);
		run(gc);
		assertEquals(1

		gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		gc.getPackConfig().setSinglePack(false);
		run(gc);
		assertEquals(2
	}

	@SuppressWarnings("boxing")
	@Test
	public void producesNewReftable() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();

		BatchRefUpdate bru = git.getRepository().getRefDatabase()
				.newBatchUpdate();
		bru.addCommand(new ReceiveCommand(ObjectId.zeroId()
		for (int i = 1; i <= 5100; i++) {
			bru.addCommand(new ReceiveCommand(ObjectId.zeroId()
					String.format("refs/pulls/%04d"
		}
		try (RevWalk rw = new RevWalk(git.getRepository())) {
			bru.execute(rw
		}

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		run(gc);

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		DfsPackDescription desc = pack.getPackDescription();
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"

		assertTrue(desc.hasFileExt(REFTABLE));
		ReftableWriter.Stats stats = desc.getReftableStats();
		assertNotNull(stats);
		assertTrue(stats.totalBytes() > 0);
		assertEquals(5101
		assertEquals(1
		assertEquals(1

		DfsReftable table = new DfsReftable(DfsBlockCache.getInstance()
		try (DfsReader ctx = odb.newReader();
				ReftableReader rr = table.open(ctx);
				RefCursor rc = rr.seekRef("refs/pulls/5100")) {
			assertTrue(rc.next());
			assertEquals(commit0
			assertFalse(rc.next());
		}
	}

	@Test
	public void leavesNonGcReftablesIfNotConfigured() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(master

		DfsPackDescription t1 = odb.newPack(INSERT);
		try (DfsOutputStream out = odb.writeFile(t1
			new ReftableWriter().begin(out).finish();
			t1.addFileExt(REFTABLE);
		}
		odb.commitPack(Collections.singleton(t1)

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(null);
		run(gc);

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		DfsPackDescription desc = pack.getPackDescription();
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"

		DfsReftable[] tables = odb.getReftables();
		assertEquals(2
		assertEquals(t1
	}

	@Test
	public void prunesNonGcReftables() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(master

		DfsPackDescription t1 = odb.newPack(INSERT);
		try (DfsOutputStream out = odb.writeFile(t1
			new ReftableWriter().begin(out).finish();
			t1.addFileExt(REFTABLE);
		}
		odb.commitPack(Collections.singleton(t1)
		odb.clearCache();

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		run(gc);

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		DfsPackDescription desc = pack.getPackDescription();
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"

		DfsReftable[] tables = odb.getReftables();
		assertEquals(1
		assertEquals(desc
		assertTrue(desc.hasFileExt(REFTABLE));
	}

	@Test
	public void compactsReftables() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(master

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		run(gc);

		DfsPackDescription t1 = odb.newPack(INSERT);
		Ref next = new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE
				"refs/heads/next"
		try (DfsOutputStream out = odb.writeFile(t1
			ReftableWriter w = new ReftableWriter();
			w.setMinUpdateIndex(42);
			w.setMaxUpdateIndex(42);
			w.begin(out);
			w.sortAndWriteRefs(Collections.singleton(next));
			w.finish();
			t1.addFileExt(REFTABLE);
			t1.setReftableStats(w.getStats());
		}
		odb.commitPack(Collections.singleton(t1)

		gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		run(gc);

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		DfsPackDescription desc = pack.getPackDescription();
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"

		DfsReftable[] tables = odb.getReftables();
		assertEquals(1
		assertEquals(desc
		assertTrue(desc.hasFileExt(REFTABLE));

		DfsReftable table = new DfsReftable(DfsBlockCache.getInstance()
		try (DfsReader ctx = odb.newReader();
				ReftableReader rr = table.open(ctx);
				RefCursor rc = rr.allRefs()) {
			assertEquals(1
			assertEquals(42

			assertTrue(rc.next());
			assertEquals(master
			assertEquals(commit1

			assertTrue(rc.next());
			assertEquals(next.getName()
			assertEquals(commit0

			assertFalse(rc.next());
		}
	}

	@Test
	public void reftableWithoutTombstoneResurrected() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		String NEXT = "refs/heads/next";
		DfsRefDatabase refdb = (DfsRefDatabase)repo.getRefDatabase();
		git.update(NEXT
		Ref next = refdb.exactRef(NEXT);
		assertNotNull(next);
		assertEquals(commit0

		git.delete(NEXT);
		refdb.clearCache();
		assertNull(refdb.exactRef(NEXT));

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		gc.setIncludeDeletes(false);
		gc.setConvertToReftable(false);
		run(gc);
		assertEquals(1
		try (DfsReader ctx = odb.newReader();
			 ReftableReader rr = odb.getReftables()[0].open(ctx)) {
			rr.setIncludeDeletes(true);
			assertEquals(1
			assertEquals(2
			assertNull(rr.exactRef(NEXT));
		}

		RevCommit commit1 = commit().message("1").create();
		DfsPackDescription t1 = odb.newPack(INSERT);
		Ref newNext = new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE
				commit1);
		try (DfsOutputStream out = odb.writeFile(t1
			ReftableWriter w = new ReftableWriter();
			w.setMinUpdateIndex(1);
			w.setMaxUpdateIndex(1);
			w.begin(out);
			w.writeRef(newNext
			w.finish();
			t1.addFileExt(REFTABLE);
			t1.setReftableStats(w.getStats());
		}
		odb.commitPack(Collections.singleton(t1)
		assertEquals(2
		refdb.clearCache();
		newNext = refdb.exactRef(NEXT);
		assertNotNull(newNext);
		assertEquals(commit1
	}

	@Test
	public void reftableWithTombstoneNotResurrected() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		String NEXT = "refs/heads/next";
		DfsRefDatabase refdb = (DfsRefDatabase)repo.getRefDatabase();
		git.update(NEXT
		Ref next = refdb.exactRef(NEXT);
		assertNotNull(next);
		assertEquals(commit0

		git.delete(NEXT);
		refdb.clearCache();
		assertNull(refdb.exactRef(NEXT));

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		gc.setIncludeDeletes(true);
		gc.setConvertToReftable(false);
		run(gc);
		assertEquals(1
		try (DfsReader ctx = odb.newReader();
			 ReftableReader rr = odb.getReftables()[0].open(ctx)) {
			rr.setIncludeDeletes(true);
			assertEquals(1
			assertEquals(2
			next = rr.exactRef(NEXT);
			assertNotNull(next);
			assertNull(next.getObjectId());
		}

		RevCommit commit1 = commit().message("1").create();
		DfsPackDescription t1 = odb.newPack(INSERT);
		Ref newNext = new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE
				commit1);
		try (DfsOutputStream out = odb.writeFile(t1
			ReftableWriter w = new ReftableWriter();
			w.setMinUpdateIndex(1);
			w.setMaxUpdateIndex(1);
			w.begin(out);
			w.writeRef(newNext
			w.finish();
			t1.addFileExt(REFTABLE);
			t1.setReftableStats(w.getStats());
		}
		odb.commitPack(Collections.singleton(t1)
		assertEquals(2
		refdb.clearCache();
		assertNull(refdb.exactRef(NEXT));
	}

	private TestRepository<InMemoryRepository>.CommitBuilder commit() {
		return git.commit();
	}

	private void gcNoTtl() throws IOException {
		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		run(gc);
	}

	private void gcWithTtl() throws IOException {
		mockSystemReader.tick(60);
		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(1
		run(gc);
	}

	private void run(DfsGarbageCollector gc) throws IOException {
		mockSystemReader.tick(1);
		assertTrue("gc repacked"
		odb.clearCache();
	}

	private static boolean isReachable(Repository repo
			throws IOException {
		try (RevWalk rw = new RevWalk(repo)) {
			for (Ref ref : repo.getRefDatabase().getRefs()) {
				rw.markStart(rw.parseCommit(ref.getObjectId()));
			}
			for (RevCommit next; (next = rw.next()) != null;) {
				if (AnyObjectId.equals(next
					return true;
				}
			}
		}
		return false;
	}

	private boolean isObjectInPack(AnyObjectId id
			throws IOException {
		try (DfsReader reader = odb.newReader()) {
			return pack.hasObject(reader
		}
	}

	private int countPacks(PackSource source) throws IOException {
		int cnt = 0;
		for (DfsPackFile pack : odb.getPacks()) {
			if (pack.getPackDescription().getPackSource() == source) {
				cnt++;
			}
		}
		return cnt;
	}
}
