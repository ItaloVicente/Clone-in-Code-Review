	protected int getUnstaged(IndexDiffData indexDiff) {
		int size = indexDiff.getUntracked().size();
		size += indexDiff.getMissing().size();
		size += indexDiff.getModified().size();
		size += indexDiff.getConflicting().size();
		return size;
	}

	protected int getStaged(IndexDiffData indexDiff) {
		int size = indexDiff.getAdded().size();
		size += indexDiff.getChanged().size();
		size += indexDiff.getRemoved().size();
		return size;
	}

	private int updateAutoExpand(TreeViewer viewer, int newSize) {
		if (newSize > 10000) {
			disableAutoExpand(viewer);
		}
		return newSize;
	}

	void switchToCompactModeInternal() {
		presentation = Presentation.COMPACT_TREE;
		getPreferenceStore().setValue(UIPreferences.STAGING_VIEW_PRESENTATION,
				Presentation.COMPACT_TREE.name());
		listPresentationAction.setChecked(false);
		treePresentationAction.setChecked(false);
		setExpandCollapseActionsVisible(true);
	}

