		private void createShowTagSequenceAction() {
			showTagSequenceAction = new BooleanPrefAction(
					UIPreferences.HISTORY_SHOW_TAG_SEQUENCE,
					UIText.ResourceHistory_ShowTagSequence) {
				void apply(boolean value) {
				}
			};
			showTagSequenceAction.apply(showTagSequenceAction.isChecked());
			actionsToDispose.add(showTagSequenceAction);
		}

