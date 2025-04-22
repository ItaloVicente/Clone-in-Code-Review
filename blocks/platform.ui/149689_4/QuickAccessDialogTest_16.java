		activateCurrentElement(dialog);
		assertNotEquals(0, dialogSettings.getArray("orderedElements").length);
		QuickAccessDialog secondDialog = new QuickAccessDialog(activeWorkbenchWindow,
				null) {
			@Override
			protected IDialogSettings getDialogSettings() {
				return dialogSettings;
			}
		};
		secondDialog.open();
		assertTrue("Missing entry in previous pick", new DisplayHelper() {
			@Override
			protected boolean condition() {
				return dialogContains(secondDialog, quickAccessElementText);
			}
		}.waitForCondition(secondDialog.getShell().getDisplay(), 2000 * 1000 /* TODO */));
	}

	private void activateCurrentElement(QuickAccessDialog dialog) {
