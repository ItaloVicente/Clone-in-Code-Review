	private void showNotPerformedDialog(final Shell shell) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openWarning(shell,
						UIText.CherryPickHandler_NoCherryPickPerformedTitle,
						UIText.CherryPickHandler_NoCherryPickPerformedMessage);
			}
		});
	}

	private void showConflictDialog(final Shell shell) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openWarning(shell,
						UIText.CherryPickHandler_CherryPickConflictsTitle,
						UIText.CherryPickHandler_CherryPickConflictsMessage);
			}
		});
	}

	private void showFailure(CherryPickResult result) {
		IStatus details = getErrorList(result.getFailingPaths());
		Activator.showErrorStatus(
				UIText.CherryPickHandler_CherryPickFailedMessage, details);
	}

	private IStatus getErrorList(Map<String, MergeFailureReason> failingPaths) {
