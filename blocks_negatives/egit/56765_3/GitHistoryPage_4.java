	static boolean isShowingRelativeDates() {
		return Activator.getDefault().getPreferenceStore().getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE);
	}

	private boolean isShowingEmailAddresses() {
		return Activator.getDefault().getPreferenceStore().getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_EMAIL_ADDRESSES);
	}

