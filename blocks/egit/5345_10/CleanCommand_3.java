package org.eclipse.egit.ui.internal.clean;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;

public class CleanWizardDialog extends WizardDialog {

	public CleanWizardDialog(Shell parentShell, Repository repository) {
		super(parentShell, new CleanWizard(repository));
	}

}
