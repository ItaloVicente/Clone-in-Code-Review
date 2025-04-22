
package org.eclipse.jface.tests.wizards;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

public class TheTestWizardDialog extends WizardDialog {

	public TheTestWizardDialog(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
		setBlockOnOpen(false);
	}

	public Button getFinishedButton() {
		return getButton(IDialogConstants.FINISH_ID);
	}
	
	@Override
	public Button getCancelButton() {
		return getButton(IDialogConstants.CANCEL_ID);
	}

	public Button getBackButton() {
		return getButton(IDialogConstants.BACK_ID);
	}
	
	public Button getNextButton() {
		return getButton(IDialogConstants.NEXT_ID);
	}
	
	@Override
	public void finishPressed() {
		super.finishPressed();
	}
	@Override
	public void cancelPressed() {
		super.cancelPressed();
	}
	@Override
	public void backPressed() {
		super.backPressed();
	}
	@Override
	public void nextPressed() {
		super.nextPressed();
	}
	@Override
	public void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
	}
}
