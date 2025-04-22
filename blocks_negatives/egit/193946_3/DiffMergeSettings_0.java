	/**
	 * Updates preferences with the current diff tool.
	 *
	 * @param config
	 */
	private static void updateDefaultDiffToolFromGitConfig(
			FileBasedConfig config) {
		String diffTool = getCustomDiffToolFromGitConfig(config);
		if (diffTool != null) {
			getStore().setValue(UIPreferences.DIFF_TOOL_FROM_GIT_CONFIG,
					diffTool);
