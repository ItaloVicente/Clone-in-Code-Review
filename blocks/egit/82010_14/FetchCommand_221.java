package org.eclipse.egit.ui.internal.repository.tree.command;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.op.DeleteTagOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.repository.tree.TagNode;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;

public class DeleteTagCommand extends RepositoriesViewCommandHandler<TagNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final List<TagNode> tags = getSelectedNodes(event);
		if (tags.isEmpty())
			return null;

		final AtomicBoolean confirmed = new AtomicBoolean();
		final Shell shell = getActiveShell(event);
		shell.getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				String message;
				if (tags.size() > 1)
					message = MessageFormat.format(
							UIText.DeleteTagCommand_messageConfirmMultipleTag,
							Integer.valueOf(tags.size()));
				else
					message = MessageFormat.format(
							UIText.DeleteTagCommand_messageConfirmSingleTag,
							Repository.shortenRefName(tags.get(0).getObject()
									.getName()));
				confirmed.set(MessageDialog.openConfirm(shell,
						UIText.DeleteTagCommand_titleConfirm, message));
			}
		});
		if (!confirmed.get())
			return null;

		Job job = new Job(UIText.DeleteTagCommand_taskName) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask(UIText.DeleteTagCommand_taskName, tags.size());
				for (TagNode tag : tags) {
					final Repository repo = tag.getRepository();
					final String tagName = tag.getObject().getName();
					final DeleteTagOperation op = new DeleteTagOperation(repo,
							tagName);
					monitor.subTask(tagName);
					try {
						op.execute(monitor);
					} catch (CoreException e) {
						Activator.logError(e.getLocalizedMessage(), e);
					}
					monitor.worked(1);
				}
				monitor.done();
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.TAG.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}
}
