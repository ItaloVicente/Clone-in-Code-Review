		a.setToolTipText(UIText.GitHistoryPage_showAllBranches);
		a.setImageDescriptor(UIIcons.BRANCHES);
		a.apply(a.isChecked());
		actionsToDispose.add(a);
		return a;
	}

	private IAction createCompareMode() {
		final BooleanPrefAction a = new BooleanPrefAction(
				UIPreferences.RESOURCEHISTORY_COMPARE_MODE,
				UIText.GitHistoryPage_CompareModeMenuLabel) {
			void apply(boolean fill) {
			}
		};
		a.setToolTipText(UIText.GitHistoryPage_compareMode);
		a.setImageDescriptor(UIIcons.ELCL16_COMPARE_VIEW);
