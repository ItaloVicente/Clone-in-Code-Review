package org.eclipse.e4.demo.contacts.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class FragmentHandler {

	@Execute 
	public void run(Shell shell){
		MessageDialog.openInformation(shell, "Hello", "See us at #eclipse-e4 !");
	}
}
