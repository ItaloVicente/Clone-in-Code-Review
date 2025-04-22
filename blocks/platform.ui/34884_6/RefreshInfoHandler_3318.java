
package org.eclipse.ui.examples.contributions.view;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.examples.contributions.model.IPersonService;
import org.eclipse.ui.examples.contributions.model.Person;
import org.eclipse.ui.handlers.HandlerUtil;

public class LoginHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);
		ISelection sel = HandlerUtil.getActiveMenuSelection(event);
		if (sel == null) {
			sel = HandlerUtil.getCurrentSelection(event);
		}
		if (sel instanceof IStructuredSelection && !sel.isEmpty()) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			Person person = (Person) selection.getFirstElement();
			IPersonService service = (IPersonService) part.getSite()
					.getService(IPersonService.class);
			service.login(person);
		}
		return null;
	}

}
