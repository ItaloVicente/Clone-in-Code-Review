		assertTrue("Missing entry", new DisplayHelper() {
			@Override
			protected boolean condition() {
				return dialogContains(dialog, quickAccessElementText);
			}
		}.waitForCondition(firstTable.getDisplay(), 2000));
