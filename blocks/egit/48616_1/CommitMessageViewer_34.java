		showBranchSequencePrefAction = new BooleanPrefAction(pstore,
				UIPreferences.HISTORY_SHOW_BRANCH_SEQUENCE,
				UIText.ResourceHistory_ShowBranchSequence) {
			@Override
			protected void apply(boolean value) {
			}
		};
		mgr.add(showBranchSequencePrefAction);

