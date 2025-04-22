		assertTrue(new DisplayHelper() {
			@Override
			protected boolean condition() {
				return firstTable.getItemCount() > 0
						&& TestIncrementalQuickAccessComputer.isContributedItem(getAllEntries(firstTable).get(0));
			}
		}.waitForCondition(text.getDisplay(), 2000));
