
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.COMPACT;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.GC;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.GC_REST;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.INSERT;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.RECEIVE;
import static org.eclipse.jgit.internal.storage.pack.PackExt.INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;
import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource;
import org.junit.Before;
import org.junit.Test;

public final class DfsPackDescriptionTest {
	private AtomicInteger counter;

	@Before
	public void setUp() {
		counter = new AtomicInteger();
	}

	@Test
	public void objectLookupComparatorEqual() throws Exception {
		DfsPackDescription a = create(RECEIVE);
		a.setFileSize(PACK
		a.setFileSize(INDEX
		a.setLastModified(1);
		a.setObjectCount(1);
		a.setMaxUpdateIndex(1);

		DfsPackDescription b = create(INSERT);
		b.setFileSize(PACK
		b.setFileSize(INDEX
		b.setLastModified(1);
		b.setObjectCount(1);
		b.setMaxUpdateIndex(2);

		assertComparesEqual(DfsPackDescription.objectLookupComparator()
	}

	@Test
	public void objectLookupComparatorPackSource() throws Exception {
		DfsPackDescription a = create(COMPACT);
		a.setFileSize(PACK
		a.setLastModified(1);
		a.setObjectCount(2);

		DfsPackDescription b = create(GC);
		b.setFileSize(PACK
		b.setLastModified(2);
		b.setObjectCount(1);

		assertComparesLessThan(DfsPackDescription.objectLookupComparator()
	}

	@Test
	public void objectLookupComparatorGcFileSize() throws Exception {
		DfsPackDescription a = create(GC_REST);
		a.setFileSize(PACK
		a.setLastModified(1);
		a.setObjectCount(2);

		DfsPackDescription b = create(GC_REST);
		b.setFileSize(PACK
		b.setLastModified(2);
		b.setObjectCount(1);

		assertComparesLessThan(DfsPackDescription.objectLookupComparator()
	}

	@Test
	public void objectLookupComparatorNonGcLastModified()
			throws Exception {
		DfsPackDescription a = create(INSERT);
		a.setFileSize(PACK
		a.setLastModified(1);
		a.setObjectCount(2);

		DfsPackDescription b = create(INSERT);
		b.setFileSize(PACK
		b.setLastModified(2);
		b.setObjectCount(1);

		assertComparesLessThan(DfsPackDescription.objectLookupComparator()
	}

	@Test
	public void objectLookupComparatorObjectCount() throws Exception {
		DfsPackDescription a = create(INSERT);
		a.setObjectCount(1);

		DfsPackDescription b = create(INSERT);
		b.setObjectCount(2);

		assertComparesLessThan(DfsPackDescription.objectLookupComparator()
	}

	@Test
	public void reftableComparatorEqual() throws Exception {
		DfsPackDescription a = create(INSERT);
		a.setFileSize(PACK
		a.setObjectCount(1);

		DfsPackDescription b = create(INSERT);
		b.setFileSize(PACK
		a.setObjectCount(2);

		assertComparesEqual(DfsPackDescription.reftableComparator()
	}

	@Test
	public void reftableComparatorPackSource() throws Exception {
		DfsPackDescription a = create(INSERT);
		a.setMaxUpdateIndex(1);
		a.setLastModified(1);

		DfsPackDescription b = create(GC);
		b.setMaxUpdateIndex(2);
		b.setLastModified(2);

		assertComparesLessThan(DfsPackDescription.reftableComparator()
	}

	@Test
	public void reftableComparatorMaxUpdateIndex() throws Exception {
		DfsPackDescription a = create(INSERT);
		a.setMaxUpdateIndex(1);
		a.setLastModified(2);

		DfsPackDescription b = create(INSERT);
		b.setMaxUpdateIndex(2);
		b.setLastModified(1);

		assertComparesLessThan(DfsPackDescription.reftableComparator()
	}

	@Test
	public void reftableComparatorLastModified() throws Exception {
		DfsPackDescription a = create(INSERT);
		a.setLastModified(1);

		DfsPackDescription b = create(INSERT);
		b.setLastModified(2);

		assertComparesLessThan(DfsPackDescription.reftableComparator()
	}

	@Test
	public void reuseComparatorEqual() throws Exception {
		DfsPackDescription a = create(RECEIVE);
		a.setFileSize(PACK
		a.setFileSize(INDEX
		a.setLastModified(1);
		a.setObjectCount(1);
		a.setMaxUpdateIndex(1);

		DfsPackDescription b = create(INSERT);
		b.setFileSize(PACK
		b.setFileSize(INDEX
		b.setLastModified(2);
		b.setObjectCount(2);
		b.setMaxUpdateIndex(2);

		assertComparesEqual(DfsPackDescription.reuseComparator()
	}

	@Test
	public void reuseComparatorGcPackSize() throws Exception {
		DfsPackDescription a = create(GC_REST);
		a.setFileSize(PACK
		a.setFileSize(INDEX
		a.setLastModified(2);
		a.setObjectCount(1);
		a.setMaxUpdateIndex(1);

		DfsPackDescription b = create(GC_REST);
		b.setFileSize(PACK
		b.setFileSize(INDEX
		b.setLastModified(1);
		b.setObjectCount(2);
		b.setMaxUpdateIndex(2);

		assertComparesLessThan(DfsPackDescription.reuseComparator()
	}

	private DfsPackDescription create(PackSource source) {
		return new DfsPackDescription(
				new DfsRepositoryDescription("repo")
				"pack_" + counter.incrementAndGet()
				source);
	}

	private static <T> void assertComparesEqual(
			Comparator<T> comparator
		assertEquals(
				"first object must compare equal to itself"
				0
		assertEquals(
				"second object must compare equal to itself"
				0
		assertEquals(
				"first object must compare equal to second object"
				0
	}

	private static <T> void assertComparesLessThan(
			Comparator<T> comparator
		assertEquals(
				"first object must compare equal to itself"
				0
		assertEquals(
				"second object must compare equal to itself"
				0
		assertEquals(
				"first object must compare less than second object"
				-1
	}
}
