	}

	private void handleCoreException(CoreException exception, Shell shell, String title, String message) {
		IStatus status = exception.getStatus();
		if (status != null) {
			ErrorDialog.openError(shell, title, message, status);
		} else {
			MessageDialog.openError(shell, IDEWorkbenchMessages.InternalError, exception.getLocalizedMessage());
		}
	}

	private void initializeCheckedState() {
		BusyIndicator.showWhile(getShell().getDisplay(), () -> {
