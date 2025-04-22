package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class EmptyPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage {
    @Override
	protected Control createContents(Composite parent) {
        return new Composite(parent, SWT.NULL);
    }

    @Override
	protected IPreferenceStore doGetPreferenceStore() {
        return WorkbenchPlugin.getDefault().getPreferenceStore();
    }

    @Override
	public void init(IWorkbench workbench) {
    }
}
