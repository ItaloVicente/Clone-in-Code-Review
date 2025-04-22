		DisplayHelper.waitAndAssertCondition(secondDialog.getShell().getDisplay(), () -> {
			long currentTick = System.currentTimeMillis();
			long previousTick = tick.getAndSet(currentTick);
			long currentDelayInUIThread = currentTick - previousTick;
			maxBlockedUIThread.set(Math.max(maxBlockedUIThread.get(), currentDelayInUIThread));
			assertTrue("Missing contributed element as previous pick",
					dialogContains(secondDialog, TestLongRunningQuickAccessComputer.THE_ELEMENT.getLabel()));
		});
