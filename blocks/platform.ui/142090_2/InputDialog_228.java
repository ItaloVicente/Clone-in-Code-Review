		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		text.setFocus();
		if (value != null) {
			text.setText(value);
			text.selectAll();
		}
	}
