package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.dialogs.CompareTreeView;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

public class CompareInTreeCommand extends CompareCommand {

	@Override
	protected void compare(IWorkbenchPage page, Repository repo,
			String compareCommit, String baseCommit) throws ExecutionException {
		try {
			CompareTreeView view = (CompareTreeView) page
					.showView(CompareTreeView.ID);
			view.setInput(repo, compareCommit, baseCommit);
		} catch (PartInitException e) {
			throw new ExecutionException(e.getLocalizedMessage(), e);
		}
	}
}
