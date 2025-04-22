
package org.eclipse.ui.examples.contributions.view;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.examples.contributions.ContributionMessages;
import org.eclipse.ui.examples.contributions.model.Person;
import org.eclipse.ui.examples.contributions.model.PersonInput;
import org.eclipse.ui.handlers.HandlerUtil;

public class EditInfoHandler extends AbstractHandler {
	
	public static final String ID = "org.eclipse.ui.examples.contributions.view.edit"; //$NON-NLS-1$

	private static final String EDITOR_ID = "org.eclipse.ui.examples.contributions.editor"; //$NON-NLS-1$

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		if (window == null) {
			return null;
		}
		ISelection sel = HandlerUtil.getActiveMenuSelection(event);
		if (sel == null) {
			sel = HandlerUtil.getCurrentSelection(event);
		}
		if (sel instanceof IStructuredSelection && !sel.isEmpty()) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			Person person = (Person) selection.getFirstElement();
			PersonInput input = new PersonInput(person.getId());
			try {
				window.getActivePage().openEditor(input, EDITOR_ID);
			} catch (PartInitException e) {
				throw new ExecutionException(
						ContributionMessages.EditInfoHandler_failed_to_open, e);
			}
		}
		return null;
	}

}
