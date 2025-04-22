
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;

public class OpenInNewWindowHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil
				.getActiveWorkbenchWindow(event);
		if (activeWorkbenchWindow == null) {
			return null;
		}
		try {
			String perspId = null;

			IWorkbenchPage page = activeWorkbenchWindow.getActivePage();
			IAdaptable pageInput = ((Workbench) activeWorkbenchWindow
					.getWorkbench()).getDefaultPageInput();
			if (page != null && page.getPerspective() != null) {
				perspId = page.getPerspective().getId();
				pageInput = page.getInput();
			} else {
				perspId = activeWorkbenchWindow.getWorkbench()
						.getPerspectiveRegistry().getDefaultPerspective();
			}
			
			activeWorkbenchWindow.getWorkbench().openWorkbenchWindow(perspId,
					pageInput);
		} catch (WorkbenchException e) {
			StatusUtil.handleStatus(e.getStatus(),
					WorkbenchMessages.OpenInNewWindowAction_errorTitle
							+ ": " + e.getMessage(), //$NON-NLS-1$
					StatusManager.SHOW);
		}
		return null;

	}

}
