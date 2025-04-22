package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.team.ui.history.IHistoryView;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ShowHistoryActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IHistoryView view;
		try {
			view = (IHistoryView) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().showView(
							IHistoryView.VIEW_ID);
			view.showHistoryFor(getSelection(event).getFirstElement());
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
