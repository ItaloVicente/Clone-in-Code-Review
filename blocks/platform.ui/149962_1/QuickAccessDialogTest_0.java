		assertTrue("Missing contributed element as previous pick", DisplayHelper.waitForCondition(
				secondDialog.getShell().getDisplay(), TestLongRunningQuickAccessComputer.DELAY + TIMEOUT, () -> {
							long currentTick = System.currentTimeMillis();
							long previousTick = tick.getAndSet(currentTick);
							long currentDelayInUIThread = currentTick - previousTick;
							maxBlockedUIThread.set(Math.max(maxBlockedUIThread.get(), currentDelayInUIThread));
							return dialogContains(secondDialog,
									TestLongRunningQuickAccessComputer.THE_ELEMENT.getLabel());
						}));
