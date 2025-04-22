package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.core.op.PullOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.pull.PullResultDialog;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryNode;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class PullCommand extends RepositoriesViewCommandHandler<RepositoryNode> {
	public Object execute(ExecutionEvent event) throws ExecutionException {

		final Repository repository = getSelectedNodes(event).get(0)
				.getRepository();
		if (repository == null)
			return null;

		String branchName;
		try {
			branchName = repository.getBranch();
		} catch (IOException e) {
			throw new ExecutionException(e.getMessage(), e);
		}

		String jobname = NLS.bind(
				UIText.PullCurrentBranchActionHandler_PullJobname, branchName);
		int timeout = Activator.getDefault().getPreferenceStore().getInt(
				UIPreferences.REMOTE_CONNECTION_TIMEOUT);
		final PullOperation pull = new PullOperation(repository, timeout);
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					pull.execute(monitor);
				} catch (final CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.setRule(pull.getSchedulingRule());
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
											UIText.PullCurrentBranchActionHandler_PullCanceledTitle,
											UIText.PullCurrentBranchActionHandler_PullCanceledMessage);
						}
					});
				} else if (result.isOK()) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Shell shell = PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell();
							new PullResultDialog(shell, repository, pull
									.getResult()).open();
						}
					});
				}
			}
		});
		job.schedule();
		return null;
	}
}
