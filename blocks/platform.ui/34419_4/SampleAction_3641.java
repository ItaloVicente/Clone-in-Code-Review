package org.eclipse.e4.ui.menu.tests.debug.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	public SampleAction() {
	}

	public void run(IAction action) {
		MessageDialog.openInformation(
			window.getShell(),
			"Debug actionset test plugin",
			"Hello, Eclipse world");
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}
