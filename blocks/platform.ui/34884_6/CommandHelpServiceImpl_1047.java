
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;

public abstract class WorkbenchWindowHandlerDelegate extends
		ExecutableExtensionHandler implements IWorkbenchWindowHandlerDelegate {

	@Override
	public void init(final IWorkbenchWindow window) {
	}

	@Override
	public void run(final IAction action) {
		try {
			execute(new ExecutionEvent());
		} catch (final ExecutionException e) {
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
