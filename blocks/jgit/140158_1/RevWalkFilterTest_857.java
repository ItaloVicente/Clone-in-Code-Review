
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class RevWalkCullTest extends RevWalkTestCase {
	@Test
	public void testProperlyCullAllAncestors1() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(-2400
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);

		markStart(a);
		markUninteresting(d);
		assertNull(rw.next());
	}

	@Test
	public void testProperlyCullAllAncestors2() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(-5
		final RevCommit c2 = commit(10
		final RevCommit d = commit(c1

		markStart(d);
		markUninteresting(c1);
		assertCommit(d
		assertCommit(c2
		assertNull(rw.next());
	}

	@Test
	public void testProperlyCullAllAncestors_LongHistory() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);
		for (int i = 0; i < 24; i++) {
			b = commit(b);
			if ((i & 2) == 0)
				markUninteresting(b);
		}
		final RevCommit c = commit(b);

		rw.close();
		rw = createRevWalk();
		RevCommit a2 = rw.lookupCommit(a);
		markStart(c);
		markUninteresting(b);
		assertCommit(c
		assertNull(rw.next());

		assertNull(a2.parents);
	}
}
