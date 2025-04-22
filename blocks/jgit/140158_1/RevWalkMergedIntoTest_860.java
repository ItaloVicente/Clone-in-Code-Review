
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.junit.Test;

public class RevWalkMergeBaseTest extends RevWalkTestCase {
	@Test
	public void testNone() throws Exception {
		final RevCommit c1 = commit(commit(commit()));
		final RevCommit c2 = commit(commit(commit()));

		rw.setRevFilter(RevFilter.MERGE_BASE);
		markStart(c1);
		markStart(c2);
		assertNull(rw.next());
	}

	@Test
	public void testDisallowTreeFilter() throws Exception {
		final RevCommit c1 = commit();
		final RevCommit c2 = commit();

		rw.setRevFilter(RevFilter.MERGE_BASE);
		rw.setTreeFilter(TreeFilter.ANY_DIFF);
		markStart(c1);
		markStart(c2);
		try {
			assertNull(rw.next());
			fail("did not throw IllegalStateException");
		} catch (IllegalStateException ise) {
		}
	}

	@Test
	public void testSimple() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(commit(commit(commit(commit(b)))));
		final RevCommit c2 = commit(commit(commit(commit(commit(b)))));

		rw.setRevFilter(RevFilter.MERGE_BASE);
		markStart(c1);
		markStart(c2);
		assertCommit(b
		assertNull(rw.next());
	}

	@Test
	public void testMultipleHeads_SameBase1() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(commit(commit(commit(commit(b)))));
		final RevCommit c2 = commit(commit(commit(commit(commit(b)))));
		final RevCommit c3 = commit(commit(commit(b)));

		rw.setRevFilter(RevFilter.MERGE_BASE);
		markStart(c1);
		markStart(c2);
		markStart(c3);
		assertCommit(b
		assertNull(rw.next());
	}

	@Test
	public void testMultipleHeads_SameBase2() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d1 = commit(commit(commit(commit(commit(b)))));
		final RevCommit d2 = commit(commit(commit(commit(commit(c)))));
		final RevCommit d3 = commit(commit(commit(c)));

		rw.setRevFilter(RevFilter.MERGE_BASE);
		markStart(d1);
		markStart(d2);
		markStart(d3);
		assertCommit(b
		assertNull(rw.next());
	}

	@Test
	public void testCrissCross() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(b
		final RevCommit e = commit(c

		rw.setRevFilter(RevFilter.MERGE_BASE);
		markStart(d);
		markStart(e);
		assertCommit(c
		assertCommit(b
		assertNull(rw.next());
	}

	@Test
	public void testInconsistentCommitTimes() throws Exception {

		final RevCommit a = commit(2);
		final RevCommit b = commit(-1
		final RevCommit c = commit(2
		final RevCommit d = commit(1

		rw.setRevFilter(RevFilter.MERGE_BASE);
		markStart(d);
		markStart(c);
		assertCommit(b
		assertNull(rw.next());
	}

}
