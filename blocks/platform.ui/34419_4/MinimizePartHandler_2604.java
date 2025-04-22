
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class MaximizePartHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
		if (activeWorkbenchWindow != null) {
			IWorkbenchPage page = activeWorkbenchWindow.getActivePage();
			if (page != null) {
				IWorkbenchPartReference partRef = page.getActivePartReference();
				if (partRef != null) {
					page.toggleZoom(partRef);
				}
			}
		}

		return null;
	}

}
