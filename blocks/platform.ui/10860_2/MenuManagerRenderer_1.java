	private void disposeContributionItem(IContributionItem contributionItem,
			MenuManager parentManager, MMenuElement menuElement) {
		clearModelToContribution(menuElement, contributionItem);
		parentManager.remove(contributionItem);
	}

