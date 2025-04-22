	private boolean validateFile(File file) {

		if (file == null)
			return false;

		if (!file.exists())
			return true;

		if (!file.canWrite()) {
			final String title= UIText.GitCreatePatchWizard_ReadOnlyTitle;
			final String msg= UIText.GitCreatePatchWizard_ReadOnlyMsg;
			final MessageDialog dialog= new MessageDialog(getShell(), title, null, msg, MessageDialog.ERROR, new String[] { IDialogConstants.OK_LABEL }, 0);
			dialog.open();
			return false;
		}

		final String title = UIText.GitCreatePatchWizard_OverwriteTitle;
		final String msg = UIText.GitCreatePatchWizard_OverwriteMsg;
		final MessageDialog dialog = new MessageDialog(getShell(), title, null, msg, MessageDialog.QUESTION, new String[] { IDialogConstants.YES_LABEL, IDialogConstants.CANCEL_LABEL }, 0);
		dialog.open();
		if (dialog.getReturnCode() != 0)
			return false;

		return true;
	}

