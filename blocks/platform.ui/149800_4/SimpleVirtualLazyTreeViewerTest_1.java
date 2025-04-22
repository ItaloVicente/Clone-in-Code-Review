		DisplayHelper.waitAndAssertCondition(fShell.getDisplay(), () -> {
			if (eventLoopAdjustmentBug531048) {
				if (updateElementCallCount > 2) {
					assertEquals(2, updateElementCallCount);
				} else if (updateElementCallCount == 0) {
					assertEquals(1, updateElementCallCount);
