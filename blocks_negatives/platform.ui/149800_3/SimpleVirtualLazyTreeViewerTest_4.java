		}.waitForCondition(fViewer.getControl().getDisplay(), 3000);
		if (eventLoopAdjustmentBug531048) {
			assertTrue(updateElementCallCount <= 2);
		} else {
			assertEquals(1, updateElementCallCount);
		}
