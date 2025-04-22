
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class ClosePartHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		if (part instanceof IEditorPart) {
			window.getActivePage().closeEditor((IEditorPart) part, true);
		} else if (part instanceof IViewPart) {
			window.getActivePage().hideView((IViewPart) part);
		}

		return null;
	}
}
