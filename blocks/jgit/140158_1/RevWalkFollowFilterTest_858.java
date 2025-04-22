
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Date;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.filter.AndRevFilter;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.NotRevFilter;
import org.eclipse.jgit.revwalk.filter.OrRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.junit.Test;

public class RevWalkFilterTest extends RevWalkTestCase {
	private static final MyAll MY_ALL = new MyAll();

	@Test
	public void testFilter_ALL() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(RevFilter.ALL);
		markStart(c);
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testFilter_Negate_ALL() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(RevFilter.ALL.negate());
		markStart(c);
		assertNull(rw.next());
	}

	@Test
	public void testFilter_NOT_ALL() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(NotRevFilter.create(RevFilter.ALL));
		markStart(c);
		assertNull(rw.next());
	}

	@Test
	public void testFilter_NONE() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(RevFilter.NONE);
		markStart(c);
		assertNull(rw.next());
	}

	@Test
	public void testFilter_NOT_NONE() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(NotRevFilter.create(RevFilter.NONE));
		markStart(c);
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testFilter_ALL_And_NONE() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(AndRevFilter.create(RevFilter.ALL
		markStart(c);
		assertNull(rw.next());
	}

	@Test
	public void testFilter_NONE_And_ALL() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(AndRevFilter.create(RevFilter.NONE
		markStart(c);
		assertNull(rw.next());
	}

	@Test
	public void testFilter_ALL_Or_NONE() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(OrRevFilter.create(RevFilter.ALL
		markStart(c);
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testFilter_NONE_Or_ALL() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(OrRevFilter.create(RevFilter.NONE
		markStart(c);
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testFilter_MY_ALL_And_NONE() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(AndRevFilter.create(MY_ALL
		markStart(c);
		assertNull(rw.next());
	}

	@Test
	public void testFilter_NONE_And_MY_ALL() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(AndRevFilter.create(RevFilter.NONE
		markStart(c);
		assertNull(rw.next());
	}

	@Test
	public void testFilter_MY_ALL_Or_NONE() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(OrRevFilter.create(MY_ALL
		markStart(c);
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testFilter_NONE_Or_MY_ALL() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		rw.setRevFilter(OrRevFilter.create(RevFilter.NONE
		markStart(c);
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testFilter_NO_MERGES() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c1 = commit(b);
		final RevCommit c2 = commit(b);
		final RevCommit d = commit(c1
		final RevCommit e = commit(d);

		rw.setRevFilter(RevFilter.NO_MERGES);
		markStart(e);
		assertCommit(e
		assertCommit(c2
		assertCommit(c1
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testCommitTimeRevFilter() throws Exception {
		final RevCommit a = commit();
		tick(100);

		final RevCommit b = commit(a);
		tick(100);

		Date since = getDate();
		final RevCommit c1 = commit(b);
		tick(100);

		final RevCommit c2 = commit(b);
		tick(100);

		Date until = getDate();
		final RevCommit d = commit(c1
		tick(100);

		final RevCommit e = commit(d);

		{
			RevFilter after = CommitTimeRevFilter.after(since);
			assertNotNull(after);
			rw.setRevFilter(after);
			markStart(e);
			assertCommit(e
			assertCommit(d
			assertCommit(c2
			assertCommit(c1
			assertNull(rw.next());
		}

		{
			RevFilter before = CommitTimeRevFilter.before(until);
			assertNotNull(before);
			rw.reset();
			rw.setRevFilter(before);
			markStart(e);
			assertCommit(c2
			assertCommit(c1
			assertCommit(b
			assertCommit(a
			assertNull(rw.next());
		}

		{
			RevFilter between = CommitTimeRevFilter.between(since
			assertNotNull(between);
			rw.reset();
			rw.setRevFilter(between);
			markStart(e);
			assertCommit(c2
			assertCommit(c1
			assertNull(rw.next());
		}
	}

	private static class MyAll extends RevFilter {
		@Override
		public RevFilter clone() {
			return this;
		}

		@Override
		public boolean include(RevWalk walker
				throws StopWalkException
				IncorrectObjectTypeException
			return true;
		}

		@Override
		public boolean requiresCommitBody() {
			return false;
		}
	}
}
