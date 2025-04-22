
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ThreadSafeProgressMonitorTest {
	@Test
	public void testFailsMethodsOnBackgroundThread()
			throws InterruptedException {
		final MockProgressMonitor mock = new MockProgressMonitor();
		final ThreadSafeProgressMonitor pm = new ThreadSafeProgressMonitor(mock);

		runOnThread(new Runnable() {
			@Override
			public void run() {
				try {
					pm.start(1);
					fail("start did not fail on background thread");
				} catch (IllegalStateException notMainThread) {
				}

				try {
					pm.beginTask("title"
					fail("beginTask did not fail on background thread");
				} catch (IllegalStateException notMainThread) {
				}

				try {
					pm.endTask();
					fail("endTask did not fail on background thread");
				} catch (IllegalStateException notMainThread) {
				}
			}
		});

		assertNull(mock.taskTitle);
		assertEquals(0
	}

	@Test
	public void testMethodsOkOnMainThread() {
		final MockProgressMonitor mock = new MockProgressMonitor();
		final ThreadSafeProgressMonitor pm = new ThreadSafeProgressMonitor(mock);

		pm.start(1);
		assertEquals(1

		pm.beginTask("title"
		assertEquals("title"
		assertEquals(42

		pm.update(1);
		pm.pollForUpdates();
		assertEquals(43

		pm.update(2);
		pm.pollForUpdates();
		assertEquals(45

		pm.endTask();
		assertNull(mock.taskTitle);
		assertEquals(0
	}

	@Test
	public void testUpdateOnBackgroundThreads() throws InterruptedException {
		final MockProgressMonitor mock = new MockProgressMonitor();
		final ThreadSafeProgressMonitor pm = new ThreadSafeProgressMonitor(mock);

		pm.startWorker();

		final CountDownLatch doUpdate = new CountDownLatch(1);
		final CountDownLatch didUpdate = new CountDownLatch(1);
		final CountDownLatch doEndWorker = new CountDownLatch(1);

		final Thread bg = new Thread() {
			@Override
			public void run() {
				assertFalse(pm.isCancelled());

				await(doUpdate);
				pm.update(2);
				didUpdate.countDown();

				await(doEndWorker);
				pm.update(1);
				pm.endWorker();
			}
		};
		bg.start();

		pm.pollForUpdates();
		assertEquals(0
		doUpdate.countDown();

		await(didUpdate);
		pm.pollForUpdates();
		assertEquals(2

		doEndWorker.countDown();
		pm.waitForCompletion();
		assertEquals(3
	}

	private static void await(CountDownLatch cdl) {
		try {
			assertTrue("latch released"
		} catch (InterruptedException ie) {
			fail("Did not expect to be interrupted");
		}
	}

	private static void runOnThread(Runnable task) throws InterruptedException {
		Thread t = new Thread(task);
		t.start();
		t.join(1000);
		assertFalse("thread has stopped"
	}

	private static class MockProgressMonitor implements ProgressMonitor {
		String taskTitle;

		int value;

		@Override
		public void update(int completed) {
			value += completed;
		}

		@Override
		public void start(int totalTasks) {
			value = totalTasks;
		}

		@Override
		public void beginTask(String title
			taskTitle = title;
			value = totalWork;
		}

		@Override
		public void endTask() {
			taskTitle = null;
			value = 0;
		}

		@Override
		public boolean isCancelled() {
			return false;
		}
	}
}
