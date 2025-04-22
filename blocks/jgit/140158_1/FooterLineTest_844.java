
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;

import org.junit.Test;

public class FIFORevQueueTest extends RevQueueTestCase<FIFORevQueue> {
	@Override
	protected FIFORevQueue create() {
		return new FIFORevQueue();
	}

	@Override
	@Test
	public void testEmpty() throws Exception {
		super.testEmpty();
		assertEquals(0
	}

	@Test
	public void testCloneEmpty() throws Exception {
		q = new FIFORevQueue(AbstractRevQueue.EMPTY_QUEUE);
		assertNull(q.next());
	}

	@Test
	public void testAddLargeBlocks() throws Exception {
		final ArrayList<RevCommit> lst = new ArrayList<>();
		for (int i = 0; i < 3 * BlockRevQueue.Block.BLOCK_SIZE; i++) {
			final RevCommit c = commit();
			lst.add(c);
			q.add(c);
		}
		for (int i = 0; i < lst.size(); i++)
			assertSame(lst.get(i)
	}

	@Test
	public void testUnpopAtFront() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit();
		final RevCommit c = commit();

		q.add(a);
		q.unpop(b);
		q.unpop(c);

		assertSame(c
		assertSame(b
		assertSame(a
	}
}
