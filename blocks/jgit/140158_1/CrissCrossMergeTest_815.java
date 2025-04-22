
package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

public class CherryPickTest extends RepositoryTestCase {
	@Test
	public void testPick() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeP = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder p = treeP.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("a"

			o.add(createEntry("a"
			o.add(createEntry("o"

			p.add(createEntry("a"
			p.add(createEntry("p-fail"

			t.add(createEntry("a"
			t.add(createEntry("t"

			b.finish();
			o.finish();
			p.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId B = commit(ow
		final ObjectId O = commit(ow
		final ObjectId P = commit(ow
		final ObjectId T = commit(ow

		ThreeWayMerger twm = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		twm.setBase(P);
		boolean merge = twm.merge(new ObjectId[] { O
		assertTrue(merge);

		final TreeWalk tw = new TreeWalk(db);
		tw.setRecursive(true);
		tw.reset(twm.getResultTreeId());

		assertTrue(tw.next());
		assertEquals("a"
		assertCorrectId(treeO

		assertTrue(tw.next());
		assertEquals("o"
		assertCorrectId(treeO

		assertTrue(tw.next());
		assertEquals("t"
		assertCorrectId(treeT

		assertFalse(tw.next());
	}

	@Test
	public void testRevert() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeP = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder p = treeP.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry("a"

			p.add(createEntry("a"
			p.add(createEntry("p-fail"

			t.add(createEntry("a"
			t.add(createEntry("p-fail"
			t.add(createEntry("t"

			b.finish();
			p.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId B = commit(ow
		final ObjectId P = commit(ow
		final ObjectId T = commit(ow

		ThreeWayMerger twm = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		twm.setBase(P);
		boolean merge = twm.merge(new ObjectId[] { B
		assertTrue(merge);

		final TreeWalk tw = new TreeWalk(db);
		tw.setRecursive(true);
		tw.reset(twm.getResultTreeId());

		assertTrue(tw.next());
		assertEquals("a"
		assertCorrectId(treeB

		assertTrue(tw.next());
		assertEquals("t"
		assertCorrectId(treeT

		assertFalse(tw.next());
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
