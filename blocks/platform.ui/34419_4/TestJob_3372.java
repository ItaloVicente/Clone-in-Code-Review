package org.eclipse.ui.examples.jobs;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.progress.IProgressConstants;

public class TestJob extends Job {
	public static final Object FAMILY_TEST_JOB = new Object();
	private long duration;
	private boolean failure;
	private boolean unknown;
	private boolean reschedule;
	private long rescheduleWait;

	public TestJob(long duration, boolean lock, boolean failure,
			boolean indeterminate, boolean reschedule, long rescheduleWait) {
		super("Test job"); //$NON-NLS-1$
		this.duration = duration;
		this.failure = failure;
		this.unknown = indeterminate;
		this.reschedule = reschedule;
		this.rescheduleWait = rescheduleWait;
		setProperty(IProgressConstants.ICON_PROPERTY, ProgressExamplesPlugin
				.imageDescriptorFromPlugin(ProgressExamplesPlugin.ID,
						"icons/sample.gif")); //$NON-NLS-1$
		if (lock)
			setRule(ResourcesPlugin.getWorkspace().getRoot());
	}

	public boolean belongsTo(Object family) {
		if (family instanceof TestJob) {
			return true;
		}
		return family == FAMILY_TEST_JOB;
	}

	public IStatus run(IProgressMonitor monitor) {
		if (failure) {
			MultiStatus result = new MultiStatus(
					"org.eclipse.ui.examples.jobs", 1, "This is the MultiStatus message", new RuntimeException("This is the MultiStatus exception")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			result
					.add(new Status(
							IStatus.ERROR,
							"org.eclipse.ui.examples.jobs", 1, "This is the child status message", new RuntimeException("This is the child exception"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return result;
		}
		final long sleep = 10;
		int ticks = (int) (duration / sleep);
		if (this.unknown)
			monitor.beginTask(toString(), IProgressMonitor.UNKNOWN);
		else
			monitor.beginTask(toString(), ticks);
		try {
			for (int i = 0; i < ticks; i++) {
				if (monitor.isCanceled())
					return Status.CANCEL_STATUS;
				monitor.subTask("Processing tick #" + i); //$NON-NLS-1$
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				}
				monitor.worked(1);
			}
		} finally {
			if (reschedule)
				schedule(rescheduleWait);
			monitor.done();
		}
		return Status.OK_STATUS;
	}
}
