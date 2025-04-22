package org.eclipse.ui.tests.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

public class CheckInvokedHandler extends AbstractHandler {

	public static boolean invoked = false;

	@Override
	public Object execute(ExecutionEvent event) {
		invoked = true;
		return Boolean.TRUE;
	}

}
