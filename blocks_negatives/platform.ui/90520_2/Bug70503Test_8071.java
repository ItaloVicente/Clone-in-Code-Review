/*******************************************************************************
 * Copyright (c) 2000, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.tests.commands;

import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.AbstractHandler;
import org.eclipse.ui.commands.ExecutionException;
import org.eclipse.ui.commands.HandlerSubmission;
import org.eclipse.ui.commands.ICommand;
import org.eclipse.ui.commands.IHandler;
import org.eclipse.ui.commands.IWorkbenchCommandSupport;
import org.eclipse.ui.commands.NotHandledException;
import org.eclipse.ui.commands.Priority;
import org.eclipse.ui.tests.harness.util.UITestCase;

/**
 * Tests that dialogs will inherit the handlers from the workbench window, if
 * none is defined for the dialog itself. It tests all of the various
 * combinations of this situation.
 *
 * @since 3.0
 */
public final class Bug66182Test extends UITestCase {

	/**
	 * Constructor for Bug66182Test.
	 *
	 * @param name
	 *            The name of the test
	 */
	public Bug66182Test(final String name) {
		super(name);
	}

	/**
	 * Tests that the dialog handlers will take priority. The set-up is a
	 * workbench window with a handler registered for the command to test. Then
	 * there is a dialog opened with a handler for the same command. The test is
	 * to see that the dialog's handler gets priority.
	 *
	 * @throws ExecutionException
	 *             This should never happen, and indicates a problem with the
	 *             test.
	 * @throws NotHandledException
	 *             Indicates that no handler was found where one should have
	 *             been found.
	 */
	public final void testDialogHandlers() throws ExecutionException,
			NotHandledException {
	}

	/**
	 * Tests that, in the absence of a dialog handler, that the window handler
	 * will be given a chance to take over. The set-up is a workbench window
	 * with a handler registered for the command to test. Then there is a dialog
	 * opened, but with no handler. The test is to see that the window's handler
	 * is active.
	 *
	 * @throws ExecutionException
	 *             This should never happen, and indicates a problem with the
	 *             test.
	 * @throws NotHandledException
	 *             Indicates that no handler was found where one should have
	 *             been found.
	 */
	public final void testFallbackToWindow() throws ExecutionException,
			NotHandledException {
	}

	/**
	 * Tests that if a dialog is open, that the application will not fall back
	 * to the dialog. The set-up is a workbench window with a handler registered
	 * for the command to test. Then there is a dialog opened, but with no
	 * handler. The test is to see that no handler is active.
	 *
	 * @throws ExecutionException
	 *             This should never happen, and indicates a problem with the
	 *             test.
	 * @throws NotHandledException
	 *             Indicates that no handler was found where one should have
	 *             been found.
	 */
	public final void testFallbackToWindowBlockedByDialog()
			throws ExecutionException, NotHandledException {
		final IWorkbenchWindow window = openTestWindow();

		final Object windowResult = new Object();
		final IHandler windowHandler = new AbstractHandler() {

			@Override
			public Object execute(Map parameterValuesByName) {
				return windowResult;
			}
		};
		final IWorkbenchCommandSupport commandSupport = fWorkbench
				.getCommandSupport();
		final String commandId = "org.eclipse.ui.tests.Bug66182";
		final Shell windowShell = window.getShell();
		final HandlerSubmission windowSubmission = new HandlerSubmission(null,
				windowShell, null, commandId, windowHandler, Priority.MEDIUM);
		commandSupport.addHandlerSubmission(windowSubmission);

		final Shell dialogShell = new Shell(windowShell);
		dialogShell.pack();
		dialogShell.open();
		final Display display = dialogShell.getDisplay();
		while (display.readAndDispatch()) {
			;
		}

		final ICommand command = commandSupport.getCommandManager().getCommand(
				commandId);
		assertSame(
				"The active shell must be the dialog.  If you are activating other shells while this test is running, then this test will fail",
				dialogShell, display.getActiveShell());
		assertSame(
				"The active workbench window must be the window created in this test.  If you are activating other workbench windows, then this test will fail",
				windowShell, fWorkbench.getActiveWorkbenchWindow().getShell());
		assertTrue(
				"When a dialog is open, it should not fall back to the active workbench window.",
				!command.isHandled());

		commandSupport.removeHandlerSubmission(windowSubmission);
		dialogShell.close();
		while (display.readAndDispatch()) {
			;
		}
	}

	/**
	 * Tests that if the workbench window is the active shell, that its handlers
	 * will take priority. The scenario has two handlers defined: one for the
	 * workbench window, and one for a dialog that is not open. The workbench
	 * window handler should be the active handler.
	 *
	 * @throws ExecutionException
	 *             This should never happen, and indicates a problem with the
	 *             test.
	 * @throws NotHandledException
	 *             Indicates that no handler was found where one should have
	 *             been found.
	 */
	public final void testWindow() throws ExecutionException,
			NotHandledException {
	}
}
