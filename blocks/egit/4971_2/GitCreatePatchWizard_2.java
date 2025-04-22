		final String title = UIText.GitCreatePatchWizard_OverwriteTitle;
		final String msg = UIText.GitCreatePatchWizard_OverwriteMsg;
		final MessageDialog dialog = new MessageDialog(getShell(), title, null, msg, MessageDialog.QUESTION, new String[] { IDialogConstants.YES_LABEL, IDialogConstants.CANCEL_LABEL }, 0);
		dialog.open();
		if (dialog.getReturnCode() != 0)
			return false;
