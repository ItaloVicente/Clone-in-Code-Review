	private void showNoChangesToStash(final Shell shell) {
		shell.getDisplay().asyncExec(new Runnable() {

			public void run() {
				MessageDialog.openInformation(shell,
						UIText.StashCreateCommand_titleNoChanges,
						UIText.StashCreateCommand_messageNoChanges);
			}
		});
	}
