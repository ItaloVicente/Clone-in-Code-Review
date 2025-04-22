package org.eclipse.egit.ui.internal.push;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class PushBranchDialog extends WizardDialog {

	public PushBranchDialog(Shell parentShell, PushBranchWizard newWizard) {
		super(parentShell, newWizard);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		Button nextButton = getButton(IDialogConstants.NEXT_ID);
		if (nextButton != null) {
			nextButton.setLabel(UIText.PushBranchWizard_previewButton);
		}
		Button finishButton = getButton(IDialogConstants.FINISH_ID);
		if (finishButton != null) {
			finishButton.setLabel(UIText.PushBranchWizard_pushButton);
		}
	}

}
