
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.handlers.HandlerUtil;

public final class ShowPreferencePageHandler extends AbstractHandler {


	public ShowPreferencePageHandler() {
		super();
	}

	@Override
	public final Object execute(final ExecutionEvent event) {
		final String preferencePageId = event
				.getParameter(IWorkbenchCommandConstants.WINDOW_PREFERENCES_PARM_PAGEID);
		final IWorkbenchWindow activeWorkbenchWindow = HandlerUtil
				.getActiveWorkbenchWindow(event);

		final Shell shell;
		if (activeWorkbenchWindow == null) {
			shell = null;
		} else {
			shell = activeWorkbenchWindow.getShell();
		}

		final PreferenceDialog dialog = PreferencesUtil
				.createPreferenceDialogOn(shell, preferencePageId, null, null);
		dialog.open();

		return null;
	}

}
