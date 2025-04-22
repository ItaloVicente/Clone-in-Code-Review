	/**
	 * Shows this dialog asynchronously
	 *
	 * @param repository
	 * @param result
	 * @param sourceString
	 */
	public static void show(final Repository repository,
			final FetchResult result, final String sourceString) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getModalDialogShellProvider().getShell();
				new FetchResultDialog(shell, repository, result, sourceString)
						.open();
			}
		});
	}

