
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.jgit.revwalk.filter.MessageRevFilter;
import org.junit.Test;

public class FirstParentRevWalkTest extends RevWalkTestCase {
	@Test
	public void testStringOfPearls() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);
		RevCommit c = commit(b);

		rw.reset();
		rw.setFirstParent(true);
		markStart(c);
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testSideBranch() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(a);
		RevCommit c = commit(b1

		rw.reset();
		rw.setFirstParent(true);
		markStart(c);
		assertCommit(c
		assertCommit(b1
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testReachableAlongFirstAndLaterParents()
			throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(a);
		RevCommit b3 = commit(a);
		RevCommit c = commit(b1
		RevCommit d = commit(b2

		rw.reset();
		rw.setFirstParent(true);
		markStart(c);
		markStart(d);
		assertCommit(d
		assertCommit(c
		assertCommit(b2
		assertCommit(b1
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testStartCommitReachableOnlyFromLaterParents()
		throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(a);
		RevCommit c = commit(b1

		rw.reset();
		rw.setFirstParent(true);
		markStart(c);
		markStart(b2);
		assertCommit(c
		assertCommit(b2
		assertCommit(b1
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testRevFilter() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commitBuilder().parent(a).message("commit b1").create();
		RevCommit b2 = commitBuilder().parent(a).message("commit b2").create();
		RevCommit c = commit(b1

		rw.reset();
		rw.setFirstParent(true);
		rw.setRevFilter(MessageRevFilter.create("commit b"));
		rw.markStart(c);
		assertCommit(b1
		assertNull(rw.next());
	}

	@Test
	public void testTopoSort() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(a);
		RevCommit c = commit(b1

		rw.reset();
		rw.sort(RevSort.TOPO);
		rw.setFirstParent(true);
		markStart(c);
		assertCommit(c
		assertCommit(b1
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testCommitTimeSort() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(a);
		RevCommit c = commit(b1

		rw.reset();
		rw.sort(RevSort.COMMIT_TIME_DESC);
		rw.setFirstParent(true);
		markStart(c);
		assertCommit(c
		assertCommit(b1
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testReverseSort() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(a);
		RevCommit c = commit(b1

		rw.reset();
		rw.sort(RevSort.REVERSE);
		rw.setFirstParent(true);
		markStart(c);
		assertCommit(a
		assertCommit(b1
		assertCommit(c
		assertNull(rw.next());
	}

	@Test
	public void testBoundarySort() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);
		RevCommit c1 = commit(b);
		RevCommit c2 = commit(b);
		RevCommit d = commit(c1

		rw.reset();
		rw.sort(RevSort.BOUNDARY);
		rw.setFirstParent(true);
		markStart(d);
		markUninteresting(a);
		assertCommit(d
		assertCommit(c1
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testDepthWalk() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commit(a);
		RevCommit c = commit(b1

		try (DepthWalk.RevWalk dw = new DepthWalk.RevWalk(db
			dw.setFirstParent(true);
			dw.markRoot(dw.parseCommit(c));
			dw.markStart(dw.parseCommit(c));
			assertEquals(c
			assertEquals(b1
			assertNull(dw.next());
		}
	}
}
