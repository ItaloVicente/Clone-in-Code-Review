		final String[] dialogResult = new String[1];
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				dialogResult[0] = getTargetWithCheckoutRemoteTrackingDialogInUI();
			}
		});
		return dialogResult[0];
	}

	private String getTargetWithCheckoutRemoteTrackingDialogInUI() {
