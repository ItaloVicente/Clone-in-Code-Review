
package org.eclipse.ui.tests.menus;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class HelloDHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		throw new ExecutionException("This should never be called");
	}
	
	@Override
	public boolean isEnabled() {
		return false;
	}
}
