package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.RepositoriesView;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractToggleCommand
		extends RepositoriesViewCommandHandler<RepositoryTreeNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		HandlerUtil.toggleCommandState(event.getCommand());
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IViewPart part = page.findView(RepositoriesView.VIEW_ID);
				if (part instanceof RepositoriesView) {
					(((RepositoriesView) part).getCommonViewer()).refresh();
				}
			}
		}
		return null;
	}

}
