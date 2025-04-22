		private void createShowBranchSequenceAction() {
			showBranchSequenceAction = new BooleanPrefAction(
					UIPreferences.HISTORY_SHOW_BRANCH_SEQUENCE,
					UIText.ResourceHistory_ShowBranchSequence) {
				@Override
				void apply(boolean value) {
				}
			};
			showBranchSequenceAction
					.apply(showBranchSequenceAction.isChecked());
			actionsToDispose.add(showBranchSequenceAction);
		}

