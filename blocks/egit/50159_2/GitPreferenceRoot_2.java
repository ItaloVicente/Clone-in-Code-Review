	public static boolean useEclipseDiffTool() {
		int diffTool = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.DIFF_TOOL);
		if (diffTool != 0) {
			String diffToolCustom = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.DIFF_TOOL_CUSTOM);
			if (diffToolCustom.equals("none")) { //$NON-NLS-1$
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	public static String getExternalDiffToolCommand() {
		String diffCmd = null;
		int diffTool = Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.DIFF_TOOL);
		if (diffTool != 0) {
			String diffToolCustom = Activator.getDefault().getPreferenceStore()
					.getString(UIPreferences.DIFF_TOOL_CUSTOM);
			if (!diffToolCustom.equals("none")) { //$NON-NLS-1$
				diffCmd = getExternalDiffToolCommandByName(diffToolCustom);
			}
		}
		return diffCmd;
	}

	private static String getExternalDiffToolCommandByName(String name) {
		String diffCmd = null;
		StoredConfig userScopedConfig = loadUserScopedConfig();
		if (userScopedConfig != null) {
			diffCmd = userScopedConfig.getString("difftool", name, "cmd"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return diffCmd;
	}

