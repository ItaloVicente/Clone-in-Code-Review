
package org.eclipse.ui.tests.concurrency;

import org.eclipse.swt.widgets.Display;

import org.eclipse.core.runtime.jobs.ILock;

import org.eclipse.core.runtime.jobs.Job;

import junit.framework.TestCase;

public class SyncExecWhileUIThreadWaitsForLock extends TestCase {
	public void testDeadlock() {
		final ILock lock = Job.getJobManager().newLock();
		final boolean[] blocked = new boolean[] {false};
		final boolean[] lockAcquired= new boolean[] {false};
		Thread locking = new Thread("SyncExecWhileUIThreadWaitsForLock") {
			@Override
			public void run() {
				try {
					lock.acquire();
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							blocked[0] = true;
							lock.acquire();
							lock.release();
							blocked[0] = false;
						}
					});
					while (!blocked[0]) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
					}
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							try {
								if (lock.acquire(60000)) {
									lockAcquired[0] = true;
									lock.release();
								}
							} catch (InterruptedException e) {
							}
						}
					});
				} finally {
					lock.release();
				}
			}
		};
		locking.start();
		long waitStart = System.currentTimeMillis();
		Display display = Display.getDefault();
		while (!lockAcquired[0]) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			if (System.currentTimeMillis()-waitStart > 60000) {
				assertTrue("Deadlock occurred", false);
			}
		}
	}
}
