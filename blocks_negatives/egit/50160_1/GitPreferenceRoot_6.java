	/**
	 * @return external diff tool command
	 */
	public static String getExternalDiffToolCommand() {
		String diffCmd = null;
		int diffTool = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.DIFF_TOOL);
		if (diffTool != 0) {
			String diffToolCustom = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.DIFF_TOOL_CUSTOM);
				diffCmd = getExternalDiffToolCommandByName(diffToolCustom);
