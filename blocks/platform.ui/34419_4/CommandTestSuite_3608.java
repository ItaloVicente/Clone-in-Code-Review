
package org.eclipse.ui.forms.examples.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.*;

public class ShowHelpAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		try {
			window.getActivePage().showView("org.eclipse.ui.forms.examples.helpView");
		}
		catch (PartInitException e) {
			System.out.println(e);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
