
package org.eclipse.ui.internal.handlers;

import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.e4.compatibility.E4Util;

public class ClosePerspectiveHandler extends AbstractHandler {


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = HandlerUtil
				.getActiveWorkbenchWindow(event);
		if (activeWorkbenchWindow != null) {
			WorkbenchPage page = (WorkbenchPage) activeWorkbenchWindow
					.getActivePage();
			if (page != null) {
				Map parameters = event.getParameters();
				String value = (String) parameters
						.get(IWorkbenchCommandConstants.WINDOW_CLOSE_PERSPECTIVE_PARM_ID);
				if (value == null) {
					page.closePerspective(page.getPerspective(), true, true);
				} else {
					IPerspectiveDescriptor perspective = activeWorkbenchWindow
							.getWorkbench().getPerspectiveRegistry()
							.findPerspectiveWithId(value);
					if (perspective != null) {
						page.closePerspective(perspective, true, true);
					}
				}
			}
		}
		return null;
	}

	public static void closePerspective(WorkbenchPage page, Object persp) {
		E4Util.unsupported("Need a better way to close the perspective"); //$NON-NLS-1$
	}
}
