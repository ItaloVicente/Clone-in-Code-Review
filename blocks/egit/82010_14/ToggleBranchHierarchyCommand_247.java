package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.RepositoriesView;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ToggleBranchCommitCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {

	public static final String ID = "org.eclipse.egit.ui.RepositoriesToggleBranchCommit"; //$NON-NLS-1$

	public static final String TOGGLE_STATE = "org.eclipse.ui.commands.toggleState"; //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		HandlerUtil.toggleCommandState(event.getCommand());
		IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);
		if (part instanceof RepositoriesView)
			(((RepositoriesView) part).getCommonViewer()).refresh();
		return null;
	}

}
