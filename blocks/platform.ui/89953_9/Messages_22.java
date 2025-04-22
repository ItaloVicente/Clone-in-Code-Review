package org.eclipse.e4.ui.macros.internal.actions;

import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.macros.EMacroContextService;

public class MacroPlaybackActionImpl {

	@Inject
	private EMacroContextService fMacroContext;

	@Execute
	public void execute() {
		fMacroContext.playbackLastMacro();
	}

}
