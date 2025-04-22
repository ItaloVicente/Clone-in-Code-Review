
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;

import org.eclipse.jgit.revwalk.filter.AndRevFilter;
import org.eclipse.jgit.revwalk.filter.FirstParentRevFilter;
import org.eclipse.jgit.revwalk.filter.MessageRevFilter;
import org.eclipse.jgit.revwalk.filter.OrRevFilter;
import org.junit.Test;

public class FirstParentRevFilterTest extends RevWalkTestCase {
	@Test
	public void testStringOfPearls() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);
		RevCommit c = commit(b);

		rw.reset();
		rw.setRevFilter(FirstParentRevFilter.create(rw));
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
		rw.setRevFilter(FirstParentRevFilter.create(rw));
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
		rw.setRevFilter(FirstParentRevFilter.create(rw));
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
		rw.setRevFilter(FirstParentRevFilter.create(rw));
		markStart(c);
		markStart(b2);
		assertCommit(c
		assertCommit(b2
		assertCommit(b1
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testAndFilter() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commitBuilder().parent(a).message("commit b1").create();
		RevCommit b2 = commitBuilder().parent(a).message("commit b2").create();
		RevCommit c = commit(b1

		rw.reset();
		rw.setRevFilter(AndRevFilter.create(
				FirstParentRevFilter.create(rw)
				MessageRevFilter.create("commit b")));
		rw.markStart(c);
		assertCommit(b1
		assertNull(rw.next());
	}

	@Test
	public void testOrFilter() throws Exception {
		RevCommit a = commit();
		RevCommit b1 = commit(a);
		RevCommit b2 = commitBuilder().parent(a).message("commit b2").create();
		RevCommit c = commit(b1

		rw.reset();
		rw.setRevFilter(OrRevFilter.create(
				MessageRevFilter.create("b2")
				FirstParentRevFilter.create(rw)));
		rw.markStart(c);
		assertCommit(c
		assertCommit(b2
		assertCommit(b1
		assertCommit(a
		assertNull(rw.next());
	}
}
