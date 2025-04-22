		shell.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				CommitMessageEditorDialog dialog = new CommitMessageEditorDialog(
						shell, msg[0],
						UIText.CommitMessageEditorDialog_OkButton,
						UIText.SquashHandler_EditMessageDialogCancelButton);
				if (dialog.open() == Window.OK)
					msg[0] = dialog.getCommitMessage();
				else
					msg[0] = message;
