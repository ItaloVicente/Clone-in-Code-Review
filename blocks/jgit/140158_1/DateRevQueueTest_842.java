
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class AlwaysEmptyRevQueueTest extends RevWalkTestCase {
	private final AbstractRevQueue q = AbstractRevQueue.EMPTY_QUEUE;

	@Test
	public void testEmpty() throws Exception {
		assertNull(q.next());
		assertTrue(q.everbodyHasFlag(RevWalk.UNINTERESTING));
		assertFalse(q.anybodyHasFlag(RevWalk.UNINTERESTING));
		assertEquals(0
	}

	@Test
	public void testClear() throws Exception {
		q.clear();
		testEmpty();
	}

	@Test
	public void testAddFails() throws Exception {
		try {
			q.add(commit());
			fail("Did not throw UnsupportedOperationException");
		} catch (UnsupportedOperationException e) {
		}
	}
}
