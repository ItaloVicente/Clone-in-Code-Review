
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class RestartWorkbenchHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event){
		
		PlatformUI.getWorkbench().restart();
		return null;
	}
}
