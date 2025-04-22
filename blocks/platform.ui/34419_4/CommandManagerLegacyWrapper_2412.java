
package org.eclipse.ui.internal.commands;

import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.jface.bindings.BindingManager;

public final class CommandManagerFactory {

	public static CommandManagerLegacyWrapper getCommandManagerWrapper(
			final BindingManager bindingManager,
			final CommandManager commandManager,
			final ContextManager contextManager) {
		return new CommandManagerLegacyWrapper(bindingManager, commandManager,
				contextManager);
	}

	private CommandManagerFactory() {
	}
}
