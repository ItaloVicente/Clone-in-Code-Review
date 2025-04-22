package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class TestActionDependent extends Action implements IAction {
 
	private Shell shell;

	public TestActionDependent(Shell aShell, String isValid) {
		super("Dependent Action: " + isValid);
		shell = aShell;
		
	}
	
	public void run() {
		MessageDialog.openInformation(shell, "TestActionDependent", "The dependent action ran!");
	}
}
