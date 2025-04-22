		MessageDialog result = new MessageDialog(getShell(),
				JFaceResources.getString("WizardClosingDialog.title"), //$NON-NLS-1$
				null,
				JFaceResources.getString("WizardClosingDialog.message"), //$NON-NLS-1$
				MessageDialog.QUESTION,
				0, IDialogConstants.OK_LABEL) {
