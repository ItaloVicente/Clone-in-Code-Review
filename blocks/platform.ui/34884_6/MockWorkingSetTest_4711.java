package org.eclipse.ui.tests.api;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.dialogs.IWorkingSetPage;

public class MockWorkingSetPage extends WizardPage implements IWorkingSetPage {
    private IWorkingSet workingSet;

    public MockWorkingSetPage() {
        super(
                "MockWorkingSetPage", "Test Working Set", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$ $NON-NLS-2$
    }

    @Override
	public void createControl(Composite parent) {
    }

    @Override
	public IWorkingSet getSelection() {
        return workingSet;
    }

    @Override
	public void setSelection(IWorkingSet workingSet) {
    }

    @Override
	public void finish() {
    }

}
