		private void createShowAdditionalRefsAction() {
			showAdditionalRefsAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_SHOW_ADDITIONAL_REFS,
					UIText.GitHistoryPage_ShowAdditionalRefsMenuLabel) {

				@Override
				void apply(boolean value) {
					historyPage.refresh();
				}
			};
			actionsToDispose.add(showAdditionalRefsAction);
		}

