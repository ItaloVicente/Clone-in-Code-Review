package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class TestAction extends Action implements IAction {

	private Shell shell;

	public TestAction(Shell aShell) {
		super("Test Action");
		shell = aShell;
	}

	public TestAction(Shell aShell, String label) {
		super(label);
		shell = aShell;
	}

	public void run() {
		MessageDialog.openInformation(shell, "Shell", "The " + getText()
				+ " ran!");
	}
}
