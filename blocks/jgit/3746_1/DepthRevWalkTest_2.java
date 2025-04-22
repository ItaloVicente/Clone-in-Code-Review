
package org.eclipse.jgit.revwalk;

import java.util.HashSet;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileTreeEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Tree;
import org.junit.Test;

public class DepthObjectWalkTest extends RevWalkTestCase {
	protected DepthObjectWalk dow;
	protected HashSet<RevCommit> shallows;

	@Override
	protected RevWalk createRevWalk() {
		shallows = new HashSet<RevCommit>();
		dow = new DepthObjectWalk(db
		dow.sort(RevSort.BOUNDARY
		return dow;
	}

	@Test
	public void testThreeCommits() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		shallows.add(b);
		markStart(c);
		markUninteresting(b);

		assertCommit(c
		assertCommit(b
		assertTrue(b.has(RevFlag.UNINTERESTING));
		assertNull(dow.next());
		assertTrue(b.has(dow.BOUNDARY));
	}

	@Test
	public void testNoShallows() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);
		final RevCommit e = commit(d);
		markStart(e);
		markUninteresting(c);
		dow.setDepth(3);

		assertCommit(e
		assertCommit(d
		assertCommit(c
		assertNull(dow.next());
		assertTrue(a.has(RevFlag.UNINTERESTING));
		assertTrue(b.has(RevFlag.UNINTERESTING));
		assertTrue(c.has(RevFlag.UNINTERESTING));
		assertTrue(c.has(dow.BOUNDARY));
	}

	@Test
	public void testOverdeepHaves() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);
		final RevCommit e = commit(d);
		markStart(e);
		markUninteresting(a);
		dow.setDepth(2);

		assertCommit(e
		assertCommit(d
		assertCommit(c
		assertNull(dow.next());
		assertTrue(b.has(RevFlag.UNINTERESTING));
		assertTrue(a.has(RevFlag.UNINTERESTING));
		assertTrue(!c.has(dow.BOUNDARY));
	}

	@Test
	public void testOverdeepShallows() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);
		final RevCommit e = commit(d);
		shallows.add(b);
		markStart(e);
		markUninteresting(d);
		dow.setDepth(2);

		assertCommit(e
		assertCommit(d
		assertNull(dow.next());
		assertTrue(d.has(dow.BOUNDARY));
		assertTrue(b.has(RevFlag.UNINTERESTING));
	}

	@Test
	public void testDeepening() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);
		final RevCommit e = commit(d);
		shallows.add(c);
		markStart(e);
		markUninteresting(e);
		dow.setDepth(4);

		assertCommit(b
		assertCommit(a
		assertNull(dow.next());
	}

	@Test
	public void testDeepeningAndUpdate() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);
		final RevCommit e = commit(d);
		final RevCommit f = commit(e);
		shallows.add(c);
		markStart(f);
		markUninteresting(d);
		dow.setDepth(4);

		assertCommit(f
		assertCommit(e
		assertCommit(d
		assertTrue(d.has(dow.BOUNDARY));
		assertTrue(d.has(RevFlag.UNINTERESTING));
		assertCommit(b
		assertNull(dow.next());
		assertTrue(a.has(RevFlag.UNINTERESTING));
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
		shallows.add(b1);
		shallows.add(b2);
		markUninteresting(c1);
		markUninteresting(b2);
		markStart(e);
		dow.setDepth(4);

		assertCommit(e
		assertCommit(d
		assertCommit(c1
		assertTrue(c1.has(dow.BOUNDARY));
		assertTrue(c1.has(RevFlag.UNINTERESTING));
		assertCommit(c2
		assertCommit(b2
		assertTrue(b2.has(dow.BOUNDARY));
		assertTrue(b2.has(RevFlag.UNINTERESTING));
		assertCommit(a
		assertNull(dow.next());
		assertTrue(b1.has(RevFlag.UNINTERESTING));
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
		shallows.add(b1);
		markStart(e1);
		markStart(e2);
		markStart(e3);
		markUninteresting(c1);
		markUninteresting(c3);
		dow.setDepth(3);

		assertCommit(e3
		assertCommit(e2
		assertCommit(e1
		assertCommit(c3
		assertTrue(c3.has(dow.BOUNDARY));
		assertTrue(c3.has(RevFlag.UNINTERESTING));
		assertCommit(d
		assertCommit(c1
		assertTrue(c1.has(dow.BOUNDARY));
		assertTrue(c1.has(RevFlag.UNINTERESTING));
		assertCommit(c2
		assertCommit(b2
		assertNull(dow.next());
		assertTrue(a.has(RevFlag.UNINTERESTING));
		assertTrue(b2.has(dow.BOUNDARY));
		assertTrue(b2.has(RevFlag.UNINTERESTING));
	}

	@Test
	public void testBranchingHaves() throws Exception {
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
		shallows.add(b1);
		shallows.add(b2);
		markStart(e1);
		markStart(e3);
		markUninteresting(e2);
		dow.setDepth(3);

		assertCommit(e3
		assertCommit(e1
		assertCommit(c3
		assertTrue(c3.has(dow.BOUNDARY));
		assertTrue(c3.has(RevFlag.UNINTERESTING));
		assertCommit(c1
		assertTrue(c1.has(dow.BOUNDARY));
		assertTrue(c1.has(RevFlag.UNINTERESTING));
		assertCommit(a
		assertNull(dow.next());
	}

	@Test
	public void testIdenticalDepth() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);
		final RevCommit e = commit(d);
		shallows.add(c);
		markStart(e);
		markUninteresting(e);
		dow.setDepth(2);

		assertNull(dow.next());
	}

	@Test
	public void testBranchHole() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit b2 = commit(a);
		final RevCommit c1 = commit(b1);
		final RevCommit c2 = commit(b2);
		final RevCommit d = commit(c1
		final RevCommit e = commit(d);
		shallows.add(c2);
		markUninteresting(c2);
		markStart(e);
		dow.setDepth(4);

		assertCommit(e
		assertCommit(d
		assertCommit(c1
		assertCommit(c2
		assertTrue(c2.has(dow.BOUNDARY));
		assertTrue(c2.has(RevFlag.UNINTERESTING));
		assertCommit(b1
		assertCommit(b2
		assertTrue(!b2.has(RevFlag.UNINTERESTING));
		assertCommit(a
		assertNull(dow.next());
	}
}
