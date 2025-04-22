		private void createShowFirstParentOnlyAction() {
			showFirstParentOnlyAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY,
					UIText.GitHistoryPage_ShowFirstParentOnlyMenuLabel) {

				@Override
				void apply(boolean value) {
					historyPage.refresh();
				}
			};
			showFirstParentOnlyAction
					.setImageDescriptor(UIIcons.MERGE_FIRST_PARENT);
			showFirstParentOnlyAction
					.setToolTipText(UIText.GitHistoryPage_showFirstParentOnly);
			actionsToDispose.add(showFirstParentOnlyAction);
		}

