		new DisplayHelper() {
			@Override
			protected boolean condition() {
				processEvents();
				if (eventLoopAdjustmentBug531048) {
					return updateElementCallCount <= 2;
				}
				return updateElementCallCount == 1;
