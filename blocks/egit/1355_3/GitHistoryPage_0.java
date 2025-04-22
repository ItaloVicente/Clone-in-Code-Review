	private void createShowAllBranchesAction() {
		final IToolBarManager barManager = getSite().getActionBars()
				.getToolBarManager();
		showAllBranchesAction = new Action(UIText.GitHistoryPage_showAllBranches,
				IAction.AS_CHECK_BOX) {
			public void run() {
				showAllBranches = !showAllBranches;
				setChecked(showAllBranches);
				refresh();
			}
		};
		showAllBranchesAction.setImageDescriptor(UIIcons.BRANCH);
		showAllBranchesAction.setChecked(showAllBranches);
		showAllBranchesAction.setToolTipText(UIText.GitHistoryPage_showAllBranches);
		barManager.add(showAllBranchesAction);
	}


