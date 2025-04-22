package org.eclipse.ui.tests.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;

public class RadioStateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		if(HandlerUtil.matchesRadioState(event))
			return null; // do nothing when we are in right state
		
		String currentState = event.getParameter(RadioState.PARAMETER_ID);
		HandlerUtil.updateRadioState(event.getCommand(), currentState);

		return null;
	}

}
