package org.eclipse.e4.ui.macros.internal.keybindings;

import java.util.Map;
import javax.inject.Inject;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.e4.core.macros.IMacroCommand;
import org.eclipse.e4.core.macros.IMacroCommandCreator;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;

public class MacroForParameterizedCommandCreator implements IMacroCommandCreator {

	@Inject
	private CommandManager fCommandManager;

	@Inject
	private KeyBindingDispatcher fDispatcher;

	@Override
	public IMacroCommand create(Map<String, String> stringMap) throws Exception {
		return MacroForParameterizedCommand.fromMap(stringMap, fCommandManager, fDispatcher);
	}

}
