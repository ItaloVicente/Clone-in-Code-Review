package org.eclipse.ui.dialogs;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkingSet;

public interface IWorkingSetNewWizard extends IWizard {
	
    public IWorkingSet getSelection();
}
