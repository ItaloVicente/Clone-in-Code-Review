
package org.eclipse.ui.tests.concurrency;

import junit.framework.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.ui.progress.UIJob;

public class TestBug138695 extends TestCase {
	class SampleJob extends UIJob {

		public SampleJob() {
			super("Sample");
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Status.OK_STATUS;
		}

	}

	class SerialPerObjectRule implements ISchedulingRule {

		private Object fObject = null;

		public SerialPerObjectRule(Object lock) {
			fObject = lock;
		}

		@Override
		public boolean contains(ISchedulingRule rule) {
			return rule == this;
		}

		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			if (rule instanceof SerialPerObjectRule) {
				SerialPerObjectRule vup = (SerialPerObjectRule) rule;
				return fObject == vup.fObject;
			}
			return false;
		}

	}

	public static Test suite() {
		return new TestSuite(TestBug138695.class);
	}

	public void testManyThreads() {
		for (int i = 0; i < 1000; i++) {
			SampleJob job = new SampleJob();
			job.setRule(new SerialPerObjectRule(this));
			job.schedule();
		}

	}

}
