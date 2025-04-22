		dialog = new QuickAccessDialog(activeWorkbenchWindow, null) {
			@Override
			protected IDialogSettings getDialogSettings() {
				return dialogSettings;
			}
		};
