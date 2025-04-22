package org.eclipse.jface.tests.dialogs;

import junit.framework.TestCase;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.jface.util.SafeRunnable;

public class SafeRunnableErrorTest extends TestCase {

	int count;

	protected Thread runner() {
		return new Thread(new Runnable() {

			@Override
			public void run() {
				ISafeRunnable runnable = new SafeRunnable() {
					@Override
					public void run() throws Exception {
						throw new RuntimeException("test exception " + ++count);
					}
				};
				SafeRunnable.run(runnable);

			}
		});
	}

	public void testSafeRunnableHandler() {
		SafeRunnable.run(new SafeRunnable() {
			@Override
			public void run() throws Exception {
				throw new RuntimeException("test exception");
			}
		});
	}

	public void testSafeRunnableHandlerOtherThread() throws Exception {
		Thread t = runner();
		t.run();
		t.join();
	}

	public void testSafeRunnableHandlerMulti() {
		ISafeRunnable runnable = new SafeRunnable() {
			@Override
			public void run() throws Exception {
				throw new RuntimeException("test exception " + ++count);
			}
		};

		int expectedRuns = 3;
		for (int run = 0; run < expectedRuns; run++) {
			SafeRunnable.run(runnable);
		}
		assertEquals(expectedRuns, count);
	}

}
