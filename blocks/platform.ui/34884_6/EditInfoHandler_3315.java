
package org.eclipse.ui.examples.contributions.view;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.examples.contributions.model.IPersonService;
import org.eclipse.ui.examples.contributions.model.Person;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IEvaluationService;

public class AdminHandler extends AbstractHandler implements IElementUpdater {
	private static final String ID = "org.eclipse.ui.examples.contributions.view.adminRights"; //$NON-NLS-1$

	public void updateElement(UIElement element, Map parameters) {
		IEvaluationService service = (IEvaluationService) PlatformUI
				.getWorkbench().getService(IEvaluationService.class);
		if (service == null) {
			return;
		}
		ISelection sel = (ISelection) service.getCurrentState().getVariable(
				ISources.ACTIVE_MENU_SELECTION_NAME);
		if (!(sel instanceof IStructuredSelection)) {
			return;
		}
		IStructuredSelection ssel = (IStructuredSelection) sel;
		if (ssel.isEmpty()) {
			return;
		}
		Object o = ssel.getFirstElement();
		if (!(o instanceof Person)) {
			return;
		}
		element.setChecked(((Person) o).hasAdminRights());
	}

	public Object execute(ExecutionEvent event) {
		ISelection sel = HandlerUtil.getActiveMenuSelection(event);
		if (!(sel instanceof IStructuredSelection)) {
			return null;
		}
		IStructuredSelection ssel = (IStructuredSelection) sel;
		if (ssel.isEmpty()) {
			return null;
		}
		Object o = ssel.getFirstElement();
		if (!(o instanceof Person)) {
			return null;
		}
		Person p = (Person) o;
		p.setAdminRights(!p.hasAdminRights());
		IPersonService service = (IPersonService) PlatformUI.getWorkbench()
				.getService(IPersonService.class);
		service.updatePerson(p);
		ICommandService commands = (ICommandService) PlatformUI.getWorkbench()
				.getService(ICommandService.class);
		commands.refreshElements(ID, null);
		return null;
	}
}
