
package org.eclipse.ui.internal.registry;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;

public final class ShowViewHandler extends AbstractHandler {
	
	private final String viewId;

	public ShowViewHandler(final String viewId) {
		this.viewId = viewId;
	}

	@Override
	public final Object execute(final ExecutionEvent event) 
			throws ExecutionException {
		final IWorkbenchWindow activeWorkbenchWindow = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);

		final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null) {
			return null;
		}

		try {
			activePage.showView(viewId);
		} catch (PartInitException e) {
			IStatus status = StatusUtil
					.newStatus(e.getStatus(), e.getMessage());
			StatusManager.getManager().handle(status, StatusManager.SHOW);
		}

		return null;
	}
}
