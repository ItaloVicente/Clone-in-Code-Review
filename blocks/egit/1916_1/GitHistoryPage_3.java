		BooleanPrefAction showAllBranchesAction = new BooleanPrefAction(
				UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES,
				UIText.GitHistoryPage_ShowAllBranchesMenuLabel) {

			@Override
			void apply(boolean value) {
