package org.eclipse.ui.tests.navigator.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class ImportWizard1 extends Wizard implements IImportWizard {

	public ImportWizard1() {
	}

	@Override
	public boolean performFinish() {
		return false;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

	}

}
