/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.tests.concurrency;

import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;

import junit.framework.TestCase;

/**
 * This tests the simple traditional deadlock of a thread holding a scheduling rule trying
 * to perform a syncExec, while the UI thread is waiting for that scheduling rule.
 * UISynchronizer and UILockListener conspire to prevent deadlock in this case.
 */
public class SyncExecWhileUIThreadWaitsForRuleTest extends TestCase {
	class TestRule implements ISchedulingRule {
		@Override
		public boolean contains(ISchedulingRule rule) {
			return rule == this;
		}

		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return rule == this;
		}
	}

	public void testDeadlock() {
		final ISchedulingRule rule = new TestRule();
		final boolean[] blocked = new boolean[] {false};
		final boolean[] lockAcquired = new boolean[] {false};
		final SubMonitor beginRuleMonitor = SubMonitor.convert(null);
		Thread locking = new Thread("SyncExecWhileUIThreadWaitsForRuleTest") {
			@Override
			public void run() {
				try {
					Job.getJobManager().beginRule(rule, null);
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							blocked[0] = true;
							try {
								Job.getJobManager().beginRule(rule, beginRuleMonitor);
							} finally {
								Job.getJobManager().endRule(rule);
							}
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
							Job.getJobManager().beginRule(rule, null);
							lockAcquired[0] = true;
							Job.getJobManager().endRule(rule);
						}
					});
				} finally {
					Job.getJobManager().endRule(rule);
				}
			}
		};
		locking.start();
		final long waitStart = System.currentTimeMillis();
		Thread canceler = new Thread("Canceler") {
			@Override
			public void run() {
				while (true) {
					if (System.currentTimeMillis() - waitStart > 60000) {
						beginRuleMonitor.setCanceled(true);
						break;
					}
				}

			}
		};
		canceler.start();
		Display display = Display.getDefault();
		while (!lockAcquired[0]) {
			try {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			} catch (SWTException e) {
				fail("Deadlock occurred");
			}
		}
		assertFalse("deadlock occurred", beginRuleMonitor.isCanceled());
		if (Thread.interrupted()) {
		}
	}
}
