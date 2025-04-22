package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;

import org.eclipse.jgit.revwalk.filter.MaxCountRevFilter;
import org.junit.Test;

public class MaxCountRevFilterTest extends RevWalkTestCase {
	@Test
	public void testMaxCountRevFilter() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(b);
		final RevCommit c2 = commit(b);
		final RevCommit d = commit(c1
		final RevCommit e = commit(d);

		rw.reset();
		rw.setRevFilter(MaxCountRevFilter.create(3));
		markStart(e);
		assertCommit(e
		assertCommit(d
		assertCommit(c2
		assertNull(rw.next());

		rw.reset();
		rw.setRevFilter(MaxCountRevFilter.create(0));
		markStart(e);
		assertNull(rw.next());
	}

	@Test
	public void testMaxCountRevFilter0() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);

		rw.reset();
		rw.setRevFilter(MaxCountRevFilter.create(0));
		markStart(b);
		assertNull(rw.next());
	}
}
