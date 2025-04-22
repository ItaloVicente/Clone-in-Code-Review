		private void createShowEmailAddressesAction() {
			showEmailAddressesAction = new BooleanPrefAction(
					UIPreferences.RESOURCEHISTORY_SHOW_EMAIL_ADDRESSES,
					UIText.GitHistoryPage_toggleEmailAddresses) {
				void apply(boolean date) {
				}
			};
			showEmailAddressesAction.apply(showEmailAddressesAction.isChecked());
			actionsToDispose.add(showEmailAddressesAction);
		}

