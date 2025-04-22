	private static void updateDefaultDiffToolFromGitConfig(
			FileBasedConfig config) {
		String diffTool = getCustomDiffToolFromGitConfig(config);
		if (diffTool != null) {
			getStore().setValue(UIPreferences.DIFF_TOOL_FROM_GIT_CONFIG,
					diffTool);
		} else {
			getStore().setValue(UIPreferences.DIFF_TOOL_FROM_GIT_CONFIG, ""); //$NON-NLS-1$
		}
