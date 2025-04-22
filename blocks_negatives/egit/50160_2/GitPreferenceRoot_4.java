	private String[][] getCustomMergeTools() {
		return getCustomDiffOrMergeTools("mergetool", //$NON-NLS-1$
				ConfigConstants.CONFIG_KEY_MERGE);
	}

	private String[][] getCustomDiffTools() {
		return getCustomDiffOrMergeTools("difftool", //$NON-NLS-1$
				ConfigConstants.CONFIG_DIFF_SECTION);
