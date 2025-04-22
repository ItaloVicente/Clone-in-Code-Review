	private static void showRevertedDialog() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
			MessageDialog.openWarning(
					PlatformUI.getWorkbench().getModalDialogShellProvider()
							.getShell(),
					UIText.RevertHandler_NoRevertTitle,
					UIText.RevertHandler_AlreadyRevertedMessage);
