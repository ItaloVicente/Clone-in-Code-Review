package org.eclipse.e4.ui.bindings.keys;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.swt.widgets.Event;

public interface IKeyBindingDispatcherInterceptor {

	boolean interceptExecutePerfectMatch(ParameterizedCommand parameterizedCommand, Event event);

	void afterCommandExecuted(ParameterizedCommand parameterizedCommand, Event trigger, boolean commandDefined,
			boolean commandHandled);

}
