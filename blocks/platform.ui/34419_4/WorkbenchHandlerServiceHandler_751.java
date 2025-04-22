
package org.eclipse.ui.internal;

import org.eclipse.ui.statushandlers.AbstractStatusHandler;
import org.eclipse.ui.statushandlers.StatusAdapter;

public class WorkbenchErrorHandlerProxy extends AbstractStatusHandler {

	@Override
	public void handle(final StatusAdapter statusAdapter, int style) {
		Workbench.getInstance().getAdvisor().getWorkbenchErrorHandler().handle(
				statusAdapter, style);
	}

	@Override
	public boolean supportsNotification(int type) {
		return Workbench.getInstance().getAdvisor().getWorkbenchErrorHandler()
				.supportsNotification(type);
	}
	
}
