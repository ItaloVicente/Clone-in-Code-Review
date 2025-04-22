package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.dialogs.IWorkingSetEditWizard;
import org.eclipse.ui.dialogs.IWorkingSetPage;
import org.eclipse.ui.internal.WorkbenchMessages;

public class WorkingSetEditWizard extends Wizard implements
        IWorkingSetEditWizard {
    private IWorkingSetPage workingSetEditPage;

    private IWorkingSet workingSet;

    public WorkingSetEditWizard(IWorkingSetPage editPage) {
        super();
        workingSetEditPage = editPage;
        workingSetEditPage.setWizard(this);
        setWindowTitle(WorkbenchMessages.WorkingSetEditWizard_title);
    }

    @Override
	public void addPages() {
        super.addPages();
        addPage(workingSetEditPage);
    }

    @Override
	public boolean canFinish() {
        return workingSetEditPage.isPageComplete();
    }

    @Override
	public IWorkingSet getSelection() {
        return workingSet;
    }

    @Override
	public boolean performFinish() {
        workingSetEditPage.finish();
        return true;
    }

    public void setSelection(IWorkingSet workingSet) {
        this.workingSet = workingSet;
        workingSetEditPage.setSelection(workingSet);
    }
}
