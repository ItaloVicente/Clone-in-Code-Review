		MenuManager menuManager = customizeActionBars.menuManager;
		IContributionItem[] items = menuManager.getItems();
		for (IContributionItem item : items) {
			if (item instanceof ActionSetContributionItem) {
				ActionSetContributionItem asci = (ActionSetContributionItem) item;
				menuManager.add(asci.getInnerItem());
			}
		}

