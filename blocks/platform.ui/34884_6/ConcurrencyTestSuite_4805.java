package org.eclipse.ui.tests.concurrency;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.tests.harness.TestBarrier;
import org.eclipse.swt.widgets.Display;

public class Bug_262032 extends TestCase {

	ISchedulingRule identityRule = new ISchedulingRule() {
		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return rule == this;
		}
		@Override
		public boolean contains(ISchedulingRule rule) {
			return rule == this;
		}
	};

	public static Test suite() {
		return new TestSuite(Bug_262032.class);
	}
	
	volatile boolean concurrentAccess = false;

	public void testBug262032() {
		final ILock lock = Job.getJobManager().newLock();
		final TestBarrier tb1 = new TestBarrier(-1);

		Job j = new Job ("Deadlocking normal Job") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				tb1.setStatus(TestBarrier.STATUS_WAIT_FOR_START);
				tb1.waitForStatus(TestBarrier.STATUS_RUNNING);
				lock.acquire();
				assertTrue(!concurrentAccess);
				lock.release();

				tb1.setStatus(TestBarrier.STATUS_WAIT_FOR_DONE);
				return Status.OK_STATUS;
			};
		};
		j.setRule(identityRule);
		j.schedule();

		tb1.waitForStatus(TestBarrier.STATUS_WAIT_FOR_START);

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				lock.acquire();
				concurrentAccess = true;
				tb1.setStatus(TestBarrier.STATUS_RUNNING);
				try {
				Thread.sleep(1000); } catch (InterruptedException e) {/*don't care*/}
				concurrentAccess = false;
				lock.release();
			}
		});

		Job.getJobManager().beginRule(identityRule, null);
		Job.getJobManager().endRule(identityRule);

		try {
			j.join();
			tb1.waitForStatus(TestBarrier.STATUS_WAIT_FOR_DONE);
			assertEquals(Status.OK_STATUS, j.getResult());
		} catch (InterruptedException e) {fail();}
	}

}
