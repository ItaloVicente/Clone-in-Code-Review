
package org.eclipse.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;

public interface IWorkbenchWizard extends IWizard {
    void init(IWorkbench workbench, IStructuredSelection selection);
}
