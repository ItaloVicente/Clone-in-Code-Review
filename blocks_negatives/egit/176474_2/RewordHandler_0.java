		final String[] message = { commit.getFullMessage() };
		shell.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				CommitMessageEditorDialog dialog = new CommitMessageEditorDialog(
						shell, message[0]);
				if (dialog.open() == Window.OK)
					message[0] = dialog.getCommitMessage();
				else
					message[0] = null;
			}
		});
		return message[0];
