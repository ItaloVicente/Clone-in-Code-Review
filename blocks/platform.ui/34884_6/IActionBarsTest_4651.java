package org.eclipse.ui.tests.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IParameter;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class GenericCommandActionDelegate implements
		IWorkbenchWindowActionDelegate, IViewActionDelegate,
		IEditorActionDelegate, IObjectActionDelegate, IExecutableExtension {

	public static final String PARM_COMMAND_ID = "commandId";

	private String commandId = null;

	private Map parameterMap = null;

	private ParameterizedCommand parameterizedCommand = null;

	private IHandlerService handlerService = null;

	@Override
	public void dispose() {
		handlerService = null;
		parameterizedCommand = null;
		parameterMap = null;
	}

	@Override
	public void run(IAction action) {
		if (handlerService == null) {
			return;
		}
		try {
			if (commandId != null) {
				handlerService.executeCommand(commandId, null);
			} else if (parameterizedCommand != null) {
				handlerService.executeCommand(parameterizedCommand, null);
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		String id = config.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		if (data instanceof String) {
			commandId = (String) data;
		} else if (data instanceof Map) {
			parameterMap = (Map) data;
			if (parameterMap.get(PARM_COMMAND_ID) == null) {
				Status status = new Status(IStatus.ERROR,
						"org.eclipse.ui.tests", "The '" + id
								+ "' action won't work without a commandId");
				throw new CoreException(status);
			}
		} else {
			Status status = new Status(
					IStatus.ERROR,
					"org.eclipse.ui.tests",
					"The '"
							+ id
							+ "' action won't work without some initialization parameters");
			throw new CoreException(status);
		}
	}

	private void createCommand(ICommandService commandService) {
		String id = (String) parameterMap.get(PARM_COMMAND_ID);
		if (id == null) {
			return;
		}
		if (parameterMap.size() == 1) {
			commandId = id;
			return;
		}
		try {
			Command cmd = commandService.getCommand(id);
			if (!cmd.isDefined()) {
				return;
			}
			ArrayList parameters = new ArrayList();
			Iterator i = parameterMap.keySet().iterator();
			while (i.hasNext()) {
				String parmName = (String) i.next();
				if (PARM_COMMAND_ID.equals(parmName)) {
					continue;
				}
				IParameter parm = cmd.getParameter(parmName);
				if (parm == null) {
					return;
				}
				parameters.add(new Parameterization(parm, (String) parameterMap
						.get(parmName)));
			}
			parameterizedCommand = new ParameterizedCommand(cmd,
					(Parameterization[]) parameters
							.toArray(new Parameterization[parameters.size()]));
		} catch (NotDefinedException e) {
		}
	}

	@Override
	public void init(IWorkbenchWindow window) {
		if (handlerService != null) {
			return;
		}

		handlerService = window
				.getService(IHandlerService.class);
		if (parameterMap != null) {
			ICommandService commandService = window
					.getService(ICommandService.class);
			createCommand(commandService);
		}
	}

	@Override
	public void init(IViewPart view) {
		init(view.getSite().getWorkbenchWindow());
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor != null) {
			init(targetEditor.getSite().getWorkbenchWindow());
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		if (targetPart != null) {
			init(targetPart.getSite().getWorkbenchWindow());
		}
	}
}
