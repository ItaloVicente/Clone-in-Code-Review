		final String[] result = new String[1];
		result[0] = commitMessage;
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				CommitMessageEditorDialog dialog = new CommitMessageEditorDialog(
						PlatformUI.getWorkbench()
						.getDisplay().getActiveShell(),
						commitMessage);
				if (dialog.open() == Window.OK) {
					result[0] = dialog.getCommitMessage();
				}
