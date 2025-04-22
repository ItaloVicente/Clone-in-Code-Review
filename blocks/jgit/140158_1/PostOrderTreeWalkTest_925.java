
package org.eclipse.jgit.treewalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.junit.Test;

public class NameConflictTreeWalkTest extends RepositoryTestCase {
	private static final FileMode TREE = FileMode.TREE;

	private static final FileMode SYMLINK = FileMode.SYMLINK;

	private static final FileMode MISSING = FileMode.MISSING;

	private static final FileMode REGULAR_FILE = FileMode.REGULAR_FILE;

	private static final FileMode EXECUTABLE_FILE = FileMode.EXECUTABLE_FILE;

	@Test
	public void testNoDF_NoGap() throws Exception {
		final DirCache tree0 = db.readDirCache();
		final DirCache tree1 = db.readDirCache();
		{
			final DirCacheBuilder b0 = tree0.builder();
			final DirCacheBuilder b1 = tree1.builder();

			b0.add(createEntry("a"
			b0.add(createEntry("a.b"
			b1.add(createEntry("a/b"
			b0.add(createEntry("a0b"

			b0.finish();
			b1.finish();
			assertEquals(3
			assertEquals(1
		}

		final TreeWalk tw = new TreeWalk(db);
		tw.addTree(new DirCacheIterator(tree0));
		tw.addTree(new DirCacheIterator(tree1));

		assertModes("a"
		assertModes("a.b"
		assertModes("a"
		tw.enterSubtree();
		assertModes("a/b"
		assertModes("a0b"
	}

	@Test
	public void testDF_NoGap() throws Exception {
		final DirCache tree0 = db.readDirCache();
		final DirCache tree1 = db.readDirCache();
		{
			final DirCacheBuilder b0 = tree0.builder();
			final DirCacheBuilder b1 = tree1.builder();

			b0.add(createEntry("a"
			b0.add(createEntry("a.b"
			b1.add(createEntry("a/b"
			b0.add(createEntry("a0b"

			b0.finish();
			b1.finish();
			assertEquals(3
			assertEquals(1
		}

		final NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.addTree(new DirCacheIterator(tree0));
		tw.addTree(new DirCacheIterator(tree1));

		assertModes("a"
		assertTrue(tw.isDirectoryFileConflict());
		assertTrue(tw.isSubtree());
		tw.enterSubtree();
		assertModes("a/b"
		assertTrue(tw.isDirectoryFileConflict());
		assertModes("a.b"
		assertFalse(tw.isDirectoryFileConflict());
		assertModes("a0b"
		assertFalse(tw.isDirectoryFileConflict());
	}

	@Test
	public void testDF_GapByOne() throws Exception {
		final DirCache tree0 = db.readDirCache();
		final DirCache tree1 = db.readDirCache();
		{
			final DirCacheBuilder b0 = tree0.builder();
			final DirCacheBuilder b1 = tree1.builder();

			b0.add(createEntry("a"
			b0.add(createEntry("a.b"
			b1.add(createEntry("a.b"
			b1.add(createEntry("a/b"
			b0.add(createEntry("a0b"

			b0.finish();
			b1.finish();
			assertEquals(3
			assertEquals(2
		}

		final NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.addTree(new DirCacheIterator(tree0));
		tw.addTree(new DirCacheIterator(tree1));

		assertModes("a"
		assertTrue(tw.isSubtree());
		assertTrue(tw.isDirectoryFileConflict());
		tw.enterSubtree();
		assertModes("a/b"
		assertTrue(tw.isDirectoryFileConflict());
		assertModes("a.b"
		assertFalse(tw.isDirectoryFileConflict());
		assertModes("a0b"
		assertFalse(tw.isDirectoryFileConflict());
	}

	@Test
	public void testDF_SkipsSeenSubtree() throws Exception {
		final DirCache tree0 = db.readDirCache();
		final DirCache tree1 = db.readDirCache();
		{
			final DirCacheBuilder b0 = tree0.builder();
			final DirCacheBuilder b1 = tree1.builder();

			b0.add(createEntry("a"
			b1.add(createEntry("a.b"
			b1.add(createEntry("a/b"
			b0.add(createEntry("a0b"
			b1.add(createEntry("a0b"

			b0.finish();
			b1.finish();
			assertEquals(2
			assertEquals(3
		}

		final NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.addTree(new DirCacheIterator(tree0));
		tw.addTree(new DirCacheIterator(tree1));

		assertModes("a"
		assertTrue(tw.isSubtree());
		assertTrue(tw.isDirectoryFileConflict());
		tw.enterSubtree();
		assertModes("a/b"
		assertTrue(tw.isDirectoryFileConflict());
		assertModes("a.b"
		assertFalse(tw.isDirectoryFileConflict());
		assertModes("a0b"
		assertFalse(tw.isDirectoryFileConflict());
	}

	@Test
	public void testDF_DetectConflict() throws Exception {
		final DirCache tree0 = db.readDirCache();
		final DirCache tree1 = db.readDirCache();
		{
			final DirCacheBuilder b0 = tree0.builder();
			final DirCacheBuilder b1 = tree1.builder();

			b0.add(createEntry("0"
			b0.add(createEntry("a"
			b1.add(createEntry("0"
			b1.add(createEntry("a.b"
			b1.add(createEntry("a/b"
			b1.add(createEntry("a/c/e"

			b0.finish();
			b1.finish();
			assertEquals(2
			assertEquals(4
		}

		final NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.addTree(new DirCacheIterator(tree0));
		tw.addTree(new DirCacheIterator(tree1));

		assertModes("0"
		assertFalse(tw.isDirectoryFileConflict());
		assertModes("a"
		assertTrue(tw.isSubtree());
		assertTrue(tw.isDirectoryFileConflict());
		tw.enterSubtree();
		assertModes("a/b"
		assertTrue(tw.isDirectoryFileConflict());
		assertModes("a/c"
		assertTrue(tw.isDirectoryFileConflict());
		tw.enterSubtree();
		assertModes("a/c/e"
		assertTrue(tw.isDirectoryFileConflict());

		assertModes("a.b"
		assertFalse(tw.isDirectoryFileConflict());
	}

	private static void assertModes(final String path
			final FileMode mode1
		assertTrue("has " + path
		assertEquals(path
		assertEquals(mode0
		assertEquals(mode1
	}
}
