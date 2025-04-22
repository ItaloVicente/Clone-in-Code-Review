		assertTrue("Missing contributed element", new DisplayHelper() {
			@Override
			protected boolean condition() {
				return dialogContains(dialog, TestQuickAccessComputer.TEST_QUICK_ACCESS_PROPOSAL_LABEL);
			}
		}.waitForCondition(dialog.getShell().getDisplay(), 2000));
	}

	public void testLongRunningComputerDoesntFreezeUI() throws InterruptedException {
		QuickAccessDialog dialog = new QuickAccessDialog(activeWorkbenchWindow, null) {
			@Override
			protected IDialogSettings getDialogSettings() {
				return dialogSettings;
			}
		};
		dialog.open();
		final Table table = dialog.getQuickAccessContents().getTable();
		Text text = dialog.getQuickAccessContents().getFilterText();
		long duration = System.currentTimeMillis();
		text.setText(TestLongRunningQuickAccessComputer.THE_ELEMENT.getId());
		assertTrue("UI Frozen on text change",
				System.currentTimeMillis() - duration < TestLongRunningQuickAccessComputer.DELAY);
		assertTrue("Missing contributed element", new DisplayHelper() {
			@Override
			protected boolean condition() {
				return dialogContains(dialog, TestLongRunningQuickAccessComputer.THE_ELEMENT.getLabel());
			}
		}.waitForCondition(dialog.getShell().getDisplay(), TestLongRunningQuickAccessComputer.DELAY + 2000));
		table.select(0);
		activateCurrentElement(dialog);
		duration = System.currentTimeMillis();
		QuickAccessDialog secondDialog = new QuickAccessDialog(activeWorkbenchWindow,
				null) {
			@Override
			protected IDialogSettings getDialogSettings() {
				return dialogSettings;
			}
		};
		secondDialog.open();
		assertTrue(System.currentTimeMillis() - duration < TestLongRunningQuickAccessComputer.DELAY);
		AtomicLong tick = new AtomicLong(System.currentTimeMillis());
		AtomicLong maxBlockedUIThread = new AtomicLong();
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
		assertTrue(maxBlockedUIThread.get() < TestLongRunningQuickAccessComputer.DELAY);
