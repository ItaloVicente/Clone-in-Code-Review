	@Override
	protected Control createButtonBar(Composite parent) {
		Control buttonSection = super.createButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(listViewer.getSelection().isEmpty());

		return buttonSection;
	}

	@Override
