		if (!file.canWrite()) {
			final String title= UIText.GitCreatePatchWizard_ReadOnlyTitle;
			final String msg= UIText.GitCreatePatchWizard_ReadOnlyMsg;
			final MessageDialog dialog= new MessageDialog(getShell(), title, null, msg, MessageDialog.ERROR, new String[] { IDialogConstants.OK_LABEL }, 0);
			dialog.open();
			return false;
