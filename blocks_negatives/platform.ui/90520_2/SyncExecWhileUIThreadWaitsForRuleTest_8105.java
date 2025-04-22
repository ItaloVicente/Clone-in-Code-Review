/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.tests.concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.WorkbenchPlugin;

import junit.framework.TestCase;

/**
 * This tests the simple traditional deadlock of a thread holding a lock trying
 * to perform a syncExec, while the UI thread is waiting for that lock.
 * UISynchronizer and UILockListener conspire to prevent deadlock in this case.
 */
public class SyncExecWhileUIThreadWaitsForLock extends TestCase {

	private List<IStatus> reportedErrors;
	private ILogListener listener;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		reportedErrors = new ArrayList<>();
		listener = new ILogListener() {

			@Override
			public void logging(IStatus status, String plugin) {
				reportedErrors.add(status);
			}
		};
		WorkbenchPlugin.getDefault().getLog().addLogListener(listener);
	}

	@Override
	protected void tearDown() throws Exception {
		if (listener != null) {
			WorkbenchPlugin.getDefault().getLog().removeLogListener(listener);
		}
		super.tearDown();
	}

	public void testDeadlock() {
		if (Thread.interrupted()) {
			fail("Thread was interrupted at start of test");
		}
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

		assertEquals("Unexpected error count reported: " + reportedErrors, 1, reportedErrors.size());
		MultiStatus status = (MultiStatus) reportedErrors.get(0);
		assertEquals("Unexpected child status count reported: " + Arrays.toString(status.getChildren()), 2,
				status.getChildren().length);
		if (Thread.interrupted()) {
		}
	}
}
