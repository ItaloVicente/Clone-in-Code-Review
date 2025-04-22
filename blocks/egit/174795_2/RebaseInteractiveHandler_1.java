		String[] result = { commitMessage };
		PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
			CommitMessageEditorDialog dialog = new CommitMessageEditorDialog(
					PlatformUI.getWorkbench().getModalDialogShellProvider()
							.getShell(),
					commitMessage);
			if (dialog.open() == Window.OK) {
				result[0] = dialog.getCommitMessage();
