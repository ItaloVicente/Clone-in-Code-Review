
package org.eclipse.ui.internal.keys;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class NoKeysPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		Label info = new Label(parent, SWT.NONE);
		info.setText("Custom key preferences are not available at this time.\nPlease create key bindings through the model."); //$NON-NLS-1$
		return info;
	}

}
