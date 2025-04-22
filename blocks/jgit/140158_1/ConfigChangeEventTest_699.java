
package org.eclipse.jgit.dircache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.io.IOException;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.junit.Test;

public class DirCacheTreeTest extends RepositoryTestCase {
	@Test
	public void testEmptyCache_NoCacheTree() throws Exception {
		final DirCache dc = db.readDirCache();
		assertNull(dc.getCacheTree(false));
	}

	@Test
	public void testEmptyCache_CreateEmptyCacheTree() throws Exception {
		final DirCache dc = db.readDirCache();
		final DirCacheTree tree = dc.getCacheTree(true);
		assertNotNull(tree);
		assertSame(tree
		assertSame(tree
		assertEquals(""
		assertEquals(""
		assertEquals(0
		assertEquals(0
		assertFalse(tree.isValid());
	}

	@Test
	public void testEmptyCache_Clear_NoCacheTree() throws Exception {
		final DirCache dc = db.readDirCache();
		final DirCacheTree tree = dc.getCacheTree(true);
		assertNotNull(tree);
		dc.clear();
		assertNull(dc.getCacheTree(false));
		assertNotSame(tree
	}

	@Test
	public void testSingleSubtree() throws Exception {
		final DirCache dc = db.readDirCache();

		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(FileMode.REGULAR_FILE);
		}
		final int aFirst = 1;
		final int aLast = 3;

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		assertNull(dc.getCacheTree(false));
		final DirCacheTree root = dc.getCacheTree(true);
		assertNotNull(root);
		assertSame(root
		assertEquals(""
		assertEquals(""
		assertEquals(1
		assertEquals(dc.getEntryCount()
		assertFalse(root.isValid());

		final DirCacheTree aTree = root.getChild(0);
		assertNotNull(aTree);
		assertSame(aTree
		assertEquals("a"
		assertEquals("a/"
		assertEquals(0
		assertEquals(aLast - aFirst + 1
		assertFalse(aTree.isValid());
	}

	@Test
	public void testTwoLevelSubtree() throws Exception {
		final DirCache dc = db.readDirCache();

		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(FileMode.REGULAR_FILE);
		}
		final int aFirst = 1;
		final int aLast = 4;
		final int acFirst = 2;
		final int acLast = 3;

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		assertNull(dc.getCacheTree(false));
		final DirCacheTree root = dc.getCacheTree(true);
		assertNotNull(root);
		assertSame(root
		assertEquals(""
		assertEquals(""
		assertEquals(1
		assertEquals(dc.getEntryCount()
		assertFalse(root.isValid());

		final DirCacheTree aTree = root.getChild(0);
		assertNotNull(aTree);
		assertSame(aTree
		assertEquals("a"
		assertEquals("a/"
		assertEquals(1
		assertEquals(aLast - aFirst + 1
		assertFalse(aTree.isValid());

		final DirCacheTree acTree = aTree.getChild(0);
		assertNotNull(acTree);
		assertSame(acTree
		assertEquals("c"
		assertEquals("a/c/"
		assertEquals(0
		assertEquals(acLast - acFirst + 1
		assertFalse(acTree.isValid());
	}

	@Test
	public void testWriteReadTree() throws CorruptObjectException
		final DirCache dc = db.lockDirCache();

		final String A = String.format("a%2000s"
		final String B = String.format("b%2000s"
		final String[] paths = { A + "-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(FileMode.REGULAR_FILE);
		}

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }

		b.commit();
		DirCache read = db.readDirCache();

		assertEquals(paths.length
		assertEquals(1
	}
}
