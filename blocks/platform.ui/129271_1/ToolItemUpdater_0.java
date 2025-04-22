
	@Override
	public void run() {
		AbstractContributionItem[] copy = itemsToUpdateLater.toArray(new AbstractContributionItem[] {});
		itemsToUpdateLater.clear();
		for (AbstractContributionItem it : copy) {
			it.updateItemEnablement();
		}
	}
