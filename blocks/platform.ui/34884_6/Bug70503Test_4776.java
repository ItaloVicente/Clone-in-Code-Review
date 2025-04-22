
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

public final class Bug66182Test extends UITestCase {

	public Bug66182Test(final String name) {
		super(name);
	}

	public final void testDialogHandlers() throws ExecutionException,
			NotHandledException {
	}

	public final void testFallbackToWindow() throws ExecutionException,
			NotHandledException {
	}

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
		while (display.readAndDispatch())
			;

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
		while (display.readAndDispatch())
			;
	}

	public final void testWindow() throws ExecutionException,
			NotHandledException {
	}
}
