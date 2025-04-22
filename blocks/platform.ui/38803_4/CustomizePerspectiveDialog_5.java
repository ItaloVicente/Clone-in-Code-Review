		MenuManager menuManager = customizeActionBars.menuManager;
		IContributionItem[] items = menuManager.getItems();
		for (int src = 0; src < items.length; src++) {
			IContributionItem item = items[src];
			if (item instanceof ActionSetContributionItem) {
				ActionSetContributionItem asci = (ActionSetContributionItem) item;
				menuManager.add(asci.getInnerItem());
			}
		}

