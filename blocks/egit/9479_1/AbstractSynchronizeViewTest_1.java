	@BeforeClass public static void setupEnvironment() throws Exception {
		TeamUIPlugin.getPlugin().getPreferenceStore().setValue(
				SYNCHRONIZING_COMPLETE_PERSPECTIVE, NEVER);
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.SYNC_VIEW_FETCH_BEFORE_LAUNCH, false);

		bot.perspectiveById("org.eclipse.jdt.ui.JavaPerspective").activate();
		bot.viewByTitle("Package Explorer").show();
