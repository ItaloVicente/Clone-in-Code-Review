
	private void toggleShowAllBranchesButton(boolean checked) throws Exception{
		getHistoryViewTable(PROJ1);
		SWTBotView view = bot
				.viewById("org.eclipse.team.ui.GenericHistoryView");
		SWTBotToolbarToggleButton showAllBranches = (SWTBotToolbarToggleButton) view
				.toolbarButton(UIText.GitHistoryPage_showAllBranches);
		boolean isChecked = showAllBranches.isChecked();
		if(isChecked && !checked || !isChecked && checked)
			showAllBranches.click();
	}
