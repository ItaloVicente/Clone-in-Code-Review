
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;

import org.eclipse.jgit.revwalk.filter.FirstParentRevFilter;
import org.junit.Test;

public class FirstParentRevFilterTest extends RevWalkTestCase {
	private void setUpRevWalk() {
		rw.reset();
		rw.setRevFilter(new FirstParentRevFilter(rw));
	}

	@Test
	public void testStringOfPearls() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);
		RevCommit c = commit(b);

		setUpRevWalk();
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

		setUpRevWalk();
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

		setUpRevWalk();
		markStart(c);
		markStart(d);
		assertCommit(d
		assertCommit(c
		assertCommit(b2
		assertCommit(b1
		assertCommit(a
		assertNull(rw.next());
	}
}
