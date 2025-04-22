	private void showAbortedByHook(final Throwable cause) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			public void run() {
				MessageDialog.openWarning(PlatformUI.getWorkbench()
						.getDisplay().getActiveShell(),
						UIText.CommitJob_AbortedByHook,
						cause.getLocalizedMessage());
			}
		});
	}

