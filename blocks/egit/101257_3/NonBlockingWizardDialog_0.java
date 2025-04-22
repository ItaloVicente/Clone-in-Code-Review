package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Shell;

public class MinimumSizeWizardDialog extends WizardDialog {

	private final boolean restrictResize;

	public MinimumSizeWizardDialog(Shell parentShell, IWizard newWizard) {
		this(parentShell, newWizard, true);
	}

	public MinimumSizeWizardDialog(Shell parentShell, IWizard newWizard,
			boolean restrictResize) {
		super(parentShell, newWizard);
		this.restrictResize = restrictResize;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (restrictResize) {
			newShell.addShellListener(new ShellAdapter() {

				@Override
				public void shellActivated(ShellEvent e) {
					newShell.removeShellListener(this); // Only the first time
					newShell.setMinimumSize(newShell.getSize());
				}
			});
		}
	}
}
