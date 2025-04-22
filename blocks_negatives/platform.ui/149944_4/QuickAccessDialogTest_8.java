		assertTrue("Contributed item not found in previous choices", new DisplayHelper() { //$NON-NLS-1$
			@Override
			protected boolean condition() {
				return getAllEntries(secondTable).stream()
						.anyMatch(TestIncrementalQuickAccessComputer::isContributedItem);
			}
		}.waitForCondition(secondTable.getDisplay(), 2000));
