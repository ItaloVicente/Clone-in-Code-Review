
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.TreeFormatter;
import org.junit.Test;

public class ObjectWalkTest extends RevWalkTestCase {
	protected ObjectWalk objw;

	@Override
	protected RevWalk createRevWalk() {
		return objw = new ObjectWalk(db);
	}

	@Test
	public void testNoCommits() throws Exception {
		assertNull(objw.next());
		assertNull(objw.nextObject());
	}

	@Test
	public void testTwoCommitsEmptyTree() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		markStart(b);

		assertCommit(b
		assertCommit(a
		assertNull(objw.next());

		assertSame(tree()
		assertNull(objw.nextObject());
	}

	@Test
	public void testOneCommitOneTreeTwoBlob() throws Exception {
		final RevBlob f0 = blob("0");
		final RevBlob f1 = blob("1");
		final RevTree t = tree(file("0"
		final RevCommit a = commit(t);
		markStart(a);

		assertCommit(a
		assertNull(objw.next());

		assertSame(t
		assertSame(f0
		assertSame(f1
		assertNull(objw.nextObject());
	}

	@Test
	public void testTwoCommitTwoTreeTwoBlob() throws Exception {
		final RevBlob f0 = blob("0");
		final RevBlob f1 = blob("1");
		final RevBlob f2 = blob("0v2");
		final RevTree ta = tree(file("0"
		final RevTree tb = tree(file("0"
		final RevCommit a = commit(ta);
		final RevCommit b = commit(tb
		markStart(b);

		assertCommit(b
		assertCommit(a
		assertNull(objw.next());

		assertSame(tb
		assertSame(f2
		assertSame(f1

		assertSame(ta
		assertSame(f0

		assertNull(objw.nextObject());
	}

	@Test
	public void testTwoCommitDeepTree1() throws Exception {
		final RevBlob f0 = blob("0");
		final RevBlob f1 = blob("0v2");
		final RevTree ta = tree(file("a/b/0"
		final RevTree tb = tree(file("a/b/1"
		final RevCommit a = commit(ta);
		final RevCommit b = commit(tb
		markStart(b);

		assertCommit(b
		assertCommit(a
		assertNull(objw.next());

		assertSame(tb
		assertSame(get(tb
		assertSame(get(tb
		assertSame(f1

		assertSame(ta
		assertSame(get(ta
		assertSame(get(ta
		assertSame(f0

		assertNull(objw.nextObject());
	}

	@Test
	public void testTwoCommitDeepTree2() throws Exception {
		final RevBlob f1 = blob("1");
		final RevTree ta = tree(file("a/b/0"
		final RevTree tb = tree(file("a/b/1"
		final RevCommit a = commit(ta);
		final RevCommit b = commit(tb
		markStart(b);

		assertCommit(b
		assertCommit(a
		assertNull(objw.next());

		assertSame(tb
		assertSame(get(tb
		assertSame(get(tb
		assertSame(f1
		assertSame(get(tb

		assertSame(ta
		assertSame(get(ta
		assertSame(get(ta

		assertNull(objw.nextObject());
	}

	@Test
	public void testCull() throws Exception {
		final RevBlob f1 = blob("1");
		final RevBlob f2 = blob("2");
		final RevBlob f3 = blob("3");
		final RevBlob f4 = blob("4");

		final RevTree ta = tree(file("a/1"
		final RevCommit a = commit(ta);

		final RevTree tb = tree(file("a/1"
		final RevCommit b1 = commit(tb
		final RevCommit b2 = commit(tb

		final RevTree tc = tree(file("a/1"
		final RevCommit c1 = commit(tc
		final RevCommit c2 = commit(tc

		markStart(b2);
		markUninteresting(c2);

		assertCommit(b2
		assertCommit(b1
		assertNull(objw.next());

		assertTrue(a.has(RevFlag.UNINTERESTING));
		assertTrue(ta.has(RevFlag.UNINTERESTING));
		assertTrue(f1.has(RevFlag.UNINTERESTING));
		assertTrue(f3.has(RevFlag.UNINTERESTING));

		assertSame(tb
		assertSame(get(tb
		assertSame(f2
		assertNull(objw.nextObject());
	}

	@Test
	public void testEmptyTreeCorruption() throws Exception {
		ObjectId bId = ObjectId
				.fromString("abbbfafe3129f85747aba7bfac992af77134c607");
		final RevTree tree_root
		final RevCommit b;
		try (ObjectInserter inserter = db.newObjectInserter()) {
			ObjectId empty = inserter.insert(new TreeFormatter());

			TreeFormatter A = new TreeFormatter();
			A.append("A"
			A.append("B"
			ObjectId idA = inserter.insert(A);

			TreeFormatter root = new TreeFormatter();
			root.append("A"
			root.append("B"
			ObjectId idRoot = inserter.insert(root);
			inserter.flush();

			tree_root = objw.parseTree(idRoot);
			tree_A = objw.parseTree(idA);
			tree_AB = objw.parseTree(empty);
			b = commit(tree_root);
		}

		markStart(b);

		assertCommit(b
		assertNull(objw.next());

		assertSame(tree_root
		assertSame(tree_A
		assertSame(tree_AB
		assertSame(rw.lookupBlob(bId)
		assertNull(objw.nextObject());
	}
}
