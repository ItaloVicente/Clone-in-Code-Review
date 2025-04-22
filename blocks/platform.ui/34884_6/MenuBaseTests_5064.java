
package org.eclipse.ui.tests.menus;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

public class HelloUpdateHandler extends AbstractHandler implements
		IElementUpdater {

	private String myLabelState = null;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);

		MessageDialog.openInformation(window.getShell(), "Hello",
				"Hello label update command!");
		myLabelState = "My New Item";
		ICommandService cs = window
				.getService(ICommandService.class);
		cs.refreshElements(event.getCommand().getId(), null);
		return null;
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		if (myLabelState == null) {
			return;
		}
		element.setText(myLabelState);
	}
}
