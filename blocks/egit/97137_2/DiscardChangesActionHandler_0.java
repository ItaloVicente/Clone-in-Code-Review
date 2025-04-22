	private boolean openConfirmationDialog(ExecutionEvent event,
			String question) throws ExecutionException {
		MessageDialog dlg = new MessageDialog(getShell(event),
				UIText.DiscardChangesAction_confirmActionTitle, null, question,
				MessageDialog.CONFIRM, 0,
				UIText.DiscardChangesAction_discardChangesButtonText,
				IDialogConstants.CANCEL_LABEL);
		return dlg.open() == 0;
	}

