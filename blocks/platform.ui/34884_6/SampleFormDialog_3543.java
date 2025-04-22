package org.eclipse.ui.forms.examples.internal.dialogs;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenFormDialog extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		SampleFormDialog dialog = new SampleFormDialog(window.getShell());
		dialog.create();
		dialog.getShell().setText("Sample Form Dialog");
		dialog.getShell().setSize(500,500);
		dialog.getShell().setLocation(100, 300);
		dialog.open();
		return null;
	}
}
