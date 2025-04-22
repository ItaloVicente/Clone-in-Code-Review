
package org.eclipse.ui.part;

import org.eclipse.jface.resource.ImageDescriptor;

import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.handlers.CyclePageHandler;

public abstract class PageSwitcher {

	public PageSwitcher(IWorkbenchPart part) {
		IHandlerService service = part.getSite().getService(
				IHandlerService.class);
		service.activateHandler(IWorkbenchCommandConstants.NAVIGATE_NEXT_PAGE, new CyclePageHandler(this));
		service.activateHandler(IWorkbenchCommandConstants.NAVIGATE_PREVIOUS_PAGE, new CyclePageHandler(
				this));
	}

	public abstract void activatePage(Object page);

	public abstract ImageDescriptor getImageDescriptor(Object page);

	public abstract String getName(Object page);

	public abstract Object[] getPages();

	public int getCurrentPageIndex() {
		return 0;
	}

}
