	protected void createButtonsForButtonBar(Composite parent) {
		startButton = createButton(parent, IDialogConstants.OK_ID,
				UIText.StartDialog_ButtonOK, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);

		getText().setFocus();
		if (getValue() != null) {
			getText().setText(getValue());
			getText().selectAll();
