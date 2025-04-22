package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.blame.BlameOperation;
import org.eclipse.egit.ui.internal.job.JobUtil;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.handlers.HandlerUtil;

public class ShowBlameActionHandler extends RepositoryActionHandler {

	public Object execute(final ExecutionEvent event) throws ExecutionException {
		IResource[] selected = getSelectedResources();
		Repository repository = getRepository();
		if (repository != null && selected.length == 1
				&& selected[0] instanceof IFile)
			JobUtil.scheduleUserJob(new BlameOperation(repository,
					(IFile) selected[0], HandlerUtil.getActiveShell(event),
					HandlerUtil.getActiveSite(event).getPage()),
					UIText.ShowBlameHandler_JobName, null);
		return null;
	}
}
