	public static boolean useExternalDiffTool() {
		int toolNr = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.DIFF_TOOL);
		if (toolNr != 0) {
			String diffToolCustom = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.DIFF_TOOL_CUSTOM);
			if (!diffToolCustom.startsWith("none")) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}

	public static boolean useExternalMergeTool() {
		int toolNr = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.MERGE_TOOL);
		if (toolNr != 0) {
			String diffToolCustom = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.MERGE_TOOL_CUSTOM);
			if (!diffToolCustom.equals("none")) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}

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
	public static boolean autoAddToIndex() {
		return Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.MERGE_TOOL_AUTO_ADD_TO_INDEX);
	}

