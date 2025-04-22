
package org.eclipse.jgit.lib;

import junit.framework.TestCase;

public class ThreadSafeProgressMonitorTest extends TestCase {
	public void testFailsMethodsOnBackgroundThread()
			throws InterruptedException {
		final MockProgressMonitor mock = new MockProgressMonitor();
		final ThreadSafeProgressMonitor pm = new ThreadSafeProgressMonitor(mock);

		runOnThread(new Runnable() {
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

	public void testMethodsOkOnMainThread() {
		final MockProgressMonitor mock = new MockProgressMonitor();
		final ThreadSafeProgressMonitor pm = new ThreadSafeProgressMonitor(mock);

		pm.start(1);
		assertEquals(1

		pm.beginTask("title"
		assertEquals("title"
		assertEquals(42

		pm.update(1);
		assertEquals(43

		pm.update(2);
		assertEquals(45

		pm.endTask();
		assertNull(mock.taskTitle);
		assertEquals(0
	}

	public void testUpdateOnBackgroundThreads() throws InterruptedException {
		final MockProgressMonitor mock = new MockProgressMonitor();
		final ThreadSafeProgressMonitor pm = new ThreadSafeProgressMonitor(mock);

		pm.startWorker();
		runOnThread(new Runnable() {
			public void run() {
				assertFalse(pm.isCancelled());
				pm.update(2);
				pm.endWorker();
			}
		});
		pm.waitForCompletion();
		assertEquals(2
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

		public void update(int completed) {
			value += completed;
		}

		public void start(int totalTasks) {
			value = totalTasks;
		}

		public void beginTask(String title
			taskTitle = title;
			value = totalWork;
		}

		public void endTask() {
			taskTitle = null;
			value = 0;
		}

		public boolean isCancelled() {
			return false;
		}
	}
}
