		for (IContributionItem item : stagedToolBarManager.getItems()) {
			if (!SORT_ITEM_TOOLBAR_ID.equals(item.getId())) {
				item.setVisible(visible);
			}
		}
		unstagedExpandAllAction.setEnabled(visible);
		unstagedCollapseAllAction.setEnabled(visible);
		stagedExpandAllAction.setEnabled(visible);
		stagedCollapseAllAction.setEnabled(visible);
		sortAction.setEnabled(true);
		unstagedToolBarManager.update(true);
		stagedToolBarManager.update(true);
