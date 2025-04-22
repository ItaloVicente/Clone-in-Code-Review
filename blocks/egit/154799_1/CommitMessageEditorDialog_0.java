		this(parentShell, commitMessage, title,
				UIText.RebaseInteractiveHandler_EditMessageDialogOkButton,
				IDialogConstants.CANCEL_LABEL);
	}

	public CommitMessageEditorDialog(Shell parentShell, String commitMessage,
			String okButtonLabel, String cancelButtonLabel) {
		this(parentShell, commitMessage,
				UIText.CommitMessageEditorDialog_EditCommitMessageTitle,
				okButtonLabel, cancelButtonLabel);
	}

	public CommitMessageEditorDialog(Shell parentShell, String commitMessage,
			String title, String okButtonLabel, String cancelButtonLabel) {
