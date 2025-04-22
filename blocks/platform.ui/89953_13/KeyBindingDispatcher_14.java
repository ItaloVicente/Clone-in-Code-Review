package org.eclipse.e4.ui.bindings.keys;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.swt.widgets.Event;

public interface IKeyBindingInterceptor {

	boolean executeCommand(ParameterizedCommand parameterizedCommand, Event event);

	void postExecuteCommand(ParameterizedCommand parameterizedCommand, Event trigger, boolean commandDefined,
			boolean commandHandled);

}
