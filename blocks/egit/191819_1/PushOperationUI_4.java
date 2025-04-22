	private static boolean warnMatching(Shell shell, String repository,
			String remote, String cause) {
		MessageDialog dialog = new MessageDialog(shell,
				UIText.PushOperationUI_PushMatchingTitle, null,
				MessageFormat.format(UIText.PushOperationUI_PushMatchingMessage,
						repository, remote, cause),
				MessageDialog.QUESTION, IDialogConstants.OK_ID,
				UIText.PushOperationUI_PushMultipleOkLabel,
				IDialogConstants.CANCEL_LABEL);
		return dialog.open() == Window.OK;
	}

