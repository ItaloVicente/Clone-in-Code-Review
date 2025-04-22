package org.eclipse.ui.examples.urischemehandler.uriHandlers;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.urischeme.IUriSchemeHandler;

public class Hello1SchemeHandler implements IUriSchemeHandler {

	@Override
	public void handle(String uri) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		MessageDialog.openInformation(
				window.getShell(),
				"Handler for 'hello1' URI scheme", //$NON-NLS-1$
				"Hello, Eclipse world!\nReceived URL: " + uri); //$NON-NLS-1$
	}
}
