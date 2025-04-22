
package org.eclipse.e4.ui.progress;


import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.progress.internal.JobsViewPreferenceDialog;
import org.eclipse.e4.ui.progress.internal.PreferenceStore;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Shell;


public class OpenPreferenceDialogHandler {

	@Execute
	public void openPreferenceDialog(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell, PreferenceStore preferences) {
		new JobsViewPreferenceDialog(shell, preferences).open();
	}

}
