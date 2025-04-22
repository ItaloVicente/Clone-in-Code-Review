package org.eclipse.egit.ui.internal.rebase;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.core.op.RebaseOperation;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.api.RebaseCommand.Operation;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class RebaseHelper {

	public static Object basicExecute(final Repository repository, String jobname,
			RevCommit commit) {
		final RebaseOperation rebase = new RebaseOperation(repository, commit);
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					rebase.execute(monitor);
				} catch (final CoreException e) {
					try {
						new RebaseOperation(repository, Operation.ABORT)
								.execute(monitor);
					} catch (CoreException e1) {
						return e1.getStatus();
					}
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(rebase.getSchedulingRule());
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent cevent) {
				IStatus result = cevent.getJob().getResult();
				if (result.getSeverity() == IStatus.CANCEL) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Shell shell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							MessageDialog
									.openInformation(
											shell,
											UIText.RebaseCurrentRefCommand_RebaseCanceledTitle,
											UIText.RebaseCurrentRefCommand_RebaseCanceledMessage);
						}
					});
				} else if (result.isOK()) {
					RebaseResultDialog.show(rebase.getResult(), repository);
				}
			}
		});
		job.schedule();
		return null;
	}
}
