
package org.eclipse.ui.examples.contributions.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.examples.contributions.ContributionMessages;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

public class GlobalMenuHandler extends AbstractHandler {
	public GlobalMenuHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(),
				ContributionMessages.SampleHandler_plugin_name,
				ContributionMessages.SampleHandler_hello_msg);
		return null;
	}
}
