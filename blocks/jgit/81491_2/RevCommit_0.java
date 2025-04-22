
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RevWalkCarryFlagsTest extends RevWalkTestCase {
	@Test
	public void testRevWalkCarryUninteresting_fastClock() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(c);
		final RevCommit e = commit(b

		markStart(d);
		markUninteresting(e);
		assertNull("Found an unexpected commit"
	}

	@Test
	public void testRevWalkCarryUninteresting_SlowClock() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(c);
		final RevCommit e = commit(0

		markStart(d);
		markUninteresting(e);
		assertNull("Found an unexpected commit"
	}

	@Test
	public void testRevWalkCarryUninteresting_WrongClock() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(c);
		final RevCommit e = commit(-1

		markStart(d);
		markUninteresting(e);
		assertNull("Found an unexpected commit"
	}

	@Test
	public void testRevWalkCarryCustom_SlowClock() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(c);
		final RevCommit e = commit(0

		markStart(d);
		markStart(e);
		RevFlag customFlag = rw.newFlag("CUSTOM");
		e.flags |= customFlag.mask;
		rw.carry(customFlag);

		int count = 0;
		for (RevCommit cm : rw) {
			assertTrue(
					"Found a commit which doesn't have the custom flag: " + cm
					cm.has(customFlag));
			count++;
		}
		assertTrue("Didn't walked over all commits"
	}
}
