		if (buttonId == IDialogConstants.DETAILS_ID) {
			detailsPressed();
			return;
		}
		super.buttonPressed(buttonId);
	}

	private void detailsPressed() {
		showDetails = !showDetails;
		setDetailButtonLabel();
		setDetailHints();
		setDetails();
		((Composite) getDialogArea()).layout(true);
		getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
