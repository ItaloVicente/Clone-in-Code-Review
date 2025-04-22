
package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

public class SimpleMergeTest extends SampleDataRepositoryTestCase {

	@Test
	public void testOurs() throws IOException {
		Merger ourMerger = MergeStrategy.OURS.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a")
		assertTrue(merge);
		assertEquals(db.resolve("a^{tree}")
	}

	@Test
	public void testOurs_noRepo() throws IOException {
		try (ObjectInserter ins = db.newObjectInserter()) {
			Merger ourMerger = MergeStrategy.OURS.newMerger(ins
			boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a")
			assertTrue(merge);
			assertEquals(db.resolve("a^{tree}")
		}
	}

	@Test
	public void testTheirs() throws IOException {
		Merger ourMerger = MergeStrategy.THEIRS.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a")
		assertTrue(merge);
		assertEquals(db.resolve("c^{tree}")
	}

	@Test
	public void testTheirs_noRepo() throws IOException {
		try (ObjectInserter ins = db.newObjectInserter()) {
			Merger ourMerger = MergeStrategy.THEIRS.newMerger(db);
			boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a")
			assertTrue(merge);
			assertEquals(db.resolve("c^{tree}")
		}
	}

	@Test
	public void testTrivialTwoWay() throws IOException {
		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a")
		assertTrue(merge);
		assertEquals("02ba32d3649e510002c21651936b7077aa75ffa9"
	}

	@Test
	public void testTrivialTwoWay_disjointhistories() throws IOException {
		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a")
		assertTrue(merge);
		assertEquals("86265c33b19b2be71bdd7b8cb95823f2743d03a8"
	}

	@Test
	public void testTrivialTwoWay_ok() throws IOException {
		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a^0^0^0")
		assertTrue(merge);
		assertEquals(db.resolve("a^0^0^{tree}")
	}

	@Test
	public void testTrivialTwoWay_noRepo() throws IOException {
		try (ObjectInserter ins = db.newObjectInserter()) {
			Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(ins
			boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("a^0^0^0")
			assertTrue(merge);
			assertEquals(db.resolve("a^0^0^{tree}")
		}
	}

	@Test
	public void testTrivialTwoWay_conflict() throws IOException {
		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { db.resolve("f")
		assertFalse(merge);
	}

	@Test
	public void testTrivialTwoWay_validSubtreeSort() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("libelf-po/a"
			b.add(createEntry("libelf/c"

			o.add(createEntry("Makefile"
			o.add(createEntry("libelf-po/a"
			o.add(createEntry("libelf/c"

			t.add(createEntry("libelf-po/a"
			t.add(createEntry("libelf/c"

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { o
		assertTrue(merge);

		final TreeWalk tw = new TreeWalk(db);
		tw.setRecursive(true);
		tw.reset(ourMerger.getResultTreeId());

		assertTrue(tw.next());
		assertEquals("Makefile"
		assertCorrectId(treeO

		assertTrue(tw.next());
		assertEquals("libelf-po/a"
		assertCorrectId(treeO

		assertTrue(tw.next());
		assertEquals("libelf/c"
		assertCorrectId(treeT

		assertFalse(tw.next());
	}

	@Test
	public void testTrivialTwoWay_concurrentSubtreeChange() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("d/o"
			b.add(createEntry("d/t"

			o.add(createEntry("d/o"
			o.add(createEntry("d/t"

			t.add(createEntry("d/o"
			t.add(createEntry("d/t"

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { o
		assertTrue(merge);

		final TreeWalk tw = new TreeWalk(db);
		tw.setRecursive(true);
		tw.reset(ourMerger.getResultTreeId());

		assertTrue(tw.next());
		assertEquals("d/o"
		assertCorrectId(treeO

		assertTrue(tw.next());
		assertEquals("d/t"
		assertCorrectId(treeT

		assertFalse(tw.next());
	}

	@Test
	public void testTrivialTwoWay_conflictSubtreeChange() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("d/o"
			b.add(createEntry("d/t"

			o.add(createEntry("d/o"
			o.add(createEntry("d/t"

			t.add(createEntry("d/o"
			t.add(createEntry("d/t"

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testTrivialTwoWay_leftDFconflict1() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("d/o"
			b.add(createEntry("d/t"

			o.add(createEntry("d"

			t.add(createEntry("d/o"
			t.add(createEntry("d/t"

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testTrivialTwoWay_rightDFconflict1() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("d/o"
			b.add(createEntry("d/t"

			o.add(createEntry("d/o"
			o.add(createEntry("d/t"

			t.add(createEntry("d"

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testTrivialTwoWay_leftDFconflict2() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("d"

			o.add(createEntry("d"

			t.add(createEntry("d/o"

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testTrivialTwoWay_rightDFconflict2() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("d"

			o.add(createEntry("d/o"

			t.add(createEntry("d"

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger ourMerger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		boolean merge = ourMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	private static void assertCorrectId(DirCache treeT
		assertEquals(treeT.getEntry(tw.getPathString()).getObjectId()
				.getObjectId(0));
	}

	private static ObjectId commit(final ObjectInserter odi
			final DirCache treeB
			final ObjectId[] parentIds) throws Exception {
		final CommitBuilder c = new CommitBuilder();
		c.setTreeId(treeB.writeTree(odi));
		c.setAuthor(new PersonIdent("A U Thor"
		c.setCommitter(c.getAuthor());
		c.setParentIds(parentIds);
		c.setMessage("Tree " + c.getTreeId().name());
		ObjectId id = odi.insert(c);
		odi.flush();
		return id;
	}
}
