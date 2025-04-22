package org.eclipse.ui.internal.navigator.filters.incremental;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.navigator.CommonNavigator;

public class ActivateIncrementalResourceFilterHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) {
		IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		if (activePart instanceof CommonNavigator) {
			CommonNavigator commonNavigator = (CommonNavigator) activePart;
			IncrementalFilterContribution.addOrActivate(commonNavigator);
		}

		return null;
	}
}
