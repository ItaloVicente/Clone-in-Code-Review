package org.eclipse.e4.ui.macros.internal.keybindings;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.CommandException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.core.macros.IMacroCommand;
import org.eclipse.e4.core.macros.IMacroPlaybackContext;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;
import org.eclipse.swt.widgets.Event;

public class MacroForParameterizedCommand implements IMacroCommand {

	private static final String ID = "org.eclipse.e4.ui.bindings.keys.parameterized_macro_command"; //$NON-NLS-1$

	private static final String CHARACTER = "character"; //$NON-NLS-1$

	private static final String TYPE = "type"; //$NON-NLS-1$

	private static final String STATE_MASK = "stateMask"; //$NON-NLS-1$

	private static final String KEY_CODE = "keyCode"; //$NON-NLS-1$

	private static final String COMMAND = "command"; //$NON-NLS-1$

	@Inject
	private KeyBindingDispatcher fDispatcher;

	private ParameterizedCommand fCmd;

	private Event fEvent;

	public MacroForParameterizedCommand(ParameterizedCommand cmd, Event event) {
		this.fCmd = cmd;

		Event newEvent = new Event();
		newEvent.keyCode = event.keyCode;
		newEvent.stateMask = event.stateMask;
		newEvent.type = event.type;
		newEvent.character = event.character;

		this.fEvent = newEvent;
	}

	@Override
	public void execute(IMacroPlaybackContext macroPlaybackContext) throws Exception {
		ParameterizedCommand cmd = fCmd;
		if (cmd == null) {
			throw new RuntimeException("Macro command not set."); //$NON-NLS-1$
		}
		try {
			fDispatcher.executeCommand(cmd, this.fEvent);
		} catch (final CommandException e) {
			throw e;
		}
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public String toString() {
		try {
			return Messages.MacroForParameterizedCommand_0 + this.fCmd.getName();
		} catch (NotDefinedException e) {
			return Messages.MacroForParameterizedCommand_0 + "Undefined"; //$NON-NLS-1$
		}
	}

	@Override
	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<>();
		String serialized = fCmd.serialize();
		Assert.isNotNull(serialized);
		map.put(COMMAND, serialized);
		map.put(KEY_CODE, Integer.toString(fEvent.keyCode));
		map.put(STATE_MASK, Integer.toString(fEvent.stateMask));
		map.put(TYPE, Integer.toString(fEvent.type));
		map.put(CHARACTER, Character.toString(fEvent.character));

		return map;
	}

			KeyBindingDispatcher keybindingDispatcher)
			throws Exception {
		Assert.isNotNull(commandManager);
		Assert.isNotNull(map);
		ParameterizedCommand cmd = commandManager.deserialize(map.get(COMMAND));
		Event event = new Event();
		event.keyCode = Integer.parseInt(map.get(KEY_CODE));
		event.stateMask = Integer.parseInt(map.get(STATE_MASK));
		event.type = Integer.parseInt(map.get(TYPE));
		event.character = map.get(CHARACTER).charAt(0);
		MacroForParameterizedCommand command = new MacroForParameterizedCommand(cmd, event);
		command.fDispatcher = keybindingDispatcher;
		return command;
	}
}

