package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

public class FinishableWizardDialog extends WizardDialog {

	public FinishableWizardDialog(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
	}

	public boolean finish() {
		Button finishButton = getButton(IDialogConstants.FINISH_ID);
		if (finishButton.isEnabled() && getWizard().canFinish()) {
			finishPressed();
			return getShell() == null || getShell().isDisposed();
		}
		return false;
	}
}
