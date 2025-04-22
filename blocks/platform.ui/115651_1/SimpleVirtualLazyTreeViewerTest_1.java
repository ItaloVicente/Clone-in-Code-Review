
	private void processEventsUntilElementUpdated() {
		updateElementCallCount = 0;
		new DisplayHelper() {
			@Override
			protected boolean condition() {
				processEvents();
				return updateElementCallCount == 1;
			}
		}.waitForCondition(fViewer.getControl().getDisplay(), 3000);
		assertEquals(1, updateElementCallCount);
	}
