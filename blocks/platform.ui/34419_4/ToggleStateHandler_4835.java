package org.eclipse.ui.tests.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class SubtractIntegerHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Integer minuend = (Integer) event
				.getObjectParameterForExecution(CommandParameterTypeTest.MINUEND);
		Integer subtrahend = (Integer) event
				.getObjectParameterForExecution(CommandParameterTypeTest.SUBTRAHEND);
		return new Integer(minuend.intValue() - subtrahend.intValue());
	}
}
