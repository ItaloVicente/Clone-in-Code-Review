		compareModeAction = new Action(UIText.GitHistoryPage_compareMode,
				IAction.AS_CHECK_BOX) {
			public void run() {
				compareMode = !compareMode;
				setChecked(compareMode);
				fileViewer.setCompareMode(compareMode);
			}
		};
		compareModeAction.setImageDescriptor(UIIcons.ELCL16_COMPARE_VIEW);
		compareModeAction.setChecked(compareMode);
		compareModeAction.setText(UIText.GitHistoryPage_CompareModeMenuLabel);
		compareModeAction.setToolTipText(UIText.GitHistoryPage_compareMode);
		fileViewer.setCompareMode(compareMode);

		showAllBranchesAction = new Action(
				UIText.GitHistoryPage_showAllBranches, IAction.AS_CHECK_BOX) {
			public void run() {
				showAllBranches = !showAllBranches;
				setChecked(showAllBranches);
				refresh();
			}
		};
		showAllBranchesAction.setImageDescriptor(UIIcons.BRANCH);
		showAllBranchesAction.setChecked(showAllBranches);
		showAllBranchesAction
				.setText(UIText.GitHistoryPage_ShowAllBranchesMenuLabel);
		showAllBranchesAction
				.setToolTipText(UIText.GitHistoryPage_showAllBranches);
