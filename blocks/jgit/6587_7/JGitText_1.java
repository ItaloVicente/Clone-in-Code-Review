
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.util.ConcurrentLinkedList.ConcurrentIterator;
import org.junit.Before;
import org.junit.Test;

public class ConcurrentLinkedListTest {

	ConcurrentLinkedList<Integer> list;

	@Before
	public void setUp() {
		list = new ConcurrentLinkedList<Integer>();
	}

	@Test
	public void testInsertion() throws Exception {
		list.put(10);
		list.put(20);
		list.put(30);
		assertListEquals(new int[] { 10
	}

	@Test
	public void testDeletion() throws Exception {
		testInsertion();
		Iterator<Integer> it = list.getWriteIterator();
		int items = 0;
		while (it.hasNext()) {
			it.next();
			it.remove();
			items++;
		}
		assertEquals(3
		assertListEquals(new int[] { 30 });
	}

	@Test
	public void testConcurrentInsertion() throws Exception {
		Thread produceThread = new Thread() {
			public void run() {
				for (int i = 0; i < 50; i++) {
					list.put(i);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						fail("Unexpected interruption");
					}
				}
			}
		};
		produceThread.start();
		ConcurrentIterator<Integer> it = list.getHeadIterator();
		for (int i = 0; i < 50; i++) {
			assertEquals(new Integer(i)
		}
		produceThread.join();
		assertTrue(!it.hasNext());
	}

	@Test
	public void testConcurrentInsertionMultipleConsumers() throws Throwable {
		final Thread produceThread = new Thread() {
			public void run() {
				for (int i = 0; i < 50; i++) {
					list.put(i);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						fail("Unexpected interruption");
					}
				}
			}
		};
		final List<Throwable> threadExceptions = new ArrayList<Throwable>();
		List<Thread> consumeThreads = new ArrayList<Thread>();
		produceThread.start();
		for (int j = 0; j < 2; j++) {
			Thread t = new Thread() {
				public void run() {
					ConcurrentIterator<Integer> it = list.getHeadIterator();
					for (int i = 0; i < 50; i++) {
						try {
							assertEquals(new Integer(i)
									it.next(1
						} catch (InterruptedException e) {
							fail("Unexpected interruption");
						} catch (TimeoutException e) {
							fail("Unexpected timeout");
						}
					}
					while (produceThread.isAlive()) {
					}
					assertTrue(!it.hasNext());
				}
			};
			t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				public void uncaughtException(Thread t
					threadExceptions.add(e);
				}
			});
			t.start();
			consumeThreads.add(t);
		}
		produceThread.join();
		for (Thread t : consumeThreads)
			t.join();
		for (Throwable e : threadExceptions)
			throw e;
	}

	@Test
	public void testConsumeTimeout() throws Exception {
		Thread produceThread = new Thread() {
			public void run() {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
				}
				list.put(1);
			}
		};
		produceThread.start();
		ConcurrentIterator<Integer> it = list.getHeadIterator();
		try {
			it.next(1
			fail("Should have timed out");
		} catch (TimeoutException e) {
		}
		assertEquals(new Integer(1)
		produceThread.join();
		assertTrue(!it.hasNext());
	}

	private void assertListEquals(int[] expected)
			throws InterruptedException
		ConcurrentIterator<Integer> it = list.getHeadIterator();
		for (int i = 0; i < expected.length; i++)
			assertEquals(
					new Integer(expected[i])
		assertTrue(!it.hasNext());
	}
}
