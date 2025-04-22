
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RevWalkSortTest extends RevWalkTestCase {
	@Test
	public void testSort_Default() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(1
		final RevCommit c = commit(1
		final RevCommit d = commit(1

		markStart(d);
		assertCommit(d
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testSort_COMMIT_TIME_DESC() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);

		rw.sort(RevSort.COMMIT_TIME_DESC);
		markStart(d);
		assertCommit(d
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testSort_REVERSE() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);

		rw.sort(RevSort.REVERSE);
		markStart(d);
		assertCommit(a
		assertCommit(b
		assertCommit(c
		assertCommit(d
		assertNull(rw.next());
	}

	@Test
	public void testSort_COMMIT_TIME_DESC_OutOfOrder1() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(-5
		final RevCommit d = commit(10
		assertTrue(parseBody(a).getCommitTime() < parseBody(d).getCommitTime());
		assertTrue(parseBody(c).getCommitTime() < parseBody(b).getCommitTime());

		rw.sort(RevSort.COMMIT_TIME_DESC);
		markStart(d);
		assertCommit(d
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testSort_COMMIT_TIME_DESC_OutOfOrder2() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(-5
		final RevCommit c2 = commit(10
		final RevCommit d = commit(c1

		rw.sort(RevSort.COMMIT_TIME_DESC);
		markStart(d);
		assertCommit(d
		assertCommit(c2
		assertCommit(b
		assertCommit(a
		assertCommit(c1
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(-5
		final RevCommit c2 = commit(10
		final RevCommit d = commit(c1

		rw.sort(RevSort.TOPO);
		markStart(d);
		assertCommit(d
		assertCommit(c2
		assertCommit(c1
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testSort_TOPO_REVERSE() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(-5
		final RevCommit c2 = commit(10
		final RevCommit d = commit(c1

		rw.sort(RevSort.TOPO);
		rw.sort(RevSort.REVERSE
		markStart(d);
		assertCommit(a
		assertCommit(b
		assertCommit(c1
		assertCommit(c2
		assertCommit(d
		assertNull(rw.next());
	}
}
