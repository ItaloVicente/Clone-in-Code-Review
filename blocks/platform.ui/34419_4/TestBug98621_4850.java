package org.eclipse.ui.tests.concurrency;

import java.lang.reflect.InvocationTargetException;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.tests.harness.TestBarrier;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.progress.UIJob;

public class TestBug269121 extends TestCase {
	public static Test suite() {
		return new TestSuite(TestBug269121.class);
	}

	public void testBug() throws InterruptedException,
			InvocationTargetException {
		Job job = new UIJob("UI job") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				return Status.OK_STATUS;
			};
		};
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		final int[] status = new int[] { -1 };
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor monitor) {
				status[0] = TestBarrier.STATUS_DONE;
			}
		};
		final ProgressMonitorDialog dialog = new ProgressMonitorDialog(
				new Shell());
		Job statusJob = new Job("Checking for deadlock") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					TestBarrier.waitForStatus(status, TestBarrier.STATUS_DONE);
					return Status.OK_STATUS;
				} catch (AssertionFailedError e) {
					dialog.getShell().getDisplay().syncExec(new Runnable() {
						@Override
						public void run() {
							dialog.getProgressMonitor().setCanceled(true);
						}
					});
					return Status.CANCEL_STATUS;
				}
			}
		};
		job.schedule();
		statusJob.schedule();
		try {
			dialog.run(false, true, operation);
		} catch (InterruptedException e) {
		}
		statusJob.join();
		while (job.getResult() == null) {
			Display.getCurrent().readAndDispatch();
		}
		job.join();
		assertTrue("Deadlock occurred", statusJob.getResult().isOK());
	}
}
