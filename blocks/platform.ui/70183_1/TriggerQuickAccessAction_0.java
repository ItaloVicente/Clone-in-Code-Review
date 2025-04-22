package org.eclipse.ui.internal.quickaccess;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.handlers.IHandlerService;
public class TriggerQuickAccessAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	@Override
	public void run(IAction action) {
		IHandlerService handlerService = window.getService(IHandlerService.class);
		try {
			handlerService.executeCommand("org.eclipse.ui.window.quickAccess", null); // //$NON-NLS-1$
		} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}
