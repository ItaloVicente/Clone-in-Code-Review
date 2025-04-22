package org.eclipse.ui.tests.concurrency;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.IThreadListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class TransferRuleTest extends UITestCase {
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

	class TestRunnable implements IRunnableWithProgress, IThreadListener {
		Throwable error;
		private final ISchedulingRule rule;

		public TestRunnable(ISchedulingRule aRule) {
			this.rule = aRule;
		}

		@Override
		public void run(IProgressMonitor monitor) {
			if (error != null)
				return;
			try {
				try {
					Platform.getJobManager().beginRule(rule, monitor);
				} finally {
					Platform.getJobManager().endRule(rule);
				}
			} catch (Throwable t) {
				error = t;
			}
		}

		@Override
		public void threadChange(Thread thread) {
			try {
				Platform.getJobManager().transferRule(rule, thread);
			} catch (Throwable t) {
				error = t;
			}
		}
	}

	public TransferRuleTest(String name) {
		super(name);
	}

	public void testModalContextTransfer() {
		ISchedulingRule rule = new TestRule();
		TestRunnable runnable = new TestRunnable(rule);
		try {
			Platform.getJobManager().beginRule(rule, null);
			new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()).run(true, true, runnable);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail("1.0");
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("1.1");
		} finally {
			Platform.getJobManager().endRule(rule);
		}
		if (runnable.error != null) {
			runnable.error.printStackTrace();
			fail("1.2");
		}
	}
}
