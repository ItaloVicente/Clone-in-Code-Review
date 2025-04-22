		for (IContributionItem item : unstagedToolBarManager.getItems()) {
			if (!SORT_ITEM_TOOLBAR_ID.equals(item.getId())) {
				item.setVisible(visible);
			}
		}
		for (IContributionItem item : stagedToolBarManager.getItems()) {
			if (!SORT_ITEM_TOOLBAR_ID.equals(item.getId())) {
				item.setVisible(visible);
			}
		}
