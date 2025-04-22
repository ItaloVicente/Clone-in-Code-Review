	/**
	 * @return true if external diff tool and false if internal compare should
	 *         be used
	 */
	public static boolean useExternalDiffTool() {
		int toolNr = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.DIFF_TOOL);
		if (toolNr != 0) {
			String diffToolCustom = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.DIFF_TOOL_CUSTOM);
				return true;
			}
		}
		return false;
	}

	/**
	 * @return true if external merge tool and false if internal compare should
	 *         be used
	 */
	public static boolean useExternalMergeTool() {
		int toolNr = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.MERGE_TOOL);
		if (toolNr != 0) {
			String diffToolCustom = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.MERGE_TOOL_CUSTOM);
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the selected diff tool name or null (=default)
	 */
	public static String getDiffToolName() {
		String toolName = null;
		int toolNr = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.DIFF_TOOL);
		if (toolNr != 0) {
			toolName = Activator.getDefault().getPreferenceStore()
						.getString(UIPreferences.DIFF_TOOL_CUSTOM);
		}
		return toolName;
	}

	/**
	 * @return the selected merge tool name or null (=default)
	 */
	public static String getMergeToolName() {
		String toolName = null;
		int toolNr = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.MERGE_TOOL);
		if (toolNr != 0) {
			toolName = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.MERGE_TOOL_CUSTOM);
		}
		return toolName;
	}
