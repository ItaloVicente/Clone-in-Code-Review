	private static String[][] getCustomMergeTools() {
		BaseToolManager manager = MergeToolManager.getInstance();
		String[][] baseToolAttributes = { { "tool", null } //$NON-NLS-1$
		};
		String[][] allToolAttributes = { { "prompt", "true" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "trustExitCode", "false" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "keepBackup", "true" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "keepTemporaries", "false" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "writeToTemp", "false" } //$NON-NLS-1$ //$NON-NLS-2$
		};
		System.out.println("----- getCustomMergeTools -----"); //$NON-NLS-1$
		return getCustomDiffOrMergeTools(ConfigConstants.CONFIG_KEY_MERGE,
				baseToolAttributes, "mergetool", //$NON-NLS-1$
				allToolAttributes, manager);
