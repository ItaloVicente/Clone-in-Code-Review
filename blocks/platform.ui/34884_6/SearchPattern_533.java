package org.eclipse.ui.dialogs;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.IWorkbenchPropertyPageMulti;

public abstract class PropertyPage extends PreferencePage implements IWorkbenchPropertyPage {
    private IAdaptable element;

    public PropertyPage() {
    }

    @Override
	public IAdaptable getElement() {
        return element;
    }

    @Override
	public void setElement(IAdaptable element) {
        this.element = element;
    }
}
