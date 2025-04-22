
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.dialogs.PropertyDialog;

public class PropertyDialogHandler extends AbstractHandler {

	private String initialPageId = null;
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PreferenceDialog dialog;
		Object element = null;
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
		Shell shell;
		
		if (currentSelection instanceof IStructuredSelection) {
			element = ((IStructuredSelection) currentSelection)
					.getFirstElement();
		} else {
			return null;
		}
		
		if (activeWorkbenchWindow != null){
			shell = activeWorkbenchWindow.getShell();
			dialog = PropertyDialog.createDialogOn(shell,
					initialPageId, element);
			if (dialog != null) {
				dialog.open();
			}
		}
		return null;
	}

}
