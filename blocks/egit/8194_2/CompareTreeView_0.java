				UIText.CompareTreeView_ExpandAllTooltip) {
			@Override
			public void run() {
				tree.expandAll();
			}
		};
		expandAllAction.setImageDescriptor(UIIcons.EXPAND_ALL);
		getViewSite().getActionBars().getToolBarManager().add(expandAllAction);

		IAction collapseAllAction = new Action(
