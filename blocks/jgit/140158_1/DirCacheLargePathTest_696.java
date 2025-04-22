
package org.eclipse.jgit.dircache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Collections;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.util.FS;
import org.junit.Test;

public class DirCacheIteratorTest extends RepositoryTestCase {
	@Test
	public void testEmptyTree_NoTreeWalk() throws Exception {
		final DirCache dc = DirCache.newInCore();
		assertEquals(0

		final DirCacheIterator i = new DirCacheIterator(dc);
		assertTrue(i.eof());
	}

	@Test
	public void testEmptyTree_WithTreeWalk() throws Exception {
		final DirCache dc = DirCache.newInCore();
		assertEquals(0

		try (TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(new DirCacheIterator(dc));
			assertFalse(tw.next());
		}
	}

	@Test
	public void testNoSubtree_NoTreeWalk() throws Exception {
		final DirCache dc = DirCache.newInCore();

		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(FileMode.REGULAR_FILE);
		}

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		final DirCacheIterator i = new DirCacheIterator(dc);
		int pathIdx = 0;
		for (; !i.eof(); i.next(1)) {
			assertEquals(pathIdx
			assertSame(ents[pathIdx]
			pathIdx++;
		}
		assertEquals(paths.length
	}

	@Test
	public void testNoSubtree_WithTreeWalk() throws Exception {
		final DirCache dc = DirCache.newInCore();

		final String[] paths = { "a-"
		final FileMode[] modes = { FileMode.EXECUTABLE_FILE
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(modes[i]);
		}

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		final DirCacheIterator i = new DirCacheIterator(dc);
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(i);
			int pathIdx = 0;
			while (tw.next()) {
				assertSame(i
				assertEquals(pathIdx
				assertSame(ents[pathIdx]
				assertEquals(paths[pathIdx]
				assertEquals(modes[pathIdx].getBits()
				assertSame(modes[pathIdx]
				pathIdx++;
			}
			assertEquals(paths.length
		}
	}

	@Test
	public void testSingleSubtree_NoRecursion() throws Exception {
		final DirCache dc = DirCache.newInCore();

		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(FileMode.REGULAR_FILE);
		}

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		final String[] expPaths = { "a-"
		final FileMode[] expModes = { FileMode.REGULAR_FILE
				FileMode.REGULAR_FILE };
		final int expPos[] = { 0

		final DirCacheIterator i = new DirCacheIterator(dc);
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(i);
			tw.setRecursive(false);
			int pathIdx = 0;
			while (tw.next()) {
				assertSame(i
				assertEquals(expModes[pathIdx].getBits()
				assertSame(expModes[pathIdx]
				assertEquals(expPaths[pathIdx]

				if (expPos[pathIdx] >= 0) {
					assertEquals(expPos[pathIdx]
					assertSame(ents[expPos[pathIdx]]
				} else {
					assertSame(FileMode.TREE
				}

				pathIdx++;
			}
			assertEquals(expPaths.length
		}
	}

	@Test
	public void testSingleSubtree_Recursive() throws Exception {
		final DirCache dc = DirCache.newInCore();

		final FileMode mode = FileMode.REGULAR_FILE;
		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(mode);
		}

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		final DirCacheIterator i = new DirCacheIterator(dc);
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(i);
			tw.setRecursive(true);
			int pathIdx = 0;
			while (tw.next()) {
				final DirCacheIterator c = tw.getTree(0
				assertNotNull(c);
				assertEquals(pathIdx
				assertSame(ents[pathIdx]
				assertEquals(paths[pathIdx]
				assertEquals(mode.getBits()
				assertSame(mode
				pathIdx++;
			}
			assertEquals(paths.length
		}
	}

	@Test
	public void testTwoLevelSubtree_Recursive() throws Exception {
		final DirCache dc = DirCache.newInCore();

		final FileMode mode = FileMode.REGULAR_FILE;
		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(mode);
		}

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		try (TreeWalk tw = new TreeWalk(db)) {
			tw.addTree(new DirCacheIterator(dc));
			tw.setRecursive(true);
			int pathIdx = 0;
			while (tw.next()) {
				final DirCacheIterator c = tw.getTree(0
				assertNotNull(c);
				assertEquals(pathIdx
				assertSame(ents[pathIdx]
				assertEquals(paths[pathIdx]
				assertEquals(mode.getBits()
				assertSame(mode
				pathIdx++;
			}
			assertEquals(paths.length
		}
	}

	@Test
	public void testReset() throws Exception {
		final DirCache dc = DirCache.newInCore();

		final FileMode mode = FileMode.REGULAR_FILE;
		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(mode);
		}

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		DirCacheIterator dci = new DirCacheIterator(dc);
		assertFalse(dci.eof());
		assertEquals("a-"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.next(1);
		assertTrue(dci.eof());

		dci.reset();
		assertFalse(dci.eof());
		assertEquals("a-"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.next(1);
		assertTrue(dci.eof());

		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("a"
		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("a-"
		assertTrue(dci.first());

		assertFalse(dci.eof());
		assertEquals("a-"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.next(1);
		assertTrue(dci.eof());

		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("a"

		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("a0b"
		dci.next(1);
		assertTrue(dci.eof());

		AbstractTreeIterator sti = dci.createSubtreeIterator(null);
		assertEquals("a/b"
		sti.next(1);
		assertEquals("a/c"
		sti.next(1);
		assertEquals("a/d"
		sti.back(2);
		assertEquals("a/b"

	}

	@Test
	public void testBackBug396127() throws Exception {
		final DirCache dc = DirCache.newInCore();

		final FileMode mode = FileMode.REGULAR_FILE;
		final String[] paths = { "git-gui/po/fr.po"
				"git_remote_helpers/git/repo.py" };
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(mode);
		}

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		DirCacheIterator dci = new DirCacheIterator(dc);
		assertFalse(dci.eof());
		assertEquals("git-gui"
		dci.next(1);
		assertFalse(dci.eof());
		assertEquals("git_remote_helpers"
		dci.back(1);
		assertFalse(dci.eof());
		assertEquals("git-gui"
		dci.next(1);
		assertEquals("git_remote_helpers"
		dci.next(1);
		assertTrue(dci.eof());

	}

	@Test
	public void testTwoLevelSubtree_FilterPath() throws Exception {
		final DirCache dc = DirCache.newInCore();

		final FileMode mode = FileMode.REGULAR_FILE;
		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(mode);
		}

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		try (TreeWalk tw = new TreeWalk(db)) {
			for (int victimIdx = 0; victimIdx < paths.length; victimIdx++) {
				tw.reset();
				tw.addTree(new DirCacheIterator(dc));
				tw.setFilter(PathFilterGroup.createFromStrings(Collections
						.singleton(paths[victimIdx])));
				tw.setRecursive(tw.getFilter().shouldBeRecursive());
				assertTrue(tw.next());
				final DirCacheIterator c = tw.getTree(0
				assertNotNull(c);
				assertEquals(victimIdx
				assertSame(ents[victimIdx]
				assertEquals(paths[victimIdx]
				assertEquals(mode.getBits()
				assertSame(mode
				assertFalse(tw.next());
			}
		}
	}

	@Test
	public void testRemovedSubtree() throws Exception {
		final File path = JGitTestUtil
				.getTestResourceFile("dircache.testRemovedSubtree");

		final DirCache dc = DirCache.read(path
		assertEquals(2

		try (TreeWalk tw = new TreeWalk(db)) {
			tw.setRecursive(true);
			tw.addTree(new DirCacheIterator(dc));

			assertTrue(tw.next());
			assertEquals("a/a"
			assertSame(FileMode.REGULAR_FILE

			assertTrue(tw.next());
			assertEquals("q"
			assertSame(FileMode.REGULAR_FILE

			assertFalse(tw.next());
		}
	}
}
