		assertTrue("Contributed item not found in previous choices", new DisplayHelper() { //$NON-NLS-1$
			@Override
			protected boolean condition() {
				return getAllEntries(secondDialog.getQuickAccessContents().getTable()).stream()
						.anyMatch(TestQuickAccessComputer::isContributedItem);
			}
		}.waitForCondition(secondDialog.getShell().getDisplay(), 2000));
