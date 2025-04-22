package org.eclipse.e4.ui.progress.internal;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ViewSettingsDialog extends Dialog {

	private static int DEFAULTS_BUTTON_ID = 25;

	public ViewSettingsDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
    protected void buttonPressed(int buttonId) {
		if (buttonId == DEFAULTS_BUTTON_ID) {
			performDefaults();
		}
		super.buttonPressed(buttonId);
	}

	protected void performDefaults() {

	}

	@Override
    protected void createButtonsForButtonBar(Composite parent) {
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createButton(parent, DEFAULTS_BUTTON_ID, JFaceResources.getString("defaults"), false); //$NON-NLS-1$

		Label l = new Label(parent, SWT.NONE);
		l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		l = new Label(parent, SWT.NONE);
		l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		GridLayout layout = (GridLayout) parent.getLayout();
		layout.numColumns += 2;
		layout.makeColumnsEqualWidth = false;

		super.createButtonsForButtonBar(parent);
	}

}
