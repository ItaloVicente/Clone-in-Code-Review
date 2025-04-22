
package org.eclipse.ui.tests.contexts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.core.commands.contexts.ContextManagerEvent;
import org.eclipse.core.commands.contexts.IContextManagerListener;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.Scheme;
import org.eclipse.jface.bindings.keys.KeyBinding;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.contexts.IContextIds;
import org.eclipse.ui.tests.harness.util.UITestCase;

public final class Bug84763Test extends UITestCase {

	private BindingManager bindingManager = null;

	private ContextManager contextManager = null;

	private IContextManagerListener contextManagerListener = null;

	private Set previousContextIds = null;

	public Bug84763Test(final String name) {
		super(name);
	}

	@Override
	protected void doSetUp() {
		contextManager = new ContextManager();
		contextManagerListener = new IContextManagerListener() {

			@Override
			public void contextManagerChanged(
					ContextManagerEvent contextManagerEvent) {
				previousContextIds = contextManagerEvent
						.getPreviouslyActiveContextIds();
				if (previousContextIds != null) {
					previousContextIds = new HashSet(previousContextIds);
				}
			}

		};
		contextManager.addContextManagerListener(contextManagerListener);
		bindingManager = new BindingManager(contextManager,
				new CommandManager());
	}

	@Override
	protected void doTearDown() {
		contextManager = null;
		contextManagerListener = null;
		previousContextIds = null;
		bindingManager = null;
	}

	public void testWindowChildWhenDialog() throws NotDefinedException,
			ParseException {
		final Context dialogAndWindowsContext = contextManager
				.getContext(IContextIds.CONTEXT_ID_DIALOG_AND_WINDOW);
		dialogAndWindowsContext.define("In Dialogs and Windows", null, null);
		final Context dialogContext = contextManager
				.getContext(IContextIds.CONTEXT_ID_DIALOG);
		dialogContext.define("In Dialogs", null,
				IContextIds.CONTEXT_ID_DIALOG_AND_WINDOW);
		final Context windowContext = contextManager
				.getContext(IContextIds.CONTEXT_ID_WINDOW);
		windowContext.define("In Windows", null,
				IContextIds.CONTEXT_ID_DIALOG_AND_WINDOW);
		final Context windowChildContext = contextManager.getContext("sibling");
		windowChildContext.define("Sibling", null,
				IContextIds.CONTEXT_ID_WINDOW);

		final Scheme scheme = bindingManager.getScheme("na");
		scheme.define("name", null, null);
		bindingManager.setActiveScheme(scheme);
		final CommandManager commandManager = new CommandManager();
		final Command command = commandManager.getCommand("commandId");
		final ParameterizedCommand parameterizedCommand = new ParameterizedCommand(
				command, null);
		bindingManager.addBinding(new KeyBinding(KeySequence
				.getInstance("CTRL+F"), parameterizedCommand, scheme.getId(),
				windowChildContext.getId(), null, null, null, Binding.SYSTEM));
		bindingManager.getActiveBindingsFor((ParameterizedCommand) null);

		final Set activeContextIds = new HashSet();
		activeContextIds.add(IContextIds.CONTEXT_ID_DIALOG);
		activeContextIds.add(IContextIds.CONTEXT_ID_DIALOG_AND_WINDOW);
		activeContextIds.add(windowChildContext.getId());
		contextManager.setActiveContextIds(activeContextIds);

		bindingManager.getActiveBindingsFor((ParameterizedCommand) null);

		activeContextIds.remove(IContextIds.CONTEXT_ID_DIALOG);
		activeContextIds.add(IContextIds.CONTEXT_ID_WINDOW);
		contextManager.setActiveContextIds(activeContextIds);

		bindingManager.getActiveBindingsFor((ParameterizedCommand) null);

		assertEquals("There should have been 3 context ids active previously",
				3, previousContextIds.size());
		assertTrue("The previous contexts should include the dialog context",
				previousContextIds.contains(IContextIds.CONTEXT_ID_DIALOG));
		assertTrue("The previous contexts should include the dialog context",
				previousContextIds
						.contains(IContextIds.CONTEXT_ID_DIALOG_AND_WINDOW));
		assertTrue("The previous contexts should include the dialog context",
				previousContextIds.contains(windowChildContext.getId()));
		System.out.println("testSiblingContext");
	}
}
