
package org.eclipse.ui.internal.handlers;

import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.menus.UIElement;

public class ToggleCoolbarHandler extends AbstractHandler implements
		IElementUpdater {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow activeWorkbenchWindow = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		if (activeWorkbenchWindow instanceof WorkbenchWindow) {
			WorkbenchWindow window = (WorkbenchWindow) activeWorkbenchWindow;
			window.toggleToolbarVisibility();
		}

		return null;
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		IWorkbenchLocationService wls = element
				.getServiceLocator()
				.getService(IWorkbenchLocationService.class);
		IWorkbenchWindow window = wls.getWorkbenchWindow();
		if (window == null || !(window instanceof WorkbenchWindow))
			return;
		element
				.setText(isCoolbarVisible((WorkbenchWindow) window) ? WorkbenchMessages.ToggleCoolbarVisibilityAction_hide_text
						: WorkbenchMessages.ToggleCoolbarVisibilityAction_show_text);
	}

	private boolean isCoolbarVisible(WorkbenchWindow window) {
		return window.getCoolBarVisible() || window.getPerspectiveBarVisible();
	}
}
