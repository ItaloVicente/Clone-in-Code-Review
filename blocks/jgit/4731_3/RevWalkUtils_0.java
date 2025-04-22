
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RevWalkUtilsCountTest extends RevWalkTestCase {

	@Test
	public void shouldWorkForNormalCase() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);

		assertEquals(1
	}

	@Test
	public void shouldReturnZeroOnSameCommit() throws Exception {
		final RevCommit c1 = commit(commit(commit()));
		assertEquals(0
	}

	@Test
	public void shouldReturnZeroWhenMergedInto() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);

		assertEquals(0
	}

	@Test
	public void shouldWorkWithMerges() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit(a);
		final RevCommit b2 = commit(a);
		final RevCommit c = commit(b1

		assertEquals(3
	}

	@Test
	public void shouldWorkWithoutCommonAncestor() throws Exception {
		final RevCommit a1 = commit();
		final RevCommit a2 = commit();
		final RevCommit b = commit(a1);

		assertEquals(2
	}

	@Test
	public void shouldWorkWithZeroAsEnd() throws Exception {
		final RevCommit c = commit(commit());

		assertEquals(2
	}

	private int count(RevCommit start
		return RevWalkUtils.count(rw
	}
}
