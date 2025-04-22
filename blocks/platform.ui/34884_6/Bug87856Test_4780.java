package org.eclipse.ui.tests.commands;

import java.util.Map;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.commands.AbstractHandler;
import org.eclipse.ui.commands.HandlerSubmission;
import org.eclipse.ui.commands.ICommand;
import org.eclipse.ui.commands.IHandler;
import org.eclipse.ui.commands.IWorkbenchCommandSupport;
import org.eclipse.ui.commands.Priority;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.tests.harness.util.UITestCase;

public final class Bug74990Test extends UITestCase {

	public Bug74990Test(final String name) {
		super(name);
	}

	public final void testPartIdSubmission() throws PartInitException {
		final String testCommandId = "org.eclipse.ui.tests.commands.Bug74990";
		final IWorkbenchCommandSupport commandSupport = fWorkbench
				.getCommandSupport();
		final ICommand testCommand = commandSupport.getCommandManager()
				.getCommand(testCommandId);

		final IHandler handler = new AbstractHandler() {
			@Override
			public final Object execute(final Map parameterValuesByName) {
				return null;
			}
		};
		final HandlerSubmission testSubmission = new HandlerSubmission(
				"org.eclipse.ui.tests.api.MockViewPart", null, null,
				testCommandId, handler, Priority.MEDIUM);
		commandSupport.addHandlerSubmission(testSubmission);

		try {
			assertTrue("The MockViewPart command should not be handled",
					!testCommand.isHandled());

			final IWorkbenchPage page = openTestWindow().getActivePage();
			final IViewPart openedView = page
					.showView("org.eclipse.ui.tests.api.MockViewPart");
			page.activate(openedView);
			while (fWorkbench.getDisplay().readAndDispatch()) {
				((Workbench)fWorkbench).getContext().processWaiting();
			}
			
			assertTrue("The MockViewPart command should be handled",
					testCommand.isHandled());

			page.hideView(openedView);
			while (fWorkbench.getDisplay().readAndDispatch()) {
			}
			assertTrue("The MockViewPart command should not be handled",
					!testCommand.isHandled());

		} finally {
			commandSupport.removeHandlerSubmission(testSubmission);
		}

	}
}
