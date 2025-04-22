	@Override
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		if (id == IDialogConstants.OK_ID) {
			return super.createButton(parent, id, WorkbenchMessages.WorkbenchPreferenceDialog_applyAndCloseButtonLabel,
					defaultButton);
		}
		return super.createButton(parent, id, label, defaultButton);
	}

