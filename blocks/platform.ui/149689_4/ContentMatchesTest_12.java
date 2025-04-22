		assertTrue(new DisplayHelper() {
			@Override
			protected boolean condition() {
				return Matchers.containsString("Rename the selected resource and notify LTK participants.")
						.matches(getAllEntries(table));
			}
		}.waitForCondition(table.getDisplay(), 1000));
