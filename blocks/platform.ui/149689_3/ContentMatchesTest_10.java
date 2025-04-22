		assertTrue(new DisplayHelper() {
			@Override
			protected boolean condition() {
				return Matchers.hasItemsMatchers.hasItems(Matchers.containsString("Text Editors")).matches(getAllEntries(table));
			}
		}.waitForCondition(table.getDisplay(), 1000);
