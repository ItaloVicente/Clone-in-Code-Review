package org.eclipse.ui.examples.fieldassist.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.examples.fieldassist.FieldAssistTestDialog;

public class TestDialogAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	public TestDialogAction() {
	}

	public void run(IAction action) {
		new FieldAssistTestDialog(window.getShell(), "tom").open();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}
