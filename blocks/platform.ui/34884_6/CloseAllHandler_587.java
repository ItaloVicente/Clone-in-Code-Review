package org.eclipse.ui.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.PerspectiveMenu;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.statushandlers.StatusManager;

public class ChangeToPerspectiveMenu extends PerspectiveMenu {

    public ChangeToPerspectiveMenu(IWorkbenchWindow window, String id) {
        super(window, id);
		if (window instanceof WorkbenchWindow) {
			((WorkbenchWindow) window)
					.addSubmenu(WorkbenchWindow.OPEN_PERSPECTIVE_SUBMENU);
		}
        showActive(true);
    }

    @Override
	protected void run(IPerspectiveDescriptor desc) {
		IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
		int mode = store.getInt(IPreferenceConstants.OPEN_PERSP_MODE);
		IWorkbenchPage page = getWindow().getActivePage();
		IPerspectiveDescriptor persp = null;
		if (page != null) {
			persp = page.getPerspective();
		}

		IHandlerService handlerService = getWindow()
				.getService(IHandlerService.class);
		ICommandService commandService = getWindow()
				.getService(ICommandService.class);

		Command command = commandService
				.getCommand(IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE);
		Map parameters = new HashMap();
		parameters
				.put(
						IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE_PARM_ID,
						desc.getId());

		if (IPreferenceConstants.OPM_NEW_WINDOW == mode && persp != null) {

			parameters
					.put(
							"org.eclipse.ui.perspectives.showPerspective.newWindow", "true"); //$NON-NLS-1$//$NON-NLS-2$
		}
		
		ParameterizedCommand pCommand = ParameterizedCommand.generateCommand(
				command, parameters);
		try {
			handlerService.executeCommand(pCommand, null);
		} catch (ExecutionException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		} catch (NotDefinedException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		} catch (NotEnabledException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		} catch (NotHandledException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		}
	}
}
