		assertTrue("Missing contributed element", new DisplayHelper() {
			@Override
			protected boolean condition() {
				return dialogContains(dialog, TestLongRunningQuickAccessComputer.THE_ELEMENT.getLabel());
			}
		}.waitForCondition(dialog.getShell().getDisplay(), TestLongRunningQuickAccessComputer.DELAY + 2000));
