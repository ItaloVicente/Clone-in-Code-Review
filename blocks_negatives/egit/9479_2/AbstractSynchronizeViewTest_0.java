	@BeforeClass public static void setupEnvironment() throws Exception {
		TeamUIPlugin.getPlugin().getPreferenceStore().setValue(
				SYNCHRONIZING_COMPLETE_PERSPECTIVE, NEVER);
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.SYNC_VIEW_FETCH_BEFORE_LAUNCH, false);

