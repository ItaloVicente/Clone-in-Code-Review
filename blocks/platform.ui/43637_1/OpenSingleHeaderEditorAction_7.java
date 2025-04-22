package org.eclipse.ui.forms.examples.internal.rcp;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.forms.examples.internal.OpenFormEditorAction;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenSimpleFormEditorAction extends OpenFormEditorAction {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		return openEditor(new SimpleFormEditorInput("Simple Editor"), "org.eclipse.ui.forms.examples.base-editor", window);
	}
}
