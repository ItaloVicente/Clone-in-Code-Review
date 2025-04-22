package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;

public class RefreshCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		getView(event).refresh();
		return null;
	}
}
