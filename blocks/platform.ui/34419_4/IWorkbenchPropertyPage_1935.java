package org.eclipse.ui;

import org.eclipse.jface.preference.IPreferencePage;

public interface IWorkbenchPreferencePage extends IPreferencePage {
    void init(IWorkbench workbench);
}
