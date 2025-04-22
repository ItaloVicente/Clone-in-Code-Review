package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public final class HandlersExtensionDynamicTest extends DynamicTestCase {

	public HandlersExtensionDynamicTest(final String testName) {
		super(testName);
	}

	@Override
	protected final String getExtensionId() {
		return "handlersExtensionDynamicTest.testDynamicHandlerAddition";
	}

	@Override
	protected final String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_HANDLERS;
	}

	@Override
	protected final String getInstallLocation() {
		return "data/org.eclipse.handlersExtensionDynamicTest";
	}

	public final void testHandlers() {
		final ICommandService commandService = (ICommandService) getWorkbench()
				.getAdapter(ICommandService.class);
		Command command;

		command = commandService.getCommand("monkey");
		try {
			command.execute(new ExecutionEvent());
			fail();
		} catch (final ExecutionException e) {
			fail();
		} catch (final NotHandledException e) {
			assertTrue(true);
		}

		getBundle();

		command = commandService.getCommand("monkey");
		try {
			command.execute(new ExecutionEvent());
		} catch (final ExecutionException e) {
			fail();
		} catch (final NotHandledException e) {
			fail();
		}

		removeBundle();

		command = commandService.getCommand("monkey");
		try {
			command.execute(new ExecutionEvent());
			fail();
		} catch (final ExecutionException e) {
			fail();
		} catch (final NotHandledException e) {
			assertTrue(true);
		}
	}
}
