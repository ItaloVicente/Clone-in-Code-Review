		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				dialogResult[0] = getTargetWithCheckoutRemoteTrackingDialogInUI();
			}
		});
