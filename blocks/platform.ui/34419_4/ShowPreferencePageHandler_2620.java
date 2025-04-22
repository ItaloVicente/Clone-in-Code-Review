
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;

public class ShowKeyAssistHandler extends WorkbenchWindowHandlerDelegate {

	@Override
	public Object execute(final ExecutionEvent event) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IBindingService bindingService = workbench.getService(IBindingService.class);
		bindingService.openKeyAssistDialog();
		return null;
	}
}
