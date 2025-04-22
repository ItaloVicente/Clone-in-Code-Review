/*******************************************************************************
 * Copyright (c) 2008, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.contributions.rcp;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListenerWithChecks;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.examples.contributions.Activator;

/**
 * This workbench advisor creates the window advisor, and specifies the
 * perspective id for the initial window.
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {


	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	@Override
	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	@Override
	public void postStartup() {
		if (!Activator.DEBUG_COMMANDS) {
			return;
		}
		ICommandService service = getWorkbenchConfigurer()
				.getWorkbench().getService(ICommandService.class);
		service.addExecutionListener(new IExecutionListenerWithChecks() {

			@Override
			public void notHandled(String commandId,
					NotHandledException exception) {
			}

			@Override
			public void postExecuteFailure(String commandId,
					ExecutionException exception) {
			}

			@Override
			public void postExecuteSuccess(String commandId, Object returnValue) {
						+ returnValue);
			}

			@Override
			public void preExecute(String commandId, ExecutionEvent event) {
						+ event.getParameters().keySet());
			}

			@Override
			public void notDefined(String commandId,
					NotDefinedException exception) {
			}

			@Override
			public void notEnabled(String commandId,
					NotEnabledException exception) {
			}
		});
	}
}
