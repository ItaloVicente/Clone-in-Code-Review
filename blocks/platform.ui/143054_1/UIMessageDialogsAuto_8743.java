	}

	private MessageDialog getQuestionDialog(String title, String message) {
		return new MessageDialog(getShell(), title, null, message,
				MessageDialog.QUESTION,0,
				IDialogConstants.YES_LABEL,
				IDialogConstants.NO_LABEL );
	}

	private MessageDialog getWarningDialog(String title, String message) {
		return new MessageDialog(getShell(), title, null, message,
				MessageDialog.WARNING, 0,
				IDialogConstants.OK_LABEL);
	}

	public void testAbortPageFlipping() {
		Dialog dialog = getWarningDialog(JFaceResources
				.getString("AbortPageFlippingDialog.title"), JFaceResources
				.getString("AbortPageFlippingDialog.message"));
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testCopyOverwrite() {
		Dialog dialog = getQuestionDialog("Exists","");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testDeleteProject() {
		String title = "Project";
		String msg ="";
		Dialog dialog = new MessageDialog(getShell(), title, null, // accept the default window icon
