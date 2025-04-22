package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.ui.dialogs.PropertyDialogAction;

public interface IWorkbenchPropertyPage extends IPreferencePage {
    public IAdaptable getElement();

    public void setElement(IAdaptable element);
}
