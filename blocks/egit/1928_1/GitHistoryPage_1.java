		BooleanPrefAction reuseCompareEditorAction = new BooleanPrefAction(
				UIPreferences.RESOURCEHISTORY_REUSE_EDITOR,
				UIText.GitHistoryPage_ReuseCompareEditorMenuLabel) {
			@Override
			void apply(boolean value) {
			}
		};
		actionsToDispose.add(reuseCompareEditorAction);

