package org.eclipse.e4.ui.macros.internal.actions;

import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.e4.core.macros.EMacroService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

public class ToggleMacroRecordAction extends AbstractHandler implements IElementUpdater {

	public static final String COMMAND_ID = "org.eclipse.e4.ui.macros.toggleRecordMacro"; //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) {
		PlatformUI.getWorkbench().getService(EMacroService.class).toggleMacroRecord();
		return null;
	}

	@Override
	public void updateElement(UIElement element, Map parameters) {
		element.setChecked(PlatformUI.getWorkbench().getService(EMacroService.class).isRecording());
	}

}
