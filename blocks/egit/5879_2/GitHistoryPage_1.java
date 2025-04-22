			if (UIPreferences.RESOURCEHISTORY_SHOW_EMAIL_ADDRESSES.equals(prop)) {
				Object oldValue = event.getOldValue();
				if (oldValue == null || !oldValue.equals(event.getNewValue())) {
					graph.setShowEmailAddresses(isShowingEmailAddresses());
					graph.getTableView().refresh();
				}
			}
