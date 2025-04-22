	void registerItem(DirectContributionItem item) {
		if (!directItemsToCheck.contains(item)) {
			directItemsToCheck.add(item);
		}
	}

	void removeItem(DirectContributionItem item) {
		directItemsToCheck.remove(item);
	}

