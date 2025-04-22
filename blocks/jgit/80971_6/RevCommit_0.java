
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class RevWalkCommitsSameDateTest extends RevWalkTestCase {
	@DataPoints
	public static int[] deltaBetweenCommits = { 1

	@Theory
	public void testRevwalkOnMergedCommits_SameDate(int delta)
			throws Exception {
		final RevCommit a = commit(delta);
		final RevCommit b = commit(delta
		final RevCommit c = commit(delta
		final RevCommit d = commit(delta
		final RevCommit e = commit(delta

		markStart(d);
		markUninteresting(e);
		assertNull("Found an unexpected commit. Delta between commits was "
				+ delta
	}
}
