
		MessageDialog messageDialog = new MessageDialog(shell,
				UIText.ResetTargetSelectionDialog_ResetQuestion, null, question,
				MessageDialog.QUESTION, 0,
				UIText.CommandConfirmationHardResetDialog_resetButtonLabel,
				IDialogConstants.CANCEL_LABEL);

		return messageDialog.open() == Window.OK;
