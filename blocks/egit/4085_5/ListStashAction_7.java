package org.eclipse.egit.ui.internal.actions;

import java.util.regex.Pattern;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.core.op.CreateStashOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.internal.commit.CommitHelper;
import org.eclipse.egit.ui.internal.job.JobUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.Repository;

public class CreateStashActionHandler extends RepositoryActionHandler {
	private Repository repo;

	private String stashMessage;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IResource[] resources = getSelectedResources(event);
		repo = getRepository(true, event);

		if (repo == null)
			return null;
		if (resources.length == 0)
			return null;

		CommitHelper commitHelper = new CommitHelper(repo);
		Pattern p = Pattern.compile("[<>]+"); //$NON-NLS-1$
		Pattern x = Pattern.compile("[@]+"); //$NON-NLS-1$
		String[] author = p.split(commitHelper.getAuthor());
		String[] committer = p.split(commitHelper.getCommitter());
		String commitMessage = commitHelper.getCommitMessage();
		String[] newHead = x.split(CommitHelper.getHeadCommitInfo(repo).toString());

		System.out.println(commitHelper.getAuthor());
		System.out.println(commitHelper.getCommitter());
		System.out.println(commitHelper.getPreviousCommit());
		System.out.println(CommitHelper.getHeadCommitInfo(repo));

		CreateStashOperation op = new CreateStashOperation(resources, author, committer, commitMessage);

		JobUtil.scheduleUserJob(op, "Create Stash", //$NON-NLS-1$
				JobFamilies.CREATE_STASH);

		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
					IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(
					new org.eclipse.core.runtime.Status(
							IStatus.INFO, Activator.getPluginId(), IStatus.ERROR, e.getMessage(), e
					)
			);
		}

		stashMessage = "Saved working directory and index state WIP on master: " + newHead[1] + " test\nHEAD is now at " + newHead[1] + " test"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		String[] dialogButtons = {"OK"}; //$NON-NLS-1$
		MessageDialog dialog = new MessageDialog(
									getShell(event),
									"Stash Created", //$NON-NLS-1$
									null,
									stashMessage,
									MessageDialog.INFORMATION,
									dialogButtons,
									0
				);
		dialog.open();

		return null;
	}
}
