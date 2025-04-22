		assertTrue(new DisplayHelper() {
			@Override
			protected boolean condition() {
				return dialogContains(dialog, TestQuickAccessComputer.TEST_QUICK_ACCESS_PROPOSAL_LABEL);
			}
		}.waitForCondition(text.getDisplay(), 2000));
