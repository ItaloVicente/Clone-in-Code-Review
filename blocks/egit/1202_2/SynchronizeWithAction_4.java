package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.egit.ui.internal.repository.RepositoriesView;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ShowInContext;

public class ShowRepositoriesViewActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		RepositoriesView view;
		try {
			view = (RepositoriesView) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().showView(
							RepositoriesView.VIEW_ID);
			ShowInContext ctx = new ShowInContext(ResourcesPlugin.getWorkspace().getRoot(), getSelection(event));
			view.show(ctx);
		} catch (PartInitException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		ISelectionService srv = (ISelectionService) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getService(ISelectionService.class);
		if (srv.getSelection() instanceof StructuredSelection) {
			return ((StructuredSelection) srv.getSelection()).size() == 1;
		}
		return false;
	}
}
