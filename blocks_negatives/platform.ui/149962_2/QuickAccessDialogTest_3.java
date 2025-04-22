		assertTrue("Missing contributed element", new DisplayHelper() {
			@Override
			protected boolean condition() {
				return dialogContains(dialog, TestQuickAccessComputer.TEST_QUICK_ACCESS_PROPOSAL_LABEL);
			}
		}.waitForCondition(dialog.getShell().getDisplay(), 2000));
