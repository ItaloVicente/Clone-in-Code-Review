
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class DateRevQueueTest extends RevQueueTestCase<DateRevQueue> {
	@Override
	protected DateRevQueue create() {
		return new DateRevQueue();
	}

	@Override
	@Test
	public void testEmpty() throws Exception {
		super.testEmpty();
		assertNull(q.peek());
		assertEquals(Generator.SORT_COMMIT_TIME_DESC
	}

	@Test
	public void testCloneEmpty() throws Exception {
		q = new DateRevQueue(AbstractRevQueue.EMPTY_QUEUE);
		assertNull(q.next());
	}

	@Test
	public void testInsertOutOfOrder() throws Exception {
		final RevCommit a = parseBody(commit());
		final RevCommit b = parseBody(commit(10
		final RevCommit c1 = parseBody(commit(5
		final RevCommit c2 = parseBody(commit(-50

		q.add(c2);
		q.add(a);
		q.add(b);
		q.add(c1);

		assertCommit(c1
		assertCommit(b
		assertCommit(a
		assertCommit(c2
		assertNull(q.next());
	}

	@Test
	public void testInsertTie() throws Exception {
		final RevCommit a = parseBody(commit());
		final RevCommit b = parseBody(commit(0
		{
			q = create();
			q.add(a);
			q.add(b);

			assertCommit(a
			assertCommit(b
			assertNull(q.next());
		}
		{
			q = create();
			q.add(b);
			q.add(a);

			assertCommit(b
			assertCommit(a
			assertNull(q.next());
		}
	}

	@Test
	public void testCloneFIFO() throws Exception {
		final RevCommit a = parseBody(commit());
		final RevCommit b = parseBody(commit(200
		final RevCommit c = parseBody(commit(200

		final FIFORevQueue src = new FIFORevQueue();
		src.add(a);
		src.add(b);
		src.add(c);

		q = new DateRevQueue(src);
		assertFalse(q.everbodyHasFlag(RevWalk.UNINTERESTING));
		assertFalse(q.anybodyHasFlag(RevWalk.UNINTERESTING));
		assertCommit(c
		assertCommit(c

		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(q.next());
	}
}
