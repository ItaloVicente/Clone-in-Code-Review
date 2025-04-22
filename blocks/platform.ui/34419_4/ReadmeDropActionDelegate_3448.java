package org.eclipse.ui.examples.readmetool;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class ReadmeCreationWizard extends Wizard implements INewWizard {
    private IStructuredSelection selection;

    private IWorkbench workbench;

    private ReadmeCreationPage mainPage;

    @Override
	public void addPages() {
        mainPage = new ReadmeCreationPage(workbench, selection);
        addPage(mainPage);
    }

    @Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        setWindowTitle(MessageUtil.getString("New_Readme_File")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ReadmeImages.README_WIZARD_BANNER);
    }

    @Override
	public boolean performFinish() {
        return mainPage.finish();
    }
}
