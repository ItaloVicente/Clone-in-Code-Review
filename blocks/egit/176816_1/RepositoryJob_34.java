package org.eclipse.egit.ui.internal.jobs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.dialogs.ErrorDialogWithHelp;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;

public class GpgConfigProblemReportAction extends Action {

	private IStatus status;

	private String message;

	public GpgConfigProblemReportAction(IStatus status, String message) {
		super(UIText.GpgConfigProblemReportAction_Title);
		this.status = status;
		this.message = message;
	}

	@Override
	public void run() {
		ErrorDialogWithHelp dialog = new ErrorDialogWithHelp(
				PlatformUI.getWorkbench().getModalDialogShellProvider()
						.getShell(),
				UIText.GpgConfigProblemReportAction_Title, message, status,
				"org.eclipse.egit.ui.gpgSigning"); //$NON-NLS-1$
		dialog.open();
	}
}
