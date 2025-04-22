	private void disposeMenuManager(MenuManager menuManager,
			MenuManager parentManager, MMenu modelElement) {
		clearModelToManager(modelElement, menuManager);
		parentManager.remove(menuManager);
	}

	private void disposeContributionItem(IContributionItem contributionItem,
			MenuManager parentManager, MMenuElement menuElement) {
		clearModelToContribution(menuElement, contributionItem);
		parentManager.remove(contributionItem);
	}

