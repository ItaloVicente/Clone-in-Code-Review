package org.eclipse.ui.dialogs;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkingSet;

public interface IWorkingSetPage extends IWizardPage {
    public void finish();

    public IWorkingSet getSelection();

    public void setSelection(IWorkingSet workingSet);
}
