package org.eclipse.e4.ui.progress.internal;

import org.eclipse.e4.ui.progress.IProgressConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class JobsViewPreferenceDialog extends ViewSettingsDialog {

	private BooleanFieldEditor showSystemJob;
	private BooleanFieldEditor runInBackground;
	private IPreferenceStore preferenceStore;


	public JobsViewPreferenceDialog(Shell parentShell, IPreferenceStore preferenceStore) {
		super(parentShell);
		this.preferenceStore = preferenceStore;
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
		
		runInBackground = new BooleanFieldEditor(IProgressConstants.RUN_IN_BACKGROUND, ProgressMessages.JobsViewPreferenceDialog_RunInBackground, editArea);//$NON-NLS-1$
		runInBackground.setPreferenceName(IProgressConstants.RUN_IN_BACKGROUND);
		runInBackground.setPreferenceStore(preferenceStore);
		runInBackground.load();
		
		showSystemJob = new BooleanFieldEditor(IProgressConstants.SHOW_SYSTEM_JOBS, ProgressMessages.JobsViewPreferenceDialog_ShowSystemJobs, editArea);//$NON-NLS-1$
		showSystemJob.setPreferenceName(IProgressConstants.SHOW_SYSTEM_JOBS);
		showSystemJob.setPreferenceStore(preferenceStore);
		showSystemJob.load();
		
		Dialog.applyDialogFont(top);
		
		return top;
	}
	
	protected void okPressed() {
		runInBackground.store();
		showSystemJob.store();
		super.okPressed();
	}
	
	protected void performDefaults() {
		runInBackground.loadDefault();
		showSystemJob.loadDefault();
	}
}
