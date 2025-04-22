
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.transport.PublisherStream.Window;
import org.junit.Before;
import org.junit.Test;

public class PublisherStreamTest {

	PublisherPushTest obj1 = new PublisherPushTest("1");

	PublisherPushTest obj2 = new PublisherPushTest("2");

	PublisherPushTest obj3 = new PublisherPushTest("3");

	PublisherPushTest obj4 = new PublisherPushTest("4");

	class PublisherPushTest extends PublisherPush {

		private boolean closed;

		public PublisherPushTest(String id) {
			super(null
		}

		@Override
		public synchronized void close() throws PublisherException {
			closed = true;
		}

		public boolean isClosed() {
			return closed;
		}
	}

	PublisherStream list;

	@Before
	public void setUp() {
		list = new PublisherStream();
	}

	@Test
	public void testInsertion() throws Exception {
		Window it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		assertListEquals(it
	}

	@Test
	public void testCreation() throws Exception {
		list.add(obj1);
		Window it = getIterator(2);
		list.add(obj2);
		list.add(obj3);
		assertListEquals(it
	}

	@Test
	public void testPrepend() throws Exception {
		list.add(obj1);
		Window it = getIterator(2);
		PublisherPushTest p1 = new PublisherPushTest("p1");
		PublisherPushTest p2 = new PublisherPushTest("p2");
		it.prepend(p2);
		it.prepend(p1);
		list.add(obj2);
		list.add(obj3);
		assertListEquals(it
	}

	@Test
	public void testRollback() throws Exception {
		Window it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		assertEquals(obj1
		it.mark();
		assertEquals(obj2
		it.mark();
		assertTrue(it.rollback("1"));
		assertEquals(obj2
		it.mark();
		assertEquals(obj3
		it.mark();
		assertFalse(it.rollback("1"));
		assertTrue(obj1.isClosed());
		assertFalse(obj2.isClosed());
	}

	@Test
	public void testRollbackMidStream() throws Exception {
		list.add(obj1);
		Window it = getIterator(2);
		PublisherPushTest p1 = new PublisherPushTest("p1");
		PublisherPushTest p2 = new PublisherPushTest("p2");
		it.prepend(p2);
		it.prepend(p1);
		list.add(obj2);
		list.add(obj3);
		assertEquals(p1
		it.mark();
		assertEquals(p2
		it.mark();
		assertTrue(it.rollback("p2"));
		assertFalse(p1.isClosed());
		assertFalse(p2.isClosed());
		assertEquals(obj2
		it.mark();
		assertTrue(p1.isClosed());
		assertListEquals(it
	}

	@Test
	public void testRefCount() throws Exception {
		Window it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		assertEquals(obj1
		assertEquals(obj2
		assertEquals(obj3
		assertTrue(obj1.isClosed());
		assertTrue(obj2.isClosed());
		assertFalse(obj3.isClosed());
	}

	@Test
	public void testRefCountIterDelete() throws Exception {
		Window it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		it.release();
		assertTrue(obj1.isClosed());
		assertTrue(obj2.isClosed());
		assertTrue(obj3.isClosed());
	}

	@Test
	public void testRefCountIterMarkDelete() throws Exception {
		Window it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		it.next(1
		it.mark();
		it.release();
		assertTrue(obj1.isClosed());
		assertTrue(obj2.isClosed());
		assertTrue(obj3.isClosed());
	}

	@Test
	public void testRefCountIterMarkDelete2() throws Exception {
		list.add(obj1);
		Window it = getIterator(2);
		PublisherPushTest p1 = new PublisherPushTest("p1");
		PublisherPushTest p2 = new PublisherPushTest("p2");
		it.prepend(p2);
		it.prepend(p1);
		list.add(obj2);
		list.add(obj3);
		it.next(1
		it.mark();
		it.next(1
		it.release();
		assertTrue(obj1.isClosed());
		assertTrue(obj2.isClosed());
		assertTrue(obj3.isClosed());
		assertTrue(p1.isClosed());
		assertTrue(p2.isClosed());
	}

	@Test
	public void testConcurrentInsertion() throws Exception {
		final List<PublisherPushTest> items = new ArrayList<
				PublisherPushTest>();
		for (int i = 0; i < 50; i++)
			items.add(new PublisherPushTest(String.valueOf(i)));
		Window it = getIterator(2);
		Thread produceThread = new Thread() {
			public void run() {
				for (int i = 0; i < 50; i++) {
					try {
						list.add(items.get(i));
					} catch (PublisherException e1) {
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						fail("Unexpected interruption");
					}
				}
			}
		};
		produceThread.start();

		for (int i = 0; i < 50; i++)
			assertEquals(items.get(i)
		produceThread.join();
		assertEmpty(it);
	}

	@Test
	public void testConcurrentInsertionMultipleConsumers() throws Throwable {
		final List<PublisherPushTest> items = new ArrayList<
				PublisherPushTest>();
		for (int i = 0; i < 50; i++)
			items.add(new PublisherPushTest(String.valueOf(i)));
		final CountDownLatch startLatch = new CountDownLatch(2);
		final Thread produceThread = new Thread() {
			public void run() {
				try {
					startLatch.await();
				} catch (InterruptedException e1) {
				}
				for (int i = 0; i < 50; i++) {
					try {
						list.add(items.get(i));
					} catch (PublisherException e1) {
					}
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
					try {
						Window it = getIterator(2);
						startLatch.countDown();
						for (int i = 0; i < 50; i++) {
							assertEquals(
									items.get(i)
						}
						while (produceThread.isAlive()) {
						}
						assertEmpty(it);
					} catch (Exception e) {
						fail("Unexpected exception " + e);
					}
				}
			};
			t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				public void uncaughtException(Thread t2
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
		Window it = getIterator(2);

		Thread produceThread = new Thread() {
			public void run() {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
				}
				try {
					list.add(obj1);
				} catch (PublisherException e) {
				}
			}
		};
		produceThread.start();
		assertNull(it.next(1
		assertEquals(obj1
		produceThread.join();
		assertEquals(null
		assertEmpty(it);
	}

	private Window getIterator(int capacity) {
		return list.newWindow(capacity);
	}

	private void assertListEquals(Window it
			throws InterruptedException
		for (int i = 0; i < expected.length; i++)
			assertEquals(expected[i].getPushId()
					it.next(1
		assertEmpty(it);
	}

	private static void assertEmpty(Window it)
			throws PublisherException
		assertNull(it.next(1
	}
}
