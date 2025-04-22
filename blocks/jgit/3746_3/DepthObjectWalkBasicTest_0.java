
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileTreeEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Tree;
import org.junit.Test;

public class DepthObjectWalkBasicDepthTest extends RevWalkTestCase {
	protected DepthObjectWalk dow;

	@Override
	protected RevWalk createRevWalk() {
		return dow = new DepthObjectWalk(db
	}

	@Test
	public void testNoCommits() throws Exception {
		assertNull(dow.next());
	}

	@Test
	public void testTwoCommits() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		markStart(b);

		assertCommit(b
		assertCommit(a
		assertNull(dow.next());
	}

	@Test
	public void testOverdeepCommits() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		markStart(c);

		assertCommit(c
		assertCommit(b
		assertTrue(b.has(dow.SHALLOW));
		assertNull(dow.next());
		assertTrue(a.has(RevFlag.UNINTERESTING));
	}

	@Test
	public void testOverdeepCommits2() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);
		final RevCommit e = commit(d);
		markStart(e);
		dow.setDepth(3);

		assertCommit(e
		assertCommit(d
		assertCommit(c
		assertCommit(b
		assertNull(dow.next());
		assertTrue(a.has(RevFlag.UNINTERESTING));
		assertTrue(b.has(dow.SHALLOW));
	}

	@Test
	public void testBranchyTree() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit b2 = commit(a);
		final RevCommit c1 = commit(b1);
		final RevCommit c2 = commit(b2);
		final RevCommit d = commit(c1
		final RevCommit e = commit(d);
		markStart(e);
		dow.setDepth(4);

		assertCommit(e
		assertCommit(d
		assertCommit(c1
		assertCommit(c2
		assertCommit(b1
		assertCommit(b2
		assertCommit(a
		assertNull(dow.next());
		assertTrue(a.has(dow.SHALLOW));
	}

	@Test
	public void testOverdeepBranchyTree() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit b2 = commit(a);
		final RevCommit c1 = commit(b1);
		final RevCommit c2 = commit(b2);
		final RevCommit d = commit(c1
		final RevCommit e = commit(d);
		markStart(e);
		dow.setDepth(3);

		assertCommit(e
		assertCommit(d
		assertCommit(c1
		assertCommit(c2
		assertCommit(b1
		assertCommit(b2
		assertNull(dow.next());
		assertTrue(b1.has(dow.SHALLOW));
		assertTrue(b2.has(dow.SHALLOW));
		assertTrue(a.has(RevFlag.UNINTERESTING));
	}

	@Test
	public void testShortestPath() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit b2 = commit(a);
		final RevCommit c1 = commit(b1);
		final RevCommit c2 = commit(b2);
		final RevCommit c3 = commit(b2);
		final RevCommit d = commit(c1
		final RevCommit e = commit(d
		markStart(e);
		dow.setDepth(3);

		assertCommit(e
		assertCommit(d
		assertCommit(c3
		assertCommit(c1
		assertCommit(c2
		assertCommit(b2
		assertCommit(b1
		assertCommit(a
		assertNull(dow.next());
		assertTrue(a.has(dow.SHALLOW));
		assertTrue(b1.has(dow.SHALLOW));
		assertTrue(!a.has(RevFlag.UNINTERESTING));
	}

	@Test
	public void testUninteresting() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit b2 = commit(a);
		final RevCommit c1 = commit(b1);
		final RevCommit c2 = commit(b2);
		final RevCommit c3 = commit(b2);
		final RevCommit d = commit(c1
		final RevCommit e = commit(d
		markStart(e);
		dow.setDepth(2);

		assertCommit(e
		assertCommit(d
		assertCommit(c3
		assertCommit(c1
		assertCommit(c2
		assertCommit(b2
		assertNull(dow.next());
		assertTrue(c1.has(dow.SHALLOW));
		assertTrue(c2.has(dow.SHALLOW));
		assertTrue(b2.has(dow.SHALLOW));
		assertTrue(b1.has(RevFlag.UNINTERESTING));
		assertTrue(a.has(RevFlag.UNINTERESTING));
		assertTrue(!b2.has(RevFlag.UNINTERESTING));
	}

	@Test
	public void testMultipleWants() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit b2 = commit(a);
		final RevCommit c1 = commit(b1);
		final RevCommit c2 = commit(b2);
		final RevCommit c3 = commit(b2);
		final RevCommit d = commit(c1
		final RevCommit e1 = commit(c1);
		final RevCommit e2 = commit(d
		final RevCommit e3 = commit(c3);
		markStart(e1);
		markStart(e3);
		dow.setDepth(2);

		assertCommit(e3
		assertCommit(e1
		assertCommit(c3
		assertCommit(c1
		assertCommit(b2
		assertCommit(b1
		assertNull(dow.next());
		assertTrue(b1.has(dow.SHALLOW));
		assertTrue(b2.has(dow.SHALLOW));
		assertTrue(a.has(RevFlag.UNINTERESTING));
	}
}
