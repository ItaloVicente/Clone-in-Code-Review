	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		cancel = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		cancel.setCursor(arrowCursor);

		createDetailsButton(parent);
	}

