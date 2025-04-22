	/**
	 * @return true if Eclipse diff tool (internal compare) should used
	 */
	public static boolean useEclipseDiffTool() {
		int diffTool = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.DIFF_TOOL);
		if (diffTool != 0) {
			String diffToolCustom = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.DIFF_TOOL_CUSTOM);
				return true;
			}
		} else {
			return true;
		}
		return false;
