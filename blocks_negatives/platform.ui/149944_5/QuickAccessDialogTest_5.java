		assertTrue("Missing contributed element as previous pick", new DisplayHelper() {
			@Override
			protected boolean condition() {
				long currentTick = System.currentTimeMillis();
				long previousTick = tick.getAndSet(currentTick);
				long currentDelayInUIThread = currentTick - previousTick;
				maxBlockedUIThread.set(Math.max(maxBlockedUIThread.get(), currentDelayInUIThread));
				return dialogContains(secondDialog, TestLongRunningQuickAccessComputer.THE_ELEMENT.getLabel());
			}
		}.waitForCondition(secondDialog.getShell().getDisplay(),
				TestLongRunningQuickAccessComputer.DELAY + 2000));
