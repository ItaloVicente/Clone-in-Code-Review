		assertTrue("Missing entry in previous pick", new DisplayHelper() {
			@Override
			protected boolean condition() {
				return dialogContains(secondDialog, quickAccessElementText);
			}
		}.waitForCondition(secondDialog.getShell().getDisplay(), 2000 * 1000 /* TODO */));
