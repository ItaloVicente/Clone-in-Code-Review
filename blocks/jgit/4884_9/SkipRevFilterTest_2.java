package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;

import org.eclipse.jgit.revwalk.filter.SkipRevFilter;
import org.junit.Test;

public class SkipRevFilterTest extends RevWalkTestCase {
	@Test
	public void testSkipRevFilter() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit b2 = commit(a);
		final RevCommit c = commit(b1
		final RevCommit d = commit(c);

		rw.reset();
		rw.setRevFilter(SkipRevFilter.create(3));
		markStart(d);
		assertCommit(b1
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
