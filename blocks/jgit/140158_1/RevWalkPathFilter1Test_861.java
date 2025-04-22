package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RevWalkMergedIntoTest extends RevWalkTestCase {

	@Test
	public void testOldCommitWalk() throws Exception {
		final int threeDaysInSecs = 3 * 24 * 60 * 60;
		final RevCommit f = commit();
		final RevCommit b = commit(f);
		final RevCommit o = commit(-threeDaysInSecs
		final RevCommit n = commit(commit(commit(commit(commit(f)))));
		final RevCommit t = commit(n
		assertTrue(rw.isMergedInto(b
	}
}
