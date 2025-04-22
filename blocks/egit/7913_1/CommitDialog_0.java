	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId)
			okPressed();
		else if (COMMIT_AND_PUSH_ID == buttonId) {
			isPushRequested = true;
			okPressed();
		} else if (IDialogConstants.CANCEL_ID == buttonId)
			cancelPressed();
	}

