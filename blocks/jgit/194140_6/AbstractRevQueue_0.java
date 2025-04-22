
package org.eclipse.jgit.revwalk;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateRevPriorityQueueTest extends RevQueueTestCase<DateRevPriorityQueue> {
	@Override
	protected DateRevPriorityQueue create() {
		return new DateRevPriorityQueue();
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
		q = new DateRevPriorityQueue(AbstractRevQueue.EMPTY_QUEUE);
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

	@Ignore
	public void test1MCommits() throws Exception {
		int numCommits = 850000;
		System.out.println("Creating " + numCommits + " commits ...");
		RevCommit[] commits = new RevCommit[numCommits];
		RevCommit parent = commit();
		commits[0] = parent;
		for (int i=1; i<numCommits; i++) {
			parent = parseBody(commit(i
			commits[i] = parent;
			if(i%10000 == 0) {
				System.out.println(" " + i + " done");
			}
		}

		System.out.println("Adding " + numCommits + " commits to priority queue ...");
		long startTime = System.nanoTime();
		DateRevPriorityQueue sortedCommits = new DateRevPriorityQueue(false
		for (RevCommit commit : commits) {
			sortedCommits.add(commit);
		}
		System.out.println("Done in " + (System.nanoTime() - startTime)/1000000 + " msec");

		System.out.println("Adding " + numCommits + " commits to the queue ...");
		startTime = System.nanoTime();
		for (RevCommit commit : commits) {
			q.add(commit);
		}
		System.out.println("Done in " + (System.nanoTime() - startTime)/1000000 + " msec");
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

		q = new DateRevPriorityQueue(src);
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
