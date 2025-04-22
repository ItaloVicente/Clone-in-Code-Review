package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchWindow;

public class LockToolBarHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		WorkbenchWindow workbenchWindow = (WorkbenchWindow) HandlerUtil
				.getActiveWorkbenchWindow(event);
		if (workbenchWindow != null) {
			ICoolBarManager coolBarManager = workbenchWindow.getCoolBarManager2();
			if (coolBarManager != null) {
				boolean oldValue = HandlerUtil.toggleCommandState(event.getCommand());
				coolBarManager.setLockLayout(!oldValue);
			}
		}
		return null;
	}

}
