		new DisplayHelper() {
			@Override
			protected boolean condition() {
				return list.getTopIndex() != 0;
			}
		}.waitForCondition(fViewer.getControl().getDisplay(), 3000);
		assertNotEquals(0, list.getTopIndex());
