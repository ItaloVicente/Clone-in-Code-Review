package org.eclipse.e4.ui.progress.internal;

import org.eclipse.e4.ui.progress.legacy.ViewSettingsDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class JobsViewPreferenceDialog extends ViewSettingsDialog {

	private BooleanFieldEditor verboseEditor;

	public JobsViewPreferenceDialog(Shell parentShell) {
		super(parentShell);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(ProgressMessages.JobsViewPreferenceDialog_Title);
	}

	protected Control createDialogArea(Composite parent) {
		Composite top = (Composite) super.createDialogArea(parent);
		
		Composite editArea = new Composite(top, SWT.NONE);
		editArea.setLayout(new GridLayout());
		editArea.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		
		verboseEditor = new BooleanFieldEditor("verbose", ProgressMessages.JobsViewPreferenceDialog_Note, editArea);//$NON-NLS-1$
		verboseEditor.load();
		
		Dialog.applyDialogFont(top);
		
		return top;
	}
	
	protected void okPressed() {
		verboseEditor.store();
		super.okPressed();
	}
	
	protected void performDefaults() {
		verboseEditor.loadDefault();
	}
}
