	boolean hasVisibleChildren(StagingFolderEntry folder, String filterText) {
		if (filterText == null || filterText.length() == 0)
			return true;
		return getStagingEntries(folder, filterText).length > 0;
	}

	StagingEntry[] getStagingEntries() {
		return content;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
