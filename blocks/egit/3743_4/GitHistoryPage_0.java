		private void createFollowRenamesAction() {
			followRenamesAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_FOLLOW_RENAMES,
					UIText.GitHistoryPage_FollowRenames) {
				@Override
				void apply(boolean follow) {
					historyPage.refresh();
				}
			};
			followRenamesAction.apply(followRenamesAction.isChecked());
			actionsToDispose.add(followRenamesAction);
		}

