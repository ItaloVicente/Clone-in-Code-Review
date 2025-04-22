	/**
	 * Shows this dialog asynchronously
	 *
	 * @param repository
	 * @param result
	 * @param sourceString
	 * @param showConfigureButton
	 *            whether to show the "Configure..." button in the result dialog
	 *            or not
	 * @param modal
	 *            true to have application modal style
	 */
	public static void show(final Repository repository,
			final PushOperationResult result, final String sourceString,
			final boolean showConfigureButton, final boolean modal) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getModalDialogShellProvider().getShell();
				PushResultDialog dialog = new PushResultDialog(shell,
						repository, result, sourceString, modal);
				dialog.showConfigureButton(showConfigureButton);
				dialog.open();
			}
		});
	}

