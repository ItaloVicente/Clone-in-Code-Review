	private static String[][] getCustomDiffTools() {
		BaseToolManager manager = DiffToolManager.getInstance();
		String[][] baseToolAttributes = { { "tool", null }, //$NON-NLS-1$
				{ "guitool", null } //$NON-NLS-1$
		};
		String[][] allToolAttributes = { { "prompt", "true" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "trustExitCode", "false" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "keepTemporaries", "false" }, //$NON-NLS-1$ //$NON-NLS-2$
				{ "writeToTemp", "false" } //$NON-NLS-1$ //$NON-NLS-2$
		};
		System.out.println("----- getCustomDiffTools -----"); //$NON-NLS-1$
		return getCustomDiffOrMergeTools(ConfigConstants.CONFIG_DIFF_SECTION,
				baseToolAttributes, "difftool", //$NON-NLS-1$
				allToolAttributes, manager);
