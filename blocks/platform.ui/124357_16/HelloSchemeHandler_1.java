package org.eclipse.ui.examples.urischemehandler.uriHandlers;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.urischeme.IUriSchemeHandler;

public class HelloSchemeHandler implements IUriSchemeHandler {

	@Override
	public void handle(String uri) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		MessageDialog.openInformation(
				window.getShell(),
				"UriSchemeHandler", //$NON-NLS-1$
				"Hello, Eclipse world! Received URL: " + uri); //$NON-NLS-1$
	}
}
