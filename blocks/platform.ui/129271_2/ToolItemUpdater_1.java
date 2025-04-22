
	@Override
	public void run() {
		count--;
		AbstractContributionItem[] copy = itemsToUpdateLater.toArray(new AbstractContributionItem[] {});
		itemsToUpdateLater.clear();
		for (AbstractContributionItem it : copy) {
			it.updateItemEnablement();
		}
	}
