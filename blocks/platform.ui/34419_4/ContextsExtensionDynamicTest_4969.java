package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NamedHandleObject;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.contexts.IContextIds;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.keys.IBindingService;

public final class CommandsExtensionDynamicTest extends DynamicTestCase {

	public CommandsExtensionDynamicTest(final String testName) {
		super(testName);
	}

	@Override
	protected final String getExtensionId() {
		return "commandsExtensionDynamicTest.testDynamicCommandAddition";
	}

	@Override
	protected final String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_COMMANDS;
	}

	@Override
	protected final String getInstallLocation() {
		return "data/org.eclipse.commandsExtensionDynamicTest";
	}

	public final void testCommands() throws ParseException {
		final IBindingService bindingService = (IBindingService) getWorkbench()
				.getAdapter(IBindingService.class);
		final ICommandService commandService = (ICommandService) getWorkbench()
				.getAdapter(ICommandService.class);
		final IContextService contextService = (IContextService) getWorkbench()
				.getAdapter(IContextService.class);
		final TriggerSequence triggerSequence = KeySequence.getInstance("M1+W");
		NamedHandleObject namedHandleObject;
		Binding[] bindings;
		Command command;
		boolean found;

		assertTrue(!"monkey".equals(bindingService.getActiveScheme().getId()));
		found = false;
		bindings = bindingService.getBindings();
		if (bindings != null) {
			for (final Binding binding : bindings) {
				if ("monkey".equals(binding.getSchemeId())
						&& IContextIds.CONTEXT_ID_WINDOW.equals(binding
								.getContextId())
						&& "monkey".equals(binding.getParameterizedCommand()
								.getId()) && binding.getPlatform() == null
						&& binding.getLocale() == null
						&& binding.getType() == Binding.SYSTEM
						&& triggerSequence.equals(binding.getTriggerSequence())) {
					found = true;

				}
			}
		}
		assertTrue(!found);
		namedHandleObject = bindingService.getScheme("monkey");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
		namedHandleObject = commandService.getCategory("monkey");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
		command = commandService.getCommand("monkey");
		try {
			command.execute(new ExecutionEvent());
			fail();
		} catch (final ExecutionException e) {
			fail();
		} catch (final NotHandledException e) {
			assertTrue(true);
		}
		try {
			command.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
		namedHandleObject = contextService.getContext("context");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
		namedHandleObject = contextService.getContext("scope");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}

		getBundle();

		assertTrue("monkey".equals(bindingService.getActiveScheme().getId()));
		found = false;
		bindings = bindingService.getBindings();
		if (bindings != null) {
			for (final Binding binding : bindings) {
				if ("monkey".equals(binding.getSchemeId())
						&& IContextIds.CONTEXT_ID_WINDOW.equals(binding
								.getContextId())
						&& "monkey".equals(binding.getParameterizedCommand()
								.getId()) && binding.getPlatform() == null
						&& binding.getLocale() == null
						&& binding.getType() == Binding.SYSTEM
						&& triggerSequence.equals(binding.getTriggerSequence())) {
					found = true;

				}
			}
		}
		assertTrue(found);
		namedHandleObject = bindingService.getScheme("monkey");
		try {
			assertTrue("Monkey".equals(namedHandleObject.getName()));
		} catch (final NotDefinedException e) {
			fail();
		}
		command = commandService.getCommand("monkey");
		try {
			command.execute(new ExecutionEvent());
		} catch (final ExecutionException e) {
			fail();
		} catch (final NotHandledException e) {
			fail();
		}
		try {
			assertEquals("Monkey", command.getName());
		} catch (final NotDefinedException e) {
			fail();
		}
		namedHandleObject = commandService.getCommand("monkey");
		try {
			assertTrue("Monkey".equals(namedHandleObject.getName()));
		} catch (final NotDefinedException e) {
			fail();
		}
		namedHandleObject = contextService.getContext("context");
		try {
			assertTrue("Monkey".equals(namedHandleObject.getName()));
		} catch (final NotDefinedException e) {
			fail();
		}
		namedHandleObject = contextService.getContext("scope");
		try {
			assertTrue("Monkey".equals(namedHandleObject.getName()));
		} catch (final NotDefinedException e) {
			fail();
		}

		removeBundle();

		assertTrue(!"monkey".equals(bindingService.getActiveScheme().getId()));
		found = false;
		bindings = bindingService.getBindings();
		if (bindings != null) {
			for (final Binding binding : bindings) {
				if ("monkey".equals(binding.getSchemeId())
						&& IContextIds.CONTEXT_ID_WINDOW.equals(binding
								.getContextId())
						&& "monkey".equals(binding.getParameterizedCommand()
								.getId()) && binding.getPlatform() == null
						&& binding.getLocale() == null
						&& binding.getType() == Binding.SYSTEM
						&& triggerSequence.equals(binding.getTriggerSequence())) {
					found = true;

				}
			}
		}
		assertTrue(!found);
		namedHandleObject = bindingService.getScheme("monkey");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
		namedHandleObject = commandService.getCategory("monkey");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
		command = commandService.getCommand("monkey");
		try {
			command.execute(new ExecutionEvent());
			fail();
		} catch (final ExecutionException e) {
			fail();
		} catch (final NotHandledException e) {
			assertTrue(true);
		}
		try {
			command.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
		namedHandleObject = contextService.getContext("context");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
		namedHandleObject = contextService.getContext("scope");
		try {
			namedHandleObject.getName();
			fail();
		} catch (final NotDefinedException e) {
			assertTrue(true);
		}
	}

	public void testNonExistingHandler() {
		IHandlerService handlerService = getWorkbench()
				.getService(IHandlerService.class);
		getBundle();

		try {
			handlerService.executeCommand(
					"org.eclipse.ui.tests.command.handlerLoadException", null);
			fail("An exception should be thrown for this handler");
		} catch (Exception e) {
			if (!(e instanceof ExecutionException)) {
				fail("Unexpected exception while executing command", e);
			}
		}

		try {
			handlerService.executeCommand(
					"org.eclipse.ui.tests.command.handlerLoadException", null);
			fail("An exception should be thrown for this handler");
		} catch (Exception e) {
			if (!(e instanceof NotHandledException)) {
				fail("Unexpected exception while executing command", e);
			}
		}

		removeBundle();
	}

}
