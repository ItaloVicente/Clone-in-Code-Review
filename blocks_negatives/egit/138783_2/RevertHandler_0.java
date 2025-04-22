	private static void showFailureDialog(final Shell shell,
			final RevCommit commit, final MergeResult result) {
		shell.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				RevertFailureDialog.show(shell, commit, result);
			}
