	private String promptCommitMessage(String message) {
		String[] msg = { message };
		PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
			Shell shell = PlatformUI.getWorkbench()
					.getModalDialogShellProvider().getShell();
			CommitMessageEditorDialog dialog = new CommitMessageEditorDialog(
					shell, msg[0], UIText.CommitMessageEditorDialog_OkButton,
					UIText.SquashHandler_EditMessageDialogCancelButton);
			if (dialog.open() == Window.OK) {
				msg[0] = dialog.getCommitMessage();
