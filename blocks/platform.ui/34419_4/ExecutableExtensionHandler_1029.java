
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchPage;

public class EditActionSetsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil
				.getActiveWorkbenchWindow(event);

		if (activeWorkbenchWindow != null) {
			WorkbenchPage page = (WorkbenchPage) activeWorkbenchWindow
					.getActivePage();
			if (page != null) {
				page.editActionSets();
			}
		}
		return null;
	}
}
