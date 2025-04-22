		if (checkDisposed()) {
			return false;
		}

		boolean visibleItem = false;
		if (toolBarManager != null) {
			IContributionItem[] contributionItems = toolBarManager.getItems();
			for (IContributionItem item : contributionItems) {
				IContributionItem contributionItem = item;
