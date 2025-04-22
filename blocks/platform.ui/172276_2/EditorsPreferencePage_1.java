	protected void createDontShowDeleteConfirmAgainPref(Composite composite) {
		dontShowDeleteConfirm = new Button(composite, SWT.CHECK);
		dontShowDeleteConfirm.setText(WorkbenchMessages.WorkbenchPreference_dontShowDeleteConfirmAgainButton);
		dontShowDeleteConfirm.setSelection(
				getAPIPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.DONT_SHOW_DELETE_CONFIRM_AGAIN));
		setButtonLayoutData(dontShowDeleteConfirm);
	}

