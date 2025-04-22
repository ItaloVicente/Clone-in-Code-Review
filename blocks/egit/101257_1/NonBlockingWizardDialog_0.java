package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
	protected Control createDialogArea(Composite parent) {
		Control area = super.createDialogArea(parent);
		if (restrictResize) {
			parent.getShell().addShellListener(new ShellAdapter() {

				@Override
				public void shellActivated(ShellEvent e) {
					Shell shell = parent.getShell();
					shell.removeShellListener(this); // Only the first time
					shell.setMinimumSize(shell.getSize());
				}
			});
		}
		return area;
	}
}
