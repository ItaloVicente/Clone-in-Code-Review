			}
		};
		a.apply(a.isChecked());
		actionsToDispose.add(a);
		return a;
	}

	private IAction createShowAllBranches() {
		final BooleanPrefAction a = new BooleanPrefAction(
				UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES,
				UIText.GitHistoryPage_ShowAllBranchesMenuLabel) {
			void apply(boolean fill) {
				refresh();
