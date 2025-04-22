package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class OpenPropertiesCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(
							IPageLayout.ID_PROP_SHEET);
		} catch (PartInitException e1) {
		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

}
