package org.eclipse.e4.ui.macros.internal.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

public class ToggleMacroRecordAction extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) {
		DIWrapper.execute(ToggleMacroRecordActionImpl.class);
		return null;
	}

}
