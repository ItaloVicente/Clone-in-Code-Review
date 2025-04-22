	public static boolean getDontShowDeleteConfirmAgain() {
		IPreferenceStore store = PrefUtil.getAPIPreferenceStore();
		return store.getBoolean(IWorkbenchPreferenceConstants.DONT_SHOW_DELETE_CONFIRM_AGAIN);
	}

	public static void setDontShowDeleteConfirmAgain(boolean choice) {
		IPreferenceStore store = PrefUtil.getAPIPreferenceStore();
		store.setValue(IWorkbenchPreferenceConstants.DONT_SHOW_DELETE_CONFIRM_AGAIN, choice);
	}

