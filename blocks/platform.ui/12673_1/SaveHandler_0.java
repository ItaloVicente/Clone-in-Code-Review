
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPage;

public class SaveAllHandler extends SaveHandler {
	public Object execute(ExecutionEvent event) {
		IWorkbenchPart activePart = getActivePart();
		WorkbenchPage workbenchPage;
		
		if (activePart != null) {
			workbenchPage = (WorkbenchPage) activePart.getSite().getPage();
		} else {
			workbenchPage = (WorkbenchPage) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
		}
		workbenchPage.saveAllEditors(false);
		return null;
	}
}
