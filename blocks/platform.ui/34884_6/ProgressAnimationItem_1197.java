package org.eclipse.ui.internal.progress;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.preferences.ViewSettingsDialog;

public class JobsViewPreferenceDialog extends ViewSettingsDialog {

	private BooleanFieldEditor verboseEditor;

	public JobsViewPreferenceDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(ProgressMessages.JobsViewPreferenceDialog_Title);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite top = (Composite) super.createDialogArea(parent);
		
		Composite editArea = new Composite(top, SWT.NONE);
		editArea.setLayout(new GridLayout());
		editArea.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		
		verboseEditor = new BooleanFieldEditor("verbose", ProgressMessages.JobsViewPreferenceDialog_Note, editArea);//$NON-NLS-1$ 
		verboseEditor.setPreferenceName(IWorkbenchPreferenceConstants.SHOW_SYSTEM_JOBS);
		verboseEditor.setPreferenceStore(PrefUtil.getAPIPreferenceStore());
		verboseEditor.load();
		
		Dialog.applyDialogFont(top);
		
		return top;
	}
	
	@Override
	protected void okPressed() {
		verboseEditor.store();
		super.okPressed();
	}
	
	@Override
	protected void performDefaults() {
		verboseEditor.loadDefault();
	}
}
