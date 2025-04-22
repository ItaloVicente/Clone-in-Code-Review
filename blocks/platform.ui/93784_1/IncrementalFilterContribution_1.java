package org.eclipse.ui.internal.navigator.filters.incremental;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class CancelIncrementalResourceFilterHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) {
		IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		if (activePart instanceof IViewPart) {
			IViewSite viewSite = ((IViewPart) activePart).getViewSite();
			IStatusLineManager statusLineManager = viewSite.getActionBars().getStatusLineManager();
			IncrementalFilterContribution.remove(statusLineManager);
		}

		return null;
	}
}
