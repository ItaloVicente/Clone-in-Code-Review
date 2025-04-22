
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.eclipse.jgit.internal.storage.dfs.DeltaBaseCache.Entry;
import org.eclipse.jgit.junit.TestRng;
import org.junit.Before;
import org.junit.Test;

public class DeltaBaseCacheTest {
	private static final int SZ = 512;

	private DfsStreamKey key;
	private DeltaBaseCache cache;
	private TestRng rng;

	@Before
	public void setUp() {
		DfsRepositoryDescription repo = new DfsRepositoryDescription("test");
		key = DfsStreamKey.of(repo
		cache = new DeltaBaseCache(SZ);
		rng = new TestRng(getClass().getSimpleName());
	}

	@Test
	public void testObjectLargerThanCacheDoesNotEvict() {
		byte[] obj12 = put(12
		put(24
		assertNull("does not store large object"
		get(obj12
	}

	@Test
	public void testCacheLruExpires1() {
		byte[] obj1 = put(1
		put(2
		byte[] obj3 = put(3
		put(4
		assertEquals(SZ

		get(obj3
		get(obj1
		put(5
		assertEquals(SZ
		assertEquals(SZ
		assertEquals(SZ
		assertNull(cache.get(key
		assertNull(cache.get(key

		get(obj1
		get(obj3
	}

	@Test
	public void testCacheLruExpires2() {
		int pos0 = (0 << 10) | 2;
		int pos1 = (1 << 10) | 2;
		int pos2 = (2 << 10) | 2;
		int pos5 = (5 << 10) | 2;
		int pos6 = (6 << 10) | 2;

		put(pos0
		put(pos5
		byte[] obj1 = put(pos1
		byte[] obj2 = put(pos2
		assertEquals(SZ

		byte[] obj6 = put(pos6
		assertEquals(SZ
		assertEquals(SZ
		assertEquals(SZ
		assertNull(cache.get(key
		assertNull(cache.get(key

		get(obj1
		get(obj2
		get(obj6
	}

	@Test
	public void testCacheMemoryUsedConsistentWithExpectations() {
		put(1
		put(2
		put(3

		assertNotNull(cache.get(key
		assertNotNull(cache.get(key

		assertEquals(32 * 3
		assertEquals(32 * 3
		assertEquals(32 * 3
	}

	private void get(byte[] data
		Entry e = cache.get(key
		assertNotNull("expected entry at " + position
		assertEquals("expected blob for " + position
		assertSame("expected data for " + position
	}

	private byte[] put(int position
		byte[] data = rng.nextBytes(sz);
		cache.put(key
		return data;
	}
}
