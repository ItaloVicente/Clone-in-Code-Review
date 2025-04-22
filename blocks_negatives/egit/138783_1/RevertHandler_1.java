	private static void showRevertedDialog(final Shell shell) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog.openWarning(shell,
						UIText.RevertHandler_NoRevertTitle,
						UIText.RevertHandler_AlreadyRevertedMessage);
			}
