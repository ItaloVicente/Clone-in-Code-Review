package org.eclipse.egit.core.internal.job;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.op.IEGitOperation;

public class JobUtil {

	public static void scheduleUserJob(final IEGitOperation op, String jobName,
			final Object jobFamily) {
		scheduleUserJob(op, jobName, jobFamily, null);
	}

	public static void scheduleUserJob(final IEGitOperation op, String jobName,
			final Object jobFamily, IJobChangeListener jobChangeListener) {
		Job job = new Job(jobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
				} catch (CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (jobFamily != null && family.equals(jobFamily))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setRule(op.getSchedulingRule());
		job.setUser(true);
		if (jobChangeListener != null)
			job.addJobChangeListener(jobChangeListener);
		job.schedule();
	}
}
