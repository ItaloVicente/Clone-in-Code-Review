package org.eclipse.e4.ui.macros.internal.keybindings;

import java.util.Map;
import javax.inject.Inject;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.e4.core.macros.IMacroInstruction;
import org.eclipse.e4.core.macros.IMacroInstructionFactory;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;

public class MacroInstructionForParameterizedCommandFactory implements IMacroInstructionFactory {

	@Inject
	private CommandManager fCommandManager;

	@Inject
	private KeyBindingDispatcher fDispatcher;

	@Override
	public IMacroInstruction create(Map<String, String> stringMap) throws Exception {
		return MacroInstructionForParameterizedCommand.fromMap(stringMap, fCommandManager, fDispatcher);
	}

}
