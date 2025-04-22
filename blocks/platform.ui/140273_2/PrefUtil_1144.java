	public static IPreferenceStore getAPIPreferenceStore() {
		if (uiPreferenceStore == null) {
			Assert.isNotNull(uiCallback);
			uiPreferenceStore = uiCallback.getPreferenceStore();
		}
		return uiPreferenceStore;
	}
