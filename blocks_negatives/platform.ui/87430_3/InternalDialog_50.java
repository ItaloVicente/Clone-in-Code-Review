		supportTray = new SupportTray(dialogState, new Listener() {
			@Override
			public void handleEvent(Event event) {
				dialogState.put(IStatusDialogConstants.TRAY_OPENED,
						Boolean.FALSE);
				closeTray();
				getShell().setFocus();
			}
