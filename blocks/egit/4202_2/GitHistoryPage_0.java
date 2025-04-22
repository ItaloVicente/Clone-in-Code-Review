		private void createShowNotesAction() {
			showNotesAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_SHOW_NOTES,
					UIText.ResourceHistory_toggleShowNotes) {
				void apply(boolean value) {
					historyPage.refresh();
				}
			};
			showNotesAction.apply(showNotesAction.isChecked());
			actionsToDispose.add(showNotesAction);
		}

