		assertTrue(new DisplayHelper() {
			@Override
			protected boolean condition() {
				return Matchers.hasItems(Matchers.containsString("Text Editors")).matches(getAllEntries(table));
			}
		}.waitForCondition(table.getDisplay(), 1000));
