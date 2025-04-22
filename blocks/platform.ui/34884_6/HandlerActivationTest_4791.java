package org.eclipse.ui.tests.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ExceptionThrowingHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		throw new ExecutionException("");
	}

}
