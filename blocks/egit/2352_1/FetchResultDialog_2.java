	public static void show(final Repository repository,
			final FetchResult result, final String sourceString) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getDisplay().asyncExec(
						new Runnable() {
							public void run() {
								Shell shell = PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getShell();
								new FetchResultDialog(shell, repository,
										result, sourceString).open();
							}
						});
			}
		});
	}

