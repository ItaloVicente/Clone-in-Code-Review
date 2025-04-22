		private void createShowRelativeDateAction() {
			showRelativeDateAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE,
					UIText.ResourceHistory_toggleRelativeDate) {
				void apply(boolean date) {
				}
			};
			showRelativeDateAction.apply(showRelativeDateAction.isChecked());
			actionsToDispose.add(showRelativeDateAction);
		}

