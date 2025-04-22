
package org.eclipse.ui.examples.contributions.model;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class PersonWizard extends Wizard implements INewWizard {
	private PersonWizardPage mainPage;
	private IWorkbench workbench;

	public void addPages() {
		mainPage = new PersonWizardPage(workbench);
		addPage(mainPage);
	}

	public boolean performFinish() {
		return mainPage.finish();
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
	}

}
