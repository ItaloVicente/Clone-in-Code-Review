package org.eclipse.e4.ui.macros.internal.actions;

import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.macros.EMacroContext;
import org.eclipse.swt.widgets.Shell;

public class ToggleMacroRecordActionImpl {

	@Inject
	private EMacroContext fMacroContext;


	@Execute
	public void execute(Shell shell) {
		fMacroContext.toggleMacroRecord();
	}

}
