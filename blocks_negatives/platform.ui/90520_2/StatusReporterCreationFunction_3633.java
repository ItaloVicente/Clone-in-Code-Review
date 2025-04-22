/*******************************************************************************
 * Copyright (c) 2010, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.internal.workbench.swt;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.bindings.EBindingService;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.Policy;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.EContextService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * An SWT listener for listening for activation events of shells that aren't
 * associated with an MWindow.
 */
public class ShellActivationListener implements Listener {

	/**
	 * A string key for use with a shell's keyed data to determine whether
	 * activation events of that shell should be ignored by this listener. The
	 * retrieved data of this string key must either be <code>null</code> or be
	 * of type <code>Boolean</code>.
	 */

	/**
	 * A string key for use with a shell's keyed data to retrieve the top level
	 * eclipse context that the shell is supposed to represent.
	 */

	private MApplication application;

	ShellActivationListener(MApplication application) {
		this.application = application;
	}

	@Override
	public void handleEvent(Event event) {
		if (!(event.widget instanceof Shell)) {
			return;
		}

		Shell shell = (Shell) event.widget;
		Object obj = shell.getData(AbstractPartRenderer.OWNING_ME);
		if (obj instanceof MWindow) {
			processWindow(event, shell, (MWindow) obj);
			return;
		}

		obj = shell.getData(DIALOG_IGNORE_KEY);
		if (obj instanceof Boolean && ((Boolean) obj).booleanValue()) {
			return;
		}

		switch (event.type) {
		case SWT.Activate:
			activate(shell);
			break;
		case SWT.Deactivate:
			deactivate(shell);
			break;
		}
	}

	private void processWindow(Event event, Shell shell, MWindow window) {
		switch (event.type) {
		case SWT.Activate:
			final IEclipseContext local = window.getContext();
			if (Policy.DEBUG_WORKBENCH) {
				WorkbenchSWTActivator.trace(Policy.DEBUG_WORKBENCH_FLAG,
						"setting mwindow context " + local, null);
			}
			shell.setData(ECLIPSE_CONTEXT_SHELL_CONTEXT, local);

			SafeRunner.run(new ISafeRunnable() {
				@Override
				public void run() throws Exception {
					local.activateBranch();
				}

				@Override
				public void handleException(Throwable exception) {
					WorkbenchSWTActivator.trace("/trace/workbench",
							"failed correcting context chain", exception);
				}
			});
			break;
		case SWT.Deactivate:
			Object context = window.getContext();
			if(Policy.DEBUG_WORKBENCH) {
				WorkbenchSWTActivator.trace(Policy.DEBUG_WORKBENCH_FLAG,
						"setting mwindow context " + context, null);
			}
			shell.setData(ECLIPSE_CONTEXT_SHELL_CONTEXT, context);
			break;
		}
	}

	private void activate(Shell shell) {
		final IEclipseContext parentContext = application.getContext();
		final IEclipseContext shellContext = getShellContext(shell,
				parentContext);

		SafeRunner.run(new ISafeRunnable() {
			@Override
			public void run() throws Exception {
				shellContext.activate();
			}

			@Override
			public void handleException(Throwable exception) {
				WorkbenchSWTActivator.trace("/trace/workbench",
						"failed setting dialog child", exception);
			}
		});

	}

	private void deactivate(Shell shell) {

	}

	/**
	 * Retrieves the eclipse context for the specified shell. If one cannot be
	 * found, a child context will be created off of the provided parent
	 * context.
	 *
	 * @param shell
	 *            the shell of interest, must not be <code>null</code>
	 * @param parentContext
	 *            the parent context that the shell's context should be created
	 *            off of if it doesn't have one, must not be <code>null</code>
	 * @return the shell's eclipse context
	 */
	private IEclipseContext getShellContext(final Shell shell,
			IEclipseContext parentContext) {
		IEclipseContext shellContext = (IEclipseContext) shell
				.getData(ECLIPSE_CONTEXT_SHELL_CONTEXT);
		if (shellContext != null) {
			return shellContext;
		}
		final IEclipseContext context = parentContext
				.createChild(EBindingService.DIALOG_CONTEXT_ID);

		context.set(E4Workbench.LOCAL_ACTIVE_SHELL, shell);

		shell.setData(ECLIPSE_CONTEXT_SHELL_CONTEXT, context);

		EContextService contextService = context.get(EContextService.class);
		contextService.activateContext(EBindingService.DIALOG_CONTEXT_ID);

		shell.addDisposeListener(e -> {
			deactivate(shell);
			context.dispose();
		});

		return context;
	}
}
