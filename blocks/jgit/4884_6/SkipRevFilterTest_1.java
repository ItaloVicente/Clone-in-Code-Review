package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;

import org.eclipse.jgit.revwalk.filter.SkipRevFilter;
import org.junit.Test;

public class SkipRevFilterTest extends RevWalkTestCase {
	@Test
	public void testSkipRevFilter() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(b);
		final RevCommit c2 = commit(b);
		final RevCommit d = commit(c1
		final RevCommit e = commit(d);

		rw.reset();
		rw.setRevFilter(SkipRevFilter.create(3));
		markStart(e);
		assertCommit(c1
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());

		rw.reset();
		rw.setRevFilter(SkipRevFilter.create(0));
		markStart(e);
		assertCommit(e
		assertCommit(d
		assertCommit(c2
		assertCommit(c1
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testSkipRevFilter0() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);

		rw.reset();
		rw.setRevFilter(SkipRevFilter.create(0));
		markStart(b);
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSkipRevFilterNegative() throws Exception {
		SkipRevFilter.create(-1);
	}
}
