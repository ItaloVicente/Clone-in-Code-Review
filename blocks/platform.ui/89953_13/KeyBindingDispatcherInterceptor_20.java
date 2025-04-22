package org.eclipse.e4.ui.macros.internal.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.e4.core.macros.EMacroService;
import org.eclipse.ui.PlatformUI;

public class ToggleMacroRecordAction extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) {
		PlatformUI.getWorkbench().getService(EMacroService.class).toggleMacroRecord();
		return null;
	}

}
