
	private void showFailureDialog(final Shell shell, final RevCommit commit,
			final MergeResult result) {
		shell.getDisplay().syncExec(new Runnable() {

			public void run() {
				RevertFailureDialog.show(shell, commit, result);
			}
		});
	}

	private void showRevertedDialog(final Shell shell) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			public void run() {
				MessageDialog.openWarning(shell,
						UIText.RevertHandler_NoRevertTitle,
						UIText.RevertHandler_AlreadyRevertedMessae);
			}
		});
	}
