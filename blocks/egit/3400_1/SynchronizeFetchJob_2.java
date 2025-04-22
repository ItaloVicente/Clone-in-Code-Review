
	private void showInformationDialog(final String remoteName) {
		final Display display = PlatformUI.getWorkbench().getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				MessageDialog.openInformation(display.getActiveShell(), NLS
						.bind(UIText.SynchronizeFetchJob_FetchFailedTitle,
								remoteName), NLS.bind(
						UIText.SynchronizeFetchJob_FetchFailedMessage,
						UIText.GitPreferenceRoot_fetchBeforeSynchronization));
			}
		});
	}

