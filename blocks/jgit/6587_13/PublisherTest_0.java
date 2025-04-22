
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
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.transport.PublisherStream.RefCounted;
import org.eclipse.jgit.transport.PublisherStream.Window;
import org.junit.Before;
import org.junit.Test;

public class PublisherStreamTest {

	StreamObj obj1 = new StreamObj();

	StreamObj obj2 = new StreamObj();

	StreamObj obj3 = new StreamObj();

	StreamObj obj4 = new StreamObj();

	class StreamObj implements RefCounted {
		private final AtomicInteger ref = new AtomicInteger();

		public void setReferences(int number) {
			ref.set(number);
		}

		public void decrement() {
			ref.decrementAndGet();
		}

		public boolean isZero() {
			return ref.get() == 0;
		}
	}

	PublisherStream<StreamObj> list;

	@Before
	public void setUp() {
		list = new PublisherStream<StreamObj>();
	}

	@Test
	public void testInsertion() throws Exception {
		Window<StreamObj> it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		assertListEquals(it
	}

	@Test
	public void testRollback() throws Exception {
		Window<StreamObj> it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		assertEquals(obj1
		it.mark();
		assertEquals(obj2
		it.mark();
		assertTrue(it.rollback(obj1));
		assertEquals(obj2
		it.mark();
		assertEquals(obj3
		it.mark();
		assertFalse(it.rollback(obj1));
	}

	@Test
	public void testRefCount() throws Exception {
		Window<StreamObj> it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		assertEquals(obj1
		assertEquals(obj2
		assertEquals(obj3
		assertTrue(obj1.isZero());
		assertTrue(obj2.isZero());
		assertFalse(obj3.isZero());
	}

	@Test
	public void testRefCountIterDelete() throws Exception {
		Window<StreamObj> it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		list.deleteIterator(it);
		assertTrue(obj1.isZero());
		assertTrue(obj2.isZero());
		assertTrue(obj3.isZero());
	}

	@Test
	public void testRefCountIterMarkDelete() throws Exception {
		Window<StreamObj> it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		it.next(1
		it.mark();
		list.deleteIterator(it);
		assertTrue(obj1.isZero());
		assertTrue(obj2.isZero());
		assertTrue(obj3.isZero());
	}

	@Test
	public void testRefCountIterMarkDelete2() throws Exception {
		Window<StreamObj> it = getIterator(2);
		list.add(obj1);
		list.add(obj2);
		list.add(obj3);
		it.next(1
		it.mark();
		it.next(1
		list.deleteIterator(it);
		assertTrue(obj1.isZero());
		assertTrue(obj2.isZero());
		assertTrue(obj3.isZero());
	}

	@Test
	public void testConcurrentInsertion() throws Exception {
		final List<StreamObj> items = new ArrayList<StreamObj>();
		for (int i = 0; i < 50; i++)
			items.add(new StreamObj());
		Window<StreamObj> it = getIterator(2);
		Thread produceThread = new Thread() {
			public void run() {
				for (int i = 0; i < 50; i++) {
					list.add(items.get(i));
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
		assertTrue(!it.hasNext());
	}

	@Test
	public void testConcurrentInsertionMultipleConsumers() throws Throwable {
		final List<StreamObj> items = new ArrayList<StreamObj>();
		for (int i = 0; i < 50; i++)
			items.add(new StreamObj());
		final CountDownLatch startLatch = new CountDownLatch(2);
		final Thread produceThread = new Thread() {
			public void run() {
				try {
					startLatch.await();
				} catch (InterruptedException e1) {
				}
				for (int i = 0; i < 50; i++) {
					list.add(items.get(i));
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
					Window<StreamObj> it = getIterator(2);
					startLatch.countDown();
					for (int i = 0; i < 50; i++) {
						try {
							assertEquals(
									items.get(i)
									it.next(1
						} catch (InterruptedException e) {
							fail("Unexpected interruption");
						}
					}
					while (produceThread.isAlive()) {
					}
					assertTrue(!it.hasNext());
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
		Window<StreamObj> it = getIterator(2);

		Thread produceThread = new Thread() {
			public void run() {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
				}
				list.add(obj1);
			}
		};
		produceThread.start();
		assertNull(it.next(1
		assertEquals(obj1
		produceThread.join();
		assertEquals(null
		assertTrue(!it.hasNext());
	}

	private Window<StreamObj> getIterator(int capacity) {
		Window<StreamObj> it = list.newIterator(capacity);
		try {
			assertEquals(null
		} catch (InterruptedException e) {
		}
		return it;
	}

	private void assertListEquals(Window<StreamObj> it
			throws InterruptedException {
		for (int i = 0; i < expected.length; i++)
			assertEquals(expected[i]
		assertTrue(!it.hasNext());
	}
}
