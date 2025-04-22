		QuickAccessDialog secondDialog = new QuickAccessDialog(activeWorkbenchWindow,
				null) {
			@Override
			protected IDialogSettings getDialogSettings() {
				return dialogSettings;
			}
		};
		secondDialog.open();
