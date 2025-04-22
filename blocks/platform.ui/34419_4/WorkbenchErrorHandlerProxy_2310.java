
package org.eclipse.ui.internal;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.dialogs.WorkbenchEditorsDialog;

public class WorkbenchEditorsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow workbenchWindow = HandlerUtil
				.getActiveWorkbenchWindow(event);
		if (workbenchWindow == null) {
			return null;
		}
		IWorkbenchPage page = workbenchWindow.getActivePage();
		if (page != null) {
			new WorkbenchEditorsDialog(workbenchWindow).open();
		}
		return null;
	}

}
