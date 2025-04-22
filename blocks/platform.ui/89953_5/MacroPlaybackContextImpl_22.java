package org.eclipse.e4.ui.macros.internal.actions;

import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.macros.EMacroContext;

public class MacroPlaybackActionImpl {

	@Inject
	private EMacroContext fMacroContext;

	@Execute
	public void execute() {
		fMacroContext.playbackLastMacro(new MacroPlaybackContextImpl());
	}

}
