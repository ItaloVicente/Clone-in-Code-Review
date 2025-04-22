		if (doRunNow) {
			run();
		}
	}

	@Override
	public void run() {
		timestampOfEarliestQueuedUpdate = 0;
		AbstractContributionItem[] copy = itemsToUpdateLater.toArray(new AbstractContributionItem[] {});
		itemsToUpdateLater.clear();
		for (AbstractContributionItem it : copy) {
			it.updateItemEnablement();
		}
