	private void setExpandCollapseActionsVisible(boolean staged,
			boolean visibleExpandAll,
			boolean visibleCollapseAll) {
		ToolBarManager toolBarManager = staged ? stagedToolBarManager
						: unstagedToolBarManager;
		for (IContributionItem item : toolBarManager.getItems()) {
			String id = item.getId();
			if (EXPAND_ALL_ITEM_TOOLBAR_ID.equals(id)) {
				item.setVisible(visibleExpandAll);
			} else if (COLLAPSE_ALL_ITEM_TOOLBAR_ID.equals(id)) {
				item.setVisible(visibleCollapseAll);
