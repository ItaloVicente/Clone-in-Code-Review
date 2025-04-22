
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.LongStream;

import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class DfsBlockCacheTest {
	@Rule
	public TestName testName = new TestName();
	private TestRng rng;
	private DfsBlockCache cache;

	@Before
	public void setUp() {
		rng = new TestRng(testName.getMethodName());
		resetCache();
	}

	@SuppressWarnings("resource")
	@Test
	public void streamKeyReusesBlocks() throws Exception {
		DfsRepositoryDescription repo = new DfsRepositoryDescription("test");
		InMemoryRepository r1 = new InMemoryRepository(repo);
		byte[] content = rng.nextBytes(424242);
		ObjectId id;
		try (ObjectInserter ins = r1.newObjectInserter()) {
			id = ins.insert(OBJ_BLOB
			ins.flush();
		}

		long oldSize = LongStream.of(cache.getCurrentSize()).sum();
		assertTrue(oldSize > 2000);
		assertEquals(0

		List<DfsPackDescription> packs = r1.getObjectDatabase().listPacks();
		InMemoryRepository r2 = new InMemoryRepository(repo);
		r2.getObjectDatabase().commitPack(packs
		try (ObjectReader rdr = r2.newObjectReader()) {
			byte[] actual = rdr.open(id
			assertTrue(Arrays.equals(content
		}
		assertEquals(0
		assertEquals(oldSize
	}

	@SuppressWarnings("resource")
	@Test
	public void weirdBlockSize() throws Exception {
		DfsRepositoryDescription repo = new DfsRepositoryDescription("test");
		InMemoryRepository r1 = new InMemoryRepository(repo);

		byte[] content1 = rng.nextBytes(4);
		byte[] content2 = rng.nextBytes(424242);
		ObjectId id1;
		ObjectId id2;
		try (ObjectInserter ins = r1.newObjectInserter()) {
			id1 = ins.insert(OBJ_BLOB
			id2 = ins.insert(OBJ_BLOB
			ins.flush();
		}

		resetCache();
		List<DfsPackDescription> packs = r1.getObjectDatabase().listPacks();

		InMemoryRepository r2 = new InMemoryRepository(repo);
		r2.getObjectDatabase().setReadableChannelBlockSizeForTest(500);
		r2.getObjectDatabase().commitPack(packs
		try (ObjectReader rdr = r2.newObjectReader()) {
			byte[] actual = rdr.open(id1
			assertTrue(Arrays.equals(content1
		}

		InMemoryRepository r3 = new InMemoryRepository(repo);
		r3.getObjectDatabase().setReadableChannelBlockSizeForTest(500);
		r3.getObjectDatabase().commitPack(packs
		try (ObjectReader rdr = r3.newObjectReader()) {
			byte[] actual = rdr.open(id2
			assertTrue(Arrays.equals(content2
		}
	}

	private void resetCache() {
		DfsBlockCache.reconfigure(new DfsBlockCacheConfig()
				.setBlockSize(512)
				.setBlockLimit(1 << 20));
		cache = DfsBlockCache.getInstance();
	}
}
