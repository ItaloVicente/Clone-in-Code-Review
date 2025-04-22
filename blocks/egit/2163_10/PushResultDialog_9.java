	private boolean hideConfigure = false;

	public static void show(final Repository repository,
			final PushOperationResult result, final String sourceString) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getDisplay().asyncExec(
						new Runnable() {
							public void run() {
								Shell shell = PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getShell();
								new PushResultDialog(shell, repository, result,
										sourceString).open();
							}
						});
			}
		});
	}

