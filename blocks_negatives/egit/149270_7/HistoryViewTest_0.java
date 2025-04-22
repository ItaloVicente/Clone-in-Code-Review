		getHistoryViewTable(PROJ1);
		SWTBotView view = bot
				.viewById(IHistoryView.VIEW_ID);
		SWTBotToolbarToggleButton showAllBranches = (SWTBotToolbarToggleButton) view
				.toolbarButton(UIText.GitHistoryPage_showAllBranches);
		boolean isChecked = showAllBranches.isChecked();
		if(isChecked && !checked || !isChecked && checked)
			showAllBranches.click();
