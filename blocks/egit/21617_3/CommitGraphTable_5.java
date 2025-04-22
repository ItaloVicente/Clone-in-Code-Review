package org.eclipse.egit.ui.internal.commit.command;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.CommitUtil;
import org.eclipse.egit.core.op.SquashCommitsOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.handler.SelectionHandler;
import org.eclipse.egit.ui.internal.rebase.CommitMessageEditorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.api.RebaseCommand.InteractiveHandler;
import org.eclipse.jgit.lib.RebaseTodoLine;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swt.widgets.Shell;

public class SquashHandler extends SelectionHandler {

	public static final String ID = "org.eclipse.egit.ui.commit.Squash"; //$NON-NLS-1$

	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<RevCommit> commits = getSelectedItems(RevCommit.class, event);
		if ((commits == null) || commits.isEmpty())
			return null;
		Repository repo = getSelectedItem(Repository.class, event);
		if (repo == null)
			return null;

		commits = CommitUtil.sortCommits(commits);

		final Shell shell = getPart(event).getSite().getShell();

		InteractiveHandler messageHandler = new InteractiveHandler() {
			public void prepareSteps(List<RebaseTodoLine> steps) {
			}

			public String modifyCommitMessage(String oldMessage) {
				return promptCommitMessage(shell, oldMessage);
			}
		};

		final SquashCommitsOperation op = new SquashCommitsOperation(repo,
				commits, messageHandler);
		Job job = new Job(MessageFormat.format(UIText.SquashHandler_JobName,
				Integer.valueOf(commits.size()))) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
				} catch (CoreException e) {
					Activator.logError(UIText.SquashHandler_InternalError, e);
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.SQUASH.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.setRule(op.getSchedulingRule());
		job.schedule();
		return null;
	}

	private String promptCommitMessage(final Shell shell, final String message) {
		final String[] msg = { message };
		shell.getDisplay().syncExec(new Runnable() {
			public void run() {
				CommitMessageEditorDialog dialog = new CommitMessageEditorDialog(
						shell, msg[0]);
				if (dialog.open() == Window.OK)
					msg[0] = dialog.getCommitMessage();
				else
					msg[0] = message;
			}
		});
		return msg[0];
	}
}
