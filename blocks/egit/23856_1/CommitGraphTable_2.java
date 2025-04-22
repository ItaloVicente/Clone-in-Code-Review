package org.eclipse.egit.ui.internal.commit.command;

import java.text.MessageFormat;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.op.EditCommitOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.handler.SelectionHandler;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class EditHandler extends SelectionHandler {

	public static final String ID = "org.eclipse.egit.ui.commit.Edit"; //$NON-NLS-1$

	public Object execute(ExecutionEvent event) throws ExecutionException {
		RevCommit commit = getSelectedItem(RevCommit.class, event);
		if (commit == null)
			return null;
		Repository repo = getSelectedItem(Repository.class, event);
		if (repo == null)
			return null;

		final EditCommitOperation op = new EditCommitOperation(repo, commit);

		Job job = new WorkspaceJob(MessageFormat.format(
				UIText.EditHandler_JobName,
				commit.name())) {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
				} catch (CoreException e) {
					Activator.logError(UIText.EditHandler_InternalError, e);
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.EDIT.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.setRule(op.getSchedulingRule());
		job.schedule();
		return null;
	}
}
