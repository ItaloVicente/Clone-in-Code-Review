	public static boolean useExternalDiffTool() {
		int diffTool = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.DIFF_TOOL);
		if (diffTool != 0) {
			String diffToolCustom = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.DIFF_TOOL_CUSTOM);
			if (!diffToolCustom.equals("none")) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}

	public static boolean useExternalMergeTool() {
		int diffTool = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.MERGE_TOOL);
		if (diffTool != 0) {
			String diffToolCustom = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.MERGE_TOOL_CUSTOM);
			if (!diffToolCustom.equals("none")) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}

	public static String getExternalDiffToolCommand() {
		String diffCmd = null;
		ITool tool = getExternalDiffTool();
		if (tool != null) {
			diffCmd = tool.getCommand();
		}
		return diffCmd;
	}

	public static String getExternalMergeToolCommand() {
		String mergeCmd = null;
		ITool tool = getExternalMergeTool();
		if (tool != null) {
			mergeCmd = tool.getCommand();
		}
		return mergeCmd;
	}

	public static ITool getExternalDiffTool() {
		return getExternalTool(UIPreferences.DIFF_TOOL,
				UIPreferences.DIFF_TOOL_CUSTOM, DiffToolManager.getInstance());
	}

	public static ITool getExternalMergeTool() {
		return getExternalTool(UIPreferences.MERGE_TOOL,
				UIPreferences.MERGE_TOOL_CUSTOM,
				MergeToolManager.getInstance());
	}

	public static String getExternalDiffToolAttributeValue(String toolName,
			String attrName) {
		return DiffToolManager.getInstance().getAttributeValue(toolName,
				attrName, true);
	}

	public static boolean getExternalDiffToolAttributeValueBoolean(
			String toolName,
			String attrName) {
		return DiffToolManager.getInstance().getAttributeValueBoolean(toolName,
				attrName, true);
	}

	public static String getExternalMergeToolAttributeValue(String toolName,
			String attrName) {
		return MergeToolManager.getInstance().getAttributeValue(toolName,
				attrName, true);
	}

	public static boolean getExternalMergeToolAttributeValueBoolean(
			String toolName,
			String attrName) {
		return MergeToolManager.getInstance().getAttributeValueBoolean(toolName,
				attrName, true);
	}

	public static String getBashPath() {
		String bashPath = Activator.getDefault().getPreferenceStore()
				.getString(UIPreferences.BASH_PATH);
		if (bashPath != null && !bashPath.equals("")) { //$NON-NLS-1$
			IStringVariableManager manager = VariablesPlugin.getDefault()
					.getStringVariableManager();
			String substitutedFileName;
			try {
				substitutedFileName = manager
						.performStringSubstitution(bashPath);
			} catch (CoreException e) {
				return null;
			}
			File file = new File(substitutedFileName);
			if (file.exists() || !file.isDirectory()) {
				return file.getAbsolutePath();
			}
		}
		return null;
	}

	private static ITool getExternalTool(String prefNameTool,
			String prefNameToolCustom, BaseToolManager manager) {
		ITool tool = null;
		int toolNr = Activator.getDefault().getPreferenceStore()
				.getInt(prefNameTool);
		if (toolNr != 0) {
			String toolName = null;
			loadUserScopedConfig();
			if (toolNr == 1) {
				toolName = manager.getDefaultToolName();
			} else { // custom
				toolName = Activator.getDefault().getPreferenceStore()
						.getString(prefNameToolCustom);
			}
			if (toolName != null && !toolName.equals("none")) { //$NON-NLS-1$
				tool = manager.getTool(toolName);
			}
		}
		return tool;
	}

