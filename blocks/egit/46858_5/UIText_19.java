package org.eclipse.egit.gitflow.ui.internal;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.op.IEGitOperation;

public class JobUtil {
	public final static Object GITFLOW_FAMILY = new Object();

	public static void scheduleUserWorkspaceJob(final IEGitOperation op,
			String jobName, final Object jobFamily) {
		Job job = new WorkspaceJob(jobName) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
				} catch (CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (jobFamily != null && family.equals(jobFamily)) {
					return true;
				}
				return super.belongsTo(family);
			}
		};
		job.setRule(op.getSchedulingRule());
		job.setUser(true);
		job.schedule();
	}

}
