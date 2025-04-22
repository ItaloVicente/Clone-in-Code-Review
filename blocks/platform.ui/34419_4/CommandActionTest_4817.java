
package org.eclipse.ui.tests.commands;

import java.lang.ref.WeakReference;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.ui.commands.AbstractHandler;
import org.eclipse.ui.commands.HandlerSubmission;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IHandler;
import org.eclipse.ui.commands.IWorkbenchCommandSupport;
import org.eclipse.ui.commands.Priority;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug87856Test extends UITestCase {

	public Bug87856Test(final String name) {
		super(name);
	}

	public final void testHandlerLeak() {
		final IWorkbenchCommandSupport commandSupport = fWorkbench
				.getCommandSupport();
		final ICommandService commandService = (ICommandService) fWorkbench
				.getAdapter(ICommandService.class);
		final String commandId = Bug87856Test.class.getName();
		final Command command = commandService.getCommand(commandId);

		IHandler handler = new AbstractHandler() {

			@Override
			public Object execute(Map parameterValuesByName) {
				return null;
			}

		};
		HandlerSubmission submission = new HandlerSubmission(null, null, null,
				command.getId(), handler, Priority.MEDIUM);
		commandSupport.addHandlerSubmission(submission);

		commandSupport.removeHandlerSubmission(submission);
		submission = null;
		final WeakReference reference = new WeakReference(handler);
		handler = null;

		System.gc();
		System.runFinalization();
		Thread.yield();
		System.gc();
		System.runFinalization();
		Thread.yield();
		System.gc();
		System.runFinalization();
		Thread.yield();

		assertTrue(
				"We should not hold on to a handler after the submission has been removed.",
				reference.isEnqueued() || (reference.get() == null));
	}
}
